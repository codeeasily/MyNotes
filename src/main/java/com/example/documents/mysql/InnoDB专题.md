# InnoDB的缓存机制
InnoDB用一块内存区做IO缓存池，该缓存池不仅用来缓存InnoDB的索引块，而且也用来缓存InnoDB的数据块，这一点和MyISAM不同。

在内部，InnoDB缓存池逻辑上分为 free list(控线缓存块列表)、flush list（需要刷新到磁盘的缓存块列表）和LRU list（正在使用的缓存块，是缓存池的核心）。

InnoDB使用的LRU算法和MyISAM的"中点插入策略"LRU算法很类似。

# InnoDB 双写（double write）
在进行脏页刷新时，InnoDB采用了双写策略，这么做的原因是：
> MySQL的数据也大小（一般是16KB）与操作系统的IO数据页大小（一般是4KB）不一致，无法保证InnoDB缓存页被完整、一致的刷新到磁盘，而InnoDB的redo日志只记录了数据页改变的部分，并未记录数据页的完整前像，
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
