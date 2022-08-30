# 内存分配与回收策略
## 对象优先在Eden分配
> Eden区没有足够大的空间进行分配时，虚拟机将发起一次Minor GC;

新生代GC（Minor GC）:
> 指发生在新生代的垃圾收集动作；

老年代GC（Major GC/Full GC）:
> 指发生在老年代的GC，出现Major GC 会伴随至少一次的Minor GC（但非绝对,Parallel Scavenge 收集器的收集策略里就有直接进行Major GC 的策略选择过程）

Major GC 的速度一般会比Minor GC 慢10 倍以上

## 大对象直接进入老年代
> -XX:PretenureSizeThreshold 可设置定义大对象的大小。 只支持Serial和ParNew。
> 大对象是指：需要大量连续空间的Java对象，最典型的就是那种很长的字符串以及数组
## 长期存活的对象将进入老年代
> 如果对象在Eden中出生并经历过一次Minor GC 后仍然存活，并且能被Survivor 容纳的话，将被移动到Survivor空间中，并且对象年龄设置为1。对象在Survivor区中每“熬过”一次Minor GC ，年龄就增加1岁，当它的年龄增加到一定程度（默认是15岁），就将会被晋升到老年代中。

> 可以通过参数-XX:MaxTenuringThreshold设置年龄阈值；
## 动态对象年龄判定
> 如果Survivor空间中相同年龄所有对象大小的总和大于Survivor空间的一半，年龄大于或等于该年龄的对象就可以直接进入老年代，无须等到MaxTenuringThreshold中要求的年龄。

## 空间分配担保
> 在发生Minor GC 之前，虚拟机会先检查老年代最大可用的连续空间是否大于新生代所有对象总空间，如果这个条件成立，那么Minor GC 可以确保是安全的。如果不成立，则虚拟机会查看HandlePromotionFailure设置值是否允许担保失败；如果允许，那么会继续检查老年代最大可用的连续空间是否大于历次晋升到老年代对象的平均大小，如果大于，将尝试着进行一次Minor GC, 尽管这次Minor GC 是有风险的；如果小于，或者HandlePromotionFailure设置不允许冒险，那这时也要改为进行一次Full GC.