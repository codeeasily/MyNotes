# 死循环创建大对象代码
![](image/jvm-oom-0.png)
# 调整JVM参数
```shell
-Xms50m 
-Xmx100m 
-XX:+HeapDumpOnOutOfMemoryError 
-XX:HeapDumpPath=./java_heapdump.hprof
```
![](image/jvm-oom-config.png)
# 测试
- 调用接口，会自动生成`java_heapdump.hprof`文件
- 终端输入命令：`jvisualvm`会启动jvisualvm
- 装入`java_heapdump.hprof`文件
- 
![](image/jvm-oom-1.png)
![](image/jvm-oom-2.png)
