# 内存分配注意事项：
- 将尽量多的内存分配给MySQL做缓存，但要给操作系统和其他程序的运行预留足够的内存，否则如果产生SWAP页交换，将严重影响性能。
- MyISAM的数据页文件读取依赖于操作系统自身的IO缓存，因此如果有MyISAM表，就要预留足够多的内存给操作系统做IO缓存。
- 排序区、连接区等缓存是分配给每个数据库会话专用的，其默认值要根据最大连接数合理分配，如果设置太大，不但浪费内存资源，而且在并发连接较高时会导致物理内存耗尽。

# MyISAM内存优化
MyISAM引擎使用key buffer缓存索引块，以加速索引的读写速度。对于MyISAM表的数据块，MySQL没有特别的缓存机制，完全依赖于系统IO缓存。
1. key_buffer_size的设置：
  > 对于一般MyISAM数据库，建议至少将1/4的内存分配给key_buffer_size
  > 使用率控制在80%左右比较合适。
2. 使用多个索引缓存
3. 调整"中点插入策略"
  > 默认情况下，MySQL使用简单的LRU（Least Recently Used）策略来淘汰索引的数据块。但这种算法不是很精细，某些情况下会导致真正的热块被淘汰。
  > 
  > 可以利用中点插入策略（Midpoint Insertion Strategy）优化淘汰算法，LRU升级版。
4. 调整read_buffer_size 和read_rnd_buffer_size
  > 如果需要顺序扫描表，可以通过增大read_buffer_size来改善性能。
  >
  >对于需要做排序的表查询，如带有order by语句的SQL，适当增大read_rnd_buffer_size的值，可以改善此类SQL的性能。

这两个值都是按session分配的，默认值都不能设置的太大。  

