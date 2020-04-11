# Redis

## 资料

掘金小册：https://juejin.im/book/5afc2e5f6fb9a07a9b362527

java redis实现：https://github.com/redisson/redisson

redis面试问题集锦 https://zhuanlan.zhihu.com/p/41951014

## redis 和 memcached的区别

* redis 不仅支持简单的key-value数据类型，还提供list set hash string zset等数据结构
* redis支持数据的持久化，memcached不支持
* redis是单线程 memcached是多线程
* 数据一致性：memcached提供cas保证多线程并发时的数据一致性，redis通过事务确保一串命令的原子性

## Redis内存回收

过期键删除策略：

* 被动删除（惰性删除）、
* 主动删除（定期主动淘汰）：内部维护定时任务，每秒运行10次，删除过期键逻辑采用自适应算法，根据键的过期比例设置快慢两种速率回收键，并在键过期概率不超过25% 时，停止算法
* 内存超过maxmemory限定主动清理，主动清理策略：
  * volatile-lru：只对设置了过期时间的key进行LRU
  * allkeys-lru:根据LRU算法删除所有key
  * volatile-random：随机删除即将过期key
  * allkeys-random：随机删除所有键
  * volatile-ttl：删除即将过期的
  * noeviction：永不过期，不会删除任何数据，拒绝所有写入操作并返回客户端错误信息，此时只响应读操作

## redis事务

Redis事务如何处理？怎么保证事物之间不受影响

- Redis事务的分析及改进 https://segmentfault.com/a/1190000002594059
- 只保证一致性和隔离性 不保证原子性和持久性
- 原子性：redis事务执行遇到错误不会回滚，而是继续执行后续命令；
- 持久性：redis使用的持久化模式有三种：内存模式、RDB模式、AOF模式。
  - RDB模式下，服务器在事务执行之后、RDB文件更新之前的这段时间失败，所以RDB模式下的Redis事务也是不持久的。
  - AOF模式下，服务器在命令执行成功和数据保存到硬盘之间，也是有时间间隔的

Redis事务与MySql的区别

- MySQL实现事务，是基于`UNDO/REDO日志`。通过begin rollback commit。默认每个sql都是一个事务，并自动commit。
- redis是基于commands队列。默认不开启。通过watch监控key 通过multi开启事务 exec执行 discard取消

## redis分布式锁：

### 分布式锁的四个条件：

* 互斥性。在任意时刻，只要一个客户端能获得锁。
* 不会发生死锁。及时有一个客户端获取锁之后没有主动解锁，也不会影响后续其他客户端获取锁。通过set原子指令保证原子性
* 性能。
* 可重入。也叫递归锁，同一个线程可以重复获得同一个资源的锁

分布式锁实现参考：

* http://blueskykong.com/2018/01/06/redislock/#more
* https://www.cnblogs.com/number7/p/8320259.html java redission锁实现

* https://mp.weixin.qq.com/s/gOYWLg3xYt4OhS46woN_Lg
  * lua脚本保证释放锁时的原子性
  * redlock算法
* 官方文档关于锁的标准实现：http://redis.cn/topics/distlock.html
* http://zhangtielei.com/posts/blog-redlock-reasoning.html

### 单点redis锁实现：

1. 通过setnx原子指令进行加锁，随机值在所有客户端必须是唯一的，

   ```lua
   SET resource_name my_random_value NX PX 30000
   ```

2. 释放锁时检查随机值，然后进行删除操作，这两步需要通过lua脚本保证原子性

   ```lua
   if redis.call("get",KEYS[1]) == ARGV[1] then
       return redis.call("del",KEYS[1])
   else
       return 0
   end
   ```

***关键点：随机值和lua脚本。***

* 随机值是为了避免删除其他客户端获取成功的锁。如果value是一个定值，则如果客户端A获取到锁之后因为某种原因阻塞；锁超时后被另一个客户端B获得；如果此时客户端A进行释放锁操作，那就会将B的锁删除

* lua脚本也是为了避免删除其他客户端获取成功的锁。如果释放锁操作不是原子性，则如果客户端A执行get请求，并判断随机数与预期值相等后，因为某种原因阻塞了；锁超时后被另一个客户端B获得；如果此时客户端A进行释放锁操作，那就会将B的锁删除

### redlock分布式锁实现步骤

。。。

## Lua脚本优点

* 减少网路开销
* 原子操作
* 复用

## Redis的Scan命令与keys命令

scan是非阻塞的是扫描命令，需要手动去重，而keys会导致线上服务停顿 

## redis 集群

**主从模式**、**哨兵模式**、**集群模式**。

redi	s sentinal着眼于高可用，在master宕机时，会将slave提升为master

redis cluster 着眼于扩展性，在单个redis内存不足时，使用cluster进行分片存储

Redis的哨兵机制

Redis的发布订阅机制

## select、poll或epoll

* 每次调用select都会将所有的文件描述符（fd_set）从用户空间拷贝到内核空间 操作系统进行遍历
  * 缺点1：有数量限制
  * 缺点2：拷进拷出
  * 缺点3：操作系统需要全局扫描哪些文件描述符是就绪（读就绪或者写就绪）的 如果有连接 拷贝回用户态 用户态进行全局扫描 
* poll没有数量限制
* epoll ：在内核中维护一个存储文件描述符的红黑树 用户将文件描述符拷贝到红黑树中，红黑树中的节点使用callback表示文件描述符可用，并拷贝节点信息到链表中，内核将该包含所有可用文件描述符的链表拷贝回用户态，用户只需要遍历这个链表即可。
  * 优点1：不用拷进拷出。epoll_create创建一个epoll句柄，epoll_ctl注册新的事件到epoll句柄中，会把所有的fd拷贝进内核，而且保证只拷贝一次，并指定回调函数。 epoll_ctl函数注册要监听的事件类型
  * 优点2：不用全局扫描。设备就绪时会调用回调函数，把就绪fd放入就绪链表中，并唤醒在epoll_wait中进入睡眠的进程。epoll_wait只需要在被唤醒时，查看就虚链表是否为空即可。
* 应用场景：select适合实时要求高的场景 timeout参数精度为1ns，poll适用于描述符数量较多，且实时要求不高的场景，epoll只能用于linux ，且适用于长连接，描述符数量大、变化少的场景

## Redis Sorted set

* zadd key score value：key是有序集合的名字 score用于排序 value是存储的member，不允许重复
* 底层实现：当items小于64时 采用ziplist实现；大于64改为skiplist和字典同时实现
* 字典和跳跃表共享对象

redis有五种数据类型，若干种编码方式

## Redis AOF重写

* 通过追加log的方式，完成对数据库的备份
* AOF重写启动方式：1、用户手动执行BGREWRITEAOF命令去重写；2、增长百分比为100%的时候启动重写（需满足三个条件：1、BGSAVE不在执行； 2、BGREWRITEAOF没在执行； 3、 AOF文件大小超出1MB（太小没有必要重写））
* AOF重写实现：
  * 因为AOF的目的是保存数据库的状态，重复操作的比如插入修改等指令并不是关键，AOF重写只需要读取数据库的当前状态，比如执行zadd 有序集合中现存的所有成员
  * 一句话总结：根据键的类型， 使用适当的写入命令来重现键的当前值， 这就是 AOF 重写的实现原理 
* AOF重写过程：
  * 主进程fork子进程进行重写，重写过程中的写入指令 一方面追加到现有AOF文件中；一方面添加到AOF重写缓存（重写完成后启用）
  * 重写完成后，子进程发送给主进程完成信号，主进程将AOF重写缓存中的指令追加到新的AOF文件中
  * 新AOF文件改名并覆盖现有AOF文件