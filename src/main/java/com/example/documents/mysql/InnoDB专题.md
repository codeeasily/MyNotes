# InnoDB的缓存机制
InnoDB用一块内存区做IO缓存池，该缓存池不仅用来缓存InnoDB的索引块，而且也用来缓存InnoDB的数据块，这一点和MyISAM不同。

在内部，InnoDB缓存池逻辑上分为 free list(控线缓存块列表)、flush list（需要刷新到磁盘的缓存块列表）和LRU list（正在使用的缓存块，是缓存池的核心）。

InnoDB使用的LRU算法和MyISAM的"中点插入策略"LRU算法很类似。

# InnoDB 双写（double write）
在进行脏页刷新时，InnoDB采用了双写策略，这么做的原因是：
> MySQL的数据页大小（一般是16KB）与操作系统的IO数据页大小（一般是4KB）不一致，无法保证InnoDB缓存页被完整、一致的刷新到磁盘，而InnoDB的redo日志只记录了数据页改变的部分，并未记录数据页的完整前像，
当发生部分写或断裂写时（比如将缓存页的第一个4KB写入磁盘后，服务器断电），就会出现页面无法恢复的问题，为了解决这个问题，InnoDB引入了double write技术。

double write机制的实现原理：
> 用系统表空间中的一块连续磁盘空间（100个连续数据页，大小为2MB）作为double write buffer，当进行脏页刷新时，首先将脏页的副本写到系统表空间的doublewrite buffer中，然后调用fsync()刷新操作系统IO缓存，
> 确保副本被真正写如磁盘，最后InnoDB后台IO线程将脏页刷新到磁盘数据文件。

# InnoDB log机制
采用redo log机制来保证事务更新的一致性和持久性。是保证事务ACID属性的重要机制。

更新数据时，InnoDB内部的大概操作流程：
1. 将数据读入InnoDB buffer pool，并对相关记录加独占锁；
2. 将UNDO信息写入undo表空间的回滚段中；
3. 更改缓存页中的数据，并将更新记录写入redo buffer中；
4. 提交时，根据innodb_flush_log_at_trx_commit的设置，用不同的方式将redo buffer中的更新记录刷新到InnoDB redo log file中，然后释放独占锁；
5. 最后，后台IO线程根据需要择机将缓存中更新过的数据刷新到磁盘文件中。

可以通过 `show engine innodb status`命令查看当前日志的写入情况。
InnoDB引擎有一个特殊的功能叫做 "自适应哈希索引"。

# redo-log undo-log bin-log
- undo-log:
  > 记录数据更新之前的数据，用来做回滚操作
- redo-log:
  > 记录数据做了什么更新操作，提交事务之前，如果系统宕机等，用来恢复
- bin-log:
  > server级别的二级制文件，记录写入性的操作

# 自适应哈希索引
当InnoDB注意到某些索引值被使用的非常频繁时，会在内存中基于B-Tree索引之上再创建一个哈希索引，这样就让B-Tree索引也具有哈希索引的一些优点，比如快速查找。 

这是一个完全自动的、内部的行为，用户无法控制和配置，但是可以完全关闭该功能。

# 索引下推 ICP
MySQL 5.6引入了Index Condition Pushdown(ICP)的特性，进一步优化了查询。某些情况下的条件过滤操作下放的操作引擎。

目的是为了减少回表次数，也就是要减少IO操作。

通过explain查看执行计划`Extra：using index condition`就表示使用了ICP。
- 不使用ICP的查询
  > 在不使用ICP的情况下，在使用非主键索引（又叫普通索引或者二级索引）进行查询时，存储引擎通过索引检索到数据，然后返回给MySQL服务器，服务器然后判断数据是否符合条件 ；
- 使用ICP的查询
  > 在使用ICP的情况下，如果存在某些被索引的列的判断条件时，MySQL服务器将这一部分判断条件传递给存储引擎，
    然后由存储引擎通过判断索引是否符合MySQL服务器传递的条件，只有当索引符合条件时才会将数据检索出来返回给MySQL服务器 ；

## 索引下推使用条件
- 只能用于range、 ref、 eq_ref、ref_or_null访问方法；
- 只能用于InnoDB和 MyISAM存储引擎及其分区表；
- 对存储引擎来说，索引下推只适用于二级索引（也叫辅助索引）;
