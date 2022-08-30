```
版权声明：本文为CSDN博主「跟着Mic学架构」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/q331464542/article/details/125834397
```
# 对象头
![](image/jvm-class-head.png)

对象头中用来表示GC年龄的只有4bit,最大表示15（1111）

GC年龄是奇数
![](image/jvm-gc-from-to.png)



