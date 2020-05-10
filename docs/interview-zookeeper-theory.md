# Zookeeper原理

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

## 参考

* https://juejin.im/post/5baf7db75188255c3d11622e
* [Zookeeper源码分析-Zookeeper Leader选举算法](http://www.yidooo.net/2014/10/18/zookeeper-leader-election.html)
* [Zookeeper源码分析](http://www.yidooo.net/categories/Big-Data/ZooKeeper/)