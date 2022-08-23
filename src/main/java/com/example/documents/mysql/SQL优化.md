# SQL优化的一般步骤
1. 通过`show status` 命令了解各种SQL的执行频率
   ```sql
    mysql> show status like 'Com_%';
   ```
   | Variable_name | value |
   |-------|------|
   | Com_admin_commands | 0     |
   | Com_alter_db | 0     |
   | Com_alter_table | 0     |
   | Com_begin | 0     |

   参数说明：对于所有存储引擎的表操作都会进行累计。
   - Com_select：执行SELECT操作的次数，一次查询只累加1；
   - Com_insert：执行INSERT操作的次数，对于批量插入的INSERT操作只累加一次；
   - Com_update：执行UPDATE操作的次数
   - Com_delete：执行DELETE操作的次数
   
   只针对InnoDB存储引擎的：
   - Innodb_rows_read：SELECT查询返回的行数；
   - Innodb_rows_inserted：执行INSERT操作插入的行数；
   - Innodb_rows_updated：执行UPDATE操作更新的行数；
   - Innodb_rows_deleted：执行DELETE操作删除的行数
   
   事务型的应用可以通过Com_commit和Com_rollback了解事务提交和回滚的情况。
2. 定位执行效率较低的SQL语句
   - 通过慢查询日志定位
    > 用`--log-slow-queries[=file_name]`选项启动时，mysqld写一个包含所有执行时间超过`long_query_time`秒的SQL语句的日志文件；
   - 使用show processlist命令
    > 慢查询日志在查询结束以后才记录，所以在应用反映执行效率出现问题的时候查询慢查询日志并不能定位问题，可以使用`show processlist`命令查看当前MySQL在进行的线程，
    > 包括线程的状态、是否锁表等，可以实时的查看SQL的执行情况，同时对一些锁表操作进行优化。
3. 通过`Explain`分析抵消SQL的执行计划
   - select_type: 表示SELECT的类型，常见的取值有SIMPLE（简单表，即不使用表连接或者子查询）、PRIMARY（主查询，即外层查询）、UNION（UNION中的第二个或者后面的查询语句）、SUBQUERY（子查询中的第一个SELECT）等；
   - table: 输出结果集的表
   - type: 标识MySQL在表中找到所需行的方式，或者叫访问类型，常见类型如下：

     |ALL |index | range |ref |eq_ref |const,system |NULL |
     |---|---|---|---|---|---|---|
     从左到右，性能由最差到最好。
     - ALL：全表扫描
     - index：索引全扫描，遍历整个索引
     - range：索引范围扫描（常见于<、<=、>、>=、between等操作符）
     - ref：使用非唯一索引或唯一索引扫描的前缀扫描，返回某个单独值的匹配行；
     - eq_ref：类似ref，区别就在使用的索引是唯一索引，（多表连接中使用primary key或者unique index作为关联条件）
     - const/system：单表中最多有一个匹配行，查询起来非常迅速，所以这个匹配行中的其他列的值可以被优化器在当前查询中当做常量来处理（例如根据主键或唯一索引进行的查询）
   - possible_keys：查询时可能使用到的索引
   - key：查询时实际使用到的索引
   - key_len：使用到索引字段的长度
   - rows：扫描行的数量
   - Extra：执行情况的说明和描述。
     > - Useing index：覆盖索引扫描，不需要回表。
     > - Useing where：需要通过索引回表查询数据。
4. 通过`show profile`分析SQL
   > MySQL从5.0.37版本增加了对show profile和show profiles语句的支持。
     通过`have_profiling`参数，可以看当前MySQL是否支持profile。
    ```sql
    mysql> select @@have_profiling; 
    ```
   默认profiling是关闭的，通过`set profilling=1`开启。

   > 通过`show profiles`可以查看SQL的QUERY ID；
   > 
   > 通过`show profile for query 1`可以看到QUERY ID为1的SQL执行过程中线程的每个状态和消耗的时间；
5. 通过trace分析优化器如何选择执行计划
   > 使用方式：首先打开trace,设置格式为JSON，设置trace最大能够使用的内存大小，避免解析过程中因为默认内存过小而不能够完整显示。
   > ```sql
   > mysql> SET OPTIMIZER_TRACE="enabled=on",END_MARKERS_IN_JSON=on;
   > mysql> SET OPTIMIZER_TRACE_MAX_MEM_SIZE=1000000;
   > ```
   > 接下来执行想做trace的SQL语句。
   > 最后检查`INFORMATION_SCHEMA.OPTIMIZER_TRACE`就可以知道MySQL是如何执行SQL的；
   > ```sql
   > mysql> SELECT * FROM INFORMATION_SCHEMA.OPTIMIZER_TRACE\G
   > ```