# zookeeper服务注册与发现

## zookeeper的重要概念

### Zookeeper节点类型

* 持久节点
* 持久顺序节点
* 临时节点
* 临时顺序节点

### Zookeeper集群

Zookeeper 是一个由多个 server 组成的集群,一个 leader，多个 follower。（这个不同于我们常见的Master/Slave模式）

leader 为客户端服务器提供**读写服务**，除了leader外其他的机器只能提供**读服务**。

每个 server 保存一份数据副本全数据一致，分布式读 follower，**写由 leader 实施更新请求转发，由 leader 实施更新请求顺序进行，来自同一个 client 的更新请求按其发送顺序依次执行数据更新**。

原子性，一次数据更新要么成功，要么失败。

全局唯一数据视图，client 无论连接到哪个 server，数据视图都是一致的实时性，在一定事件范围内，client 能读到最新数据。

### 心跳机制

Leader和Follower之间通过**PING**来感知对方的存活，一旦与leader的心跳中断，就需要重新进行leader选举。

### 集群角色

* Leader

* Follower
    * 参与选举和提议

* Observer
    * 观察者可以提高读取的吞吐量；
    * 观察者不参与选举，也不响应提议，也不参与写操作的“过半写”策略
    * 观察者不需要持久化事务到磁盘

## zookeeper的特性

* 顺序一致性，从同一个客户端发起的事务请求，最终将会严格地按照其发起顺序被应用到Zookeeper中去。

* 原子性，所有事务请求的处理结果在整个集群中所有机器上的应用情况是一致的，即整个集群要么都成功应用了某个事务，要么都没有应用。

* 单一视图，无论客户端连接的是哪个 Zookeeper 服务器，其看到的服务端数据模型都是一致的。

* 可靠性，一旦服务端成功地应用了一个事务，并完成对客户端的响应，那么该事务所引起的服务端状态变更将会一直被保留，除非有另一个事务对其进行了变更。

* 实时性，Zookeeper 保证在一定的时间段内，客户端最终一定能够从服务端上读取到最新的数据状态。

## zookeeper的Leader选举算法

> https://mp.weixin.qq.com/s/6Lai6Gw9h2YAinS4QqNOLA

**名次解释**

* **myid**:节点id

* **zxid**:节点事务id（事务id大的代表该节点拥有最新数据）

* 选票pk规则：

    （a）优先对比ZXID，ZXID大的优先作为Leader（ZXID大的表示数据多）
    
    （b）如果ZXID一样的话，那么就比较myid，让myid大的作为Leader服务器。

![zookeeper选举的核心算法逻辑图](https://mmbiz.qpic.cn/mmbiz_png/9TPn66HT932oicKR4gsickpFp0CztzETKUfV2IdYdicm5XIoAsI1xD4XULicd5tBiazkpsKCptSbOVGVoLDAph2ZldQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

简单来说，选举分为以下步骤：
1. 投票；
1. 选票pk；
1. 统一选票，并调整或坚持投票；

超过半数即可终止投票。（zookeeper集群推荐为单数，服务器成本较低，与超过半数（n/2+1）有关）

Q1: Leader挂掉后恢复，如何解决多leader的问题？

A1: follower和leader间存在心跳，原leader恢复后发现其他节点与自己的心跳没了，就知道自己已经被驱逐了，然后就会调整自己为follower。

## 数据快照机制

## 数据清理机制

## 会话机制

## 长连接机制

## ZAB协议算法

## 客户端调度策略
* 随机策略
* round robin策略（按顺序轮询）

## curator源码解析
### Watch监听机制(NodeCache,PathChildrenCache,TreeCache)

https://blog.csdn.net/hosaos/article/details/88658676

------

## 参考

* https://juejin.im/post/5baf7db75188255c3d11622e
* [Zookeeper源码分析-Zookeeper Leader选举算法](http://www.yidooo.net/2014/10/18/zookeeper-leader-election.html)
* [Zookeeper源码分析](http://www.yidooo.net/categories/Big-Data/ZooKeeper/)