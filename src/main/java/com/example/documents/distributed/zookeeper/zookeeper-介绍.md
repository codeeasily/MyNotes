[原文链接](https://snailclimb.gitee.io/javaguide/#/docs/distributed-system/distributed-process-coordination/zookeeper/zookeeper-intro?id=zookeeper)

# 介绍
ZooKeeper 是一个开源的分布式协调服务，它的设计目标是将那些复杂且容易出错的分布式一致性服务封装起来，构成一个高效可靠的原语集，并以一系列简单易用的接口提供给用户使用。

ZooKeeper 为我们提供了高可用、高性能、稳定的分布式数据一致性解决方案，通常被用于实现诸如数据发布/订阅、负载均衡、命名服务、分布式协调/通知、集群管理、Master 选举、分布式锁和分布式队列等功能。

另外，ZooKeeper 将数据保存在内存中，性能是非常棒的。 在“读”多于“写”的应用程序中尤其地高性能，因为“写”会导致所有的服务器间同步状态。（“读”多于“写”是协调服务的典型场景）。
# ZooKeeper 特点
- 顺序一致性： 从同一客户端发起的事务请求，最终将会严格地按照顺序被应用到 ZooKeeper 中去。
- 原子性： 所有事务请求的处理结果在整个集群中所有机器上的应用情况是一致的，也就是说，要么整个集群中所有的机器都成功应用了某一个事务，要么都没有应用。
- 单一系统映像 ： 无论客户端连到哪一个 ZooKeeper 服务器上，其看到的服务端数据模型都是一致的。
- 可靠性： 一旦一次更改请求被应用，更改的结果就会被持久化，直到被下一次更改覆盖。

# 典型的应用场景
ZooKeeper 概览中，我们介绍到使用其通常被用于实现诸如数据发布/订阅、负载均衡、命名服务、分布式协调/通知、集群管理、Master 选举、分布式锁和分布式队列等功能。

下面选 3 个典型的应用场景来专门说说：

- 分布式锁 ： 通过创建唯一节点获得分布式锁，当获得锁的一方执行完相关代码或者是挂掉之后就释放锁。
- 命名服务 ：可以通过 ZooKeeper 的顺序节点生成全局唯一 ID
- 数据发布/订阅 ：通过 Watcher 机制 可以很方便地实现数据发布/订阅。当你将数据发布到 ZooKeeper 被监听的节点上，其他机器可通过监听 ZooKeeper 上节点的变化来实现配置的动态更新。

实际上，这些功能的实现基本都得益于 ZooKeeper 可以保存数据的功能，但是 ZooKeeper 不适合保存大量数据，这一点需要注意。

# 四种znode节点
- 持久（PERSISTENT）节点 ：一旦创建就一直存在即使 ZooKeeper 集群宕机，直到将其删除。
- 临时（EPHEMERAL）节点 ：临时节点的生命周期是与 客户端会话（session） 绑定的，会话消失则节点消失 。并且，临时节点只能做叶子节点 ，不能创建子节点。
- 持久顺序（PERSISTENT_SEQUENTIAL）节点 ：除了具有持久（PERSISTENT）节点的特性之外， 子节点的名称还具有顺序性。比如 /node1/app0000000001 、/node1/app0000000002 。
- 临时顺序（EPHEMERAL_SEQUENTIAL）节点 ：除了具备临时（EPHEMERAL）节点的特性之外，子节点的名称还具有顺序性。
# 4. ZooKeeper 集群
为了保证高可用，最好是以集群形态来部署 ZooKeeper，这样只要集群中大部分机器是可用的（能够容忍一定的机器故障），那么 ZooKeeper 本身仍然是可用的。通常 3 台服务器就可以构成一个 ZooKeeper 集群了。ZooKeeper 官方提供的架构图就是一个 ZooKeeper 集群整体对外提供服务。
![](https://snailclimb.gitee.io/javaguide/docs/distributed-system/distributed-process-coordination/zookeeper/images/zookeeper集群.png)


上图中每一个 Server 代表一个安装 ZooKeeper 服务的服务器。组成 ZooKeeper 服务的服务器都会在内存中维护当前的服务器状态，并且每台服务器之间都互相保持着通信。集群间通过 ZAB 协议（ZooKeeper Atomic Broadcast）来保持数据的一致性。

**最典型集群模式：** Master/Slave 模式（主备模式）。
> 在这种模式中，通常 Master 服务器作为主服务器提供写服务，其他的 Slave 服务器从服务器通过异步复制的方式获取 Master 服务器最新的数据提供读服务。
# 集群角色
在 ZooKeeper 中没有选择传统的 Master/Slave 概念，而是引入了 Leader、Follower 和 Observer 三种角色

ZooKeeper 集群中的所有机器通过一个 Leader 选举过程 来选定一台称为 “Leader” 的机器，Leader 既可以为客户端提供写服务又能提供读服务。除了 Leader 外，Follower 和 Observer 都只能提供读服务。Follower 和 Observer 唯一的区别在于 Observer 机器不参与 Leader 的选举过程，也不参与写操作的“过半写成功”策略，因此 Observer 机器可以在不影响写性能的情况下提升集群的读性能。

| 角色        | 	说明                                                                                                  |
|-----------|------------------------------------------------------------------------------------------------------|
| Leader	   | 为客户端提供读和写的服务，负责投票的发起和决议，更新系统状态。                                                                      |
| Follower	 | 为客户端提供读服务，如果是写服务则转发给 Leader。参与选举过程中的投票。                                                              |
| Observer	 | 为客户端提供读服务，如果是写服务则转发给 Leader。不参与选举过程中的投票，也不参与“过半写成功”策略。在不影响写性能的情况下提升集群的读性能。此角色于 ZooKeeper3.3 系列新增的角色。 |

# 选举过程
当 Leader 服务器出现网络中断、崩溃退出与重启等异常情况时，就会进入 Leader 选举过程，这个过程会选举产生新的 Leader 服务器。

这个过程大致是这样的：
- Leader election（选举阶段）： 
  > 节点在一开始都处于选举阶段，只要有一个节点得到超半数节点的票数，它就可以当选准 leader。
- Discovery（发现阶段） ：
  > 在这个阶段，followers 跟准 leader 进行通信，同步 followers 最近接收的事务提议。
- Synchronization（同步阶段） :
  > 同步阶段主要是利用 leader 前一阶段获得的最新提议历史，同步集群中所有的副本。同步完成之后 准 leader 才会成为真正的 leader。
- Broadcast（广播阶段） :
  > 到了这个阶段，ZooKeeper 集群才能正式对外提供事务服务，并且 leader 可以进行消息广播。同时如果有新的节点加入，还需要对新节点进行同步。
# 选举阶段的详细过程
```
————————————————
版权声明：本文为CSDN博主「clevercondy」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_46336672/article/details/108309563
```
## 集群初始化
当启动初始化集群的时候，server1的myid为1，zxid为0 server2的myid为2，zxid同样是0，以此类推。此种情况下zxid都是为0。先比较zxid，再比较myid
1. 服务器1启动，给自己投票，然后发投票信息，由于其它机器还没有启动所以它收不到反馈信息，服务器1的状态一直属于Looking(选举状态)。
2. 服务器2启动，给自己投票，同时与之前启动的服务器1交换结果，由于服务器2的myid大所以服务器2胜出，但此时投票数没有大于半数，所以两个服务器的状态依然是LOOKING。
3. 服务器3启动，给自己投票，同时与之前启动的服务器1,2交换信息，由于服务器3的myid最大所以服务器3胜出，此时投票数正好大于半数，所以服务器3成为领导者，服务器1,2成为小弟。
4. 服务器4启动，给自己投票，同时与之前启动的服务器1,2,3交换信息，尽管服务器4的myid大，但之前服务器3已经胜出，所以服务器4只能成为小弟。
5. 服务器5启动，后面的逻辑同服务器4成为小弟
当选举机器过半的时候，已经选举出leader后，后面的就跟随已经选出的leader，所以4和5跟随成为leader的server3
所以，在初始化的时候，一般到过半的机器数的时候谁的myid最大一般就是leader

## 运行期间
> 按照上述初始化的情况，server3成为了leader，在运行期间处于leader的server3挂了，那么非Observer服务器server1、server2、server4、server5会将自己的节点状态变为LOOKING状态
1. 开始进行leader选举。现在选举同样是根据myid和zxid来进行
2. 首先每个server都会给自己投一票竞选leader。假设server1的zxid为123，server2的zxid为124，server4的zxid为169，server5的zxid为188
3. 同样先是比较zxid再比较，server1、server2、server4比较server4根据优先条件选举为leader。然后server5还是跟随server4，即使server5的zxid最大，但是当选举到server4的时候，机器数已经过半。不再进行选举，跟随已经选举的leader

## 重新投票选举leader怎么保证数据不会丢失
我们都知道，zk是cp的，不一定保证可用性，在选举的过程中，不能对外提供服务。但在选举的过程中，首先选zxid（zk的事务ID）最大的为leader,zxid最大，表示数据是最新的，然后广播给follower，这样避免数据丢失。



