# 分布式缓存

## 缓存模式

> [缓存模式以及缓存的数据一致性](https://stephanietang.github.io/2020/04/13/cache-pattern/)
>
> [缓存更新的套路](https://coolshell.cn/articles/17416.html)

* Cache-Aside
* Read-Through
* Write-Through
* Write behind caching

## Cache Aside Pattern

**读数据**

先读缓存；缓存读不到，则读数据库；然后将结果放到缓存中。

![cache aside-read data](https://i.niupic.com/images/2020/04/13/7oXo.PNG)

**写数据**

写数据有两种策略：

* 先更新数据库，然后直接更新缓存。这种策略的问题是如果出现并发写，可能会导致缓存中出现脏数据。

  > 试想有两个写的线程，线程A和线程B
  >
  > 1. A写数据库
  > 2. B后于A写数据库
  > 3. B写缓存
  > 4. A写缓存
  > 5. 缓存和数据库中的数据不一致，缓存中的是脏数据

![cache aside-write data](https://i.niupic.com/images/2020/04/13/7oXC.PNG)

* 先更新数据库；然后删除缓存。这种策略是最常用的策略，但是也有可能出现脏数据的情况.

  > 试想一下有两个线程，线程A读，线程B写
  >
  > 1. A读数据，由于未命中那么从数据库中取数据
  > 2. B写数据库
  > 3. B删除缓存
  > 4. A由于网络延迟比较慢，将脏数据写入缓存

  但是概率要低很多。另外最好对缓存中的数据设置合适的过期时间，这样能进一步减少出现脏数据的情况。

  > 因为这个条件需要发生在读缓存时缓存失效，而且并发着有一个写操作。而实际上数据库的写操作会比读操作慢得多，而且还要锁表，而读操作必需在写操作前进入数据库操作，而又要晚于写操作更新缓存，所有的这些条件都具备的概率基本并不大。

## Read Through

读取数据时直接读取缓存；如果缓存没有命中，则由缓存服务读取数据库后，再返回。

## Write Through

更新数据时优先更新缓存，然后缓存服务同步更新数据库；缓存中不存在则直接更新数据库。

![write through](https://coolshell.cn/wp-content/uploads/2016/07/460px-Write-through_with_no-write-allocation.svg_.png)

## Write Behind/Write Back

类似Linux文件系统的Page Cache算法。在更新数据的时候，只更新缓存，由缓存自己异步地批量更新数据库。

缺点是数据不是强一致性的，所以可能会丢失数据。

另外Write Back实现逻辑比较复杂，参照下面的图示，以及[维基百科的解释](https://en.wikipedia.org/wiki/Cache_(computing))。

![Write Back](https://coolshell.cn/wp-content/uploads/2016/07/Write-back_with_write-allocation.png)

## 其他

上边讨论的内容没有考虑更新缓存和更新数据库的事务问题。比如更新缓存成功，而更新数据库失败。如果需要强一致性，则需要通过两阶段提交协议。

### 缓存穿透 缓存雪崩 缓存击穿
* 缓存穿透是指查询不存在的key  通过布隆过滤器或这缓存空值解决
* 缓存雪崩 大量key在同一时刻过期
* 缓存击穿 热点key过期  静态数据设置永不过期  或者通过异步线程在快到期时更新缓存

https://www.zsythink.net/archives/1182
用一致性hash解决前端缓存图片的雪崩问题

### 参考

* [缓存模式以及缓存的数据一致性](https://stephanietang.github.io/2020/04/13/cache-pattern/)
* [缓存更新的套路](https://coolshell.cn/articles/17416.html)
* https://en.wikipedia.org/wiki/Cache_(computing)
* https://mp.weixin.qq.com/s?__biz=MjM5ODYxMDA5OQ==&mid=403963671&idx=1&sn=51a2d2fd70212451cd5f22bbe2c6f8d6&scene=21#wechat_redirect
* https://mp.weixin.qq.com/s?__biz=MjM5ODYxMDA5OQ==&mid=404202261&idx=1&sn=1b8254ba5013952923bdc21e0579108e&scene=21#wechat_redirect
* https://mp.weixin.qq.com/s?__biz=MjM5ODYxMDA5OQ==&mid=404087915&idx=1&sn=075664193f334874a3fc87fd4f712ebc&scene=21#wechat_redirect
* https://mp.weixin.qq.com/s?__biz=MjM5ODYxMDA5OQ==&mid=404308725&idx=1&sn=1a25ce76dd1956014ceb8a011855268e&scene=21#wechat_redirect
* https://www.cnblogs.com/rjzheng/p/9041659.html