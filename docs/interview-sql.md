# MySQL数据库

## ACID

* ACID 原子性 一致性 隔离性 持久性：

  * 原子性。事务要么执行成功，要么执行失败
  * 一致性。事务执行前后，应该保持一致性状态
  * 隔离性。事务执行成功前，所做修改不应该被其他事务看到
  * 持久性。一旦事务提交，其所做更改将永远保存到数据库。可通过数据库备份和恢复来实现。

  无并发情况下，事务串行执行，原子性可以保证一致性；

  并发情况下，原子性和隔离性共同保证一致性

  持久性防止数据库崩溃

* 并发一致性问题

  * 丢失修改。事务一覆盖了事务二的修改
  * 脏读。读到其他事物为提交的数据
  * 不可重复读。事务重复读取的结果不一致
  * 幻读。读取范围数据时，得到的结果不一致。比如求count

  并发一致性问题主要是因为破坏了事务的隔离性而导致，可以通过事务隔离级别来解决（其实是通过并发控制保证隔离性，并发控制可以通过封锁实现，但是数据库提供的事务隔离级别，可以让用户更轻松的实现隔离性）

* 封锁：粒度有行级锁，表锁(行锁是针对索引加的锁，只有通过索引检索数据，innodb才使用行级锁)

  * 封锁类型：X锁（排它锁，只允许当前事务读和写） S锁（共享锁	只允许当前事务读和其他事务读）
    * select...for update
    * select...lock in shard mode
  * 临时锁和持久锁：有效时期到当前语句结束还是当前事务结束
  * 运用X锁和S锁，何时申请 持续时间 何时释放等 需要封锁协议去控制
    * 一级封锁协议
    * 二级封锁协议
    * 三级封锁协议
    * 两段锁协议，可能导致死锁

* 事务隔离级别

  * 未提交读 ：解决丢失修改问题。实现：通过对写操作加持续X锁实现，阻塞其他事务写但不阻塞读
  * 已提交读 解决脏读。实现：通过对写操作加持续-X锁，阻塞其他事务写和读
  * 可重复读 解决不可重复读。实现：通过对读操作加临时-S锁，阻塞写操作，对写操作加持续-X锁
  * 可串行化 解决幻读。实现：可以通过写操作加持续-X锁，读操作加持续-S锁

  通过乐观锁或悲观锁解决丢失修改问题：

  * 乐观锁通过增加版本号列（如时间戳）检查时间戳改变
  * 悲观锁则通过提交修改时，先加锁查询（select...for update nowait）是否有更新，然后提交修改

* 死锁

  * InnoDB处理死锁的方式是通过检测拥有最少行级排它锁的事务 进行回滚

* 事务日志

  * 存储引擎在修改数据时，只需修改内存拷贝中的数据，同时将该修改行为记录到硬盘上的事务日志中。不用管该修改什么时候持久到磁盘，可以提高事务执行效率。
  * 数据未写回磁盘而崩溃时，存储引擎重启后可以自动恢复。
  * 事务日志采取的是追加的方式

## SQL

where 过滤行 group by过滤分组

```sql
SELECT col, COUNT(*) AS num
FROM mytable
WHERE col > 2
GROUP BY col
HAVING num >= 2;
```

Group By默认会按照分组字段排序 可以通过制定Order By指定排序字段

```
SELECT col, COUNT(*) AS num
FROM mytable
GROUP BY col
ORDER BY num;
```

## 多版本并发控制MVCC

MVCC是MySQL中实现提交读和可重复读两种事务隔离级别的实现方式。

通过版本号实现，包括系统版本号和事务版本号，以及每行记录后有两个隐藏列 创建版本号和删除版本号

读数据分为快照读和当前读

## Next-Key Locks

MySQL的InnoDB存储引擎的一种锁实现，可以解决幻读，使用MVCC加Record Locks

Next-Key Locks可以锁定一个范围，通过record lock锁定记录的索引，通过Gap Locks锁定索引之间的间隙

select c from ... where c between 1 and 10 for update

**锁的算法**：

* 记录锁。加载索引记录上的锁，是行锁，使用索引作为where的过滤条件时，加记录锁；如果不用索引，则加表锁
* 间隙锁。锁定范围
* next-key lock。上述两种锁的结合，锁定当前行，以及当前行前边的间隙。

## InnoDB和MyISAM的区别

InnoDB特性：基于MVCC实现高并发，并实现四种事务隔离级别，默认级别是可重复读，并可以通过next-key lock策略防止幻读的出现，这种锁不仅锁行，还锁间隙，防止幻影行的插入。

InnoDB的表示基于聚簇索引建立的，对主键查询有很高的性能。二级索引必须包含主键列，所以主键列要尽可能小。

一些优化：磁盘读取数据时的可预测性预读、自动创建自适应hash索引、加速插入操作的插入缓冲区

InnoDB 支持事务处理与外键和行级锁，MyISAM是表锁，且不支持事务和外键

MyISAM强调的是性能，每次查询具有原子性，执行速度比InnoDB快

MyISAM的索引和数据是分开的，存放在内存中的是索引，且索引可以压缩，占用内存少

InnoDB存放在内存中的是索引+数据。

MyISAM支持全文索引，InnoDB不支持

**对比**：InnoDB适合写密集的表，MyISAM适合读密集的表

### 索引的优点

* 大大减少服务器需要扫描的数据行数
* 帮助服务器避免进行排序和分组，以及避免创建临时表
* 将随机IO变为顺序IO

### 索引的使用场景

* 非常小的表，全局扫描更有优势
* 大到中型的表，索引较有效

### 优化数据访问

减少请求的数据量；减少服务端扫描的行数（覆盖索引）

### 重构查询方式

1、切分大查询；

2、分解大连接查询

* 让缓存更高效
* 分解成多个单表查询
* 减少锁竞争
* 在应用层进行连接，更容易对数据库进行拆分

## 复制

主从复制：

* binlog线程，将主服务器对数据的更改写入log
* I/O线程：从主服务器读取二进制log，写入从服务器中的重放log（replay log）
* SQL线程：读取重放日志，并重放其中的SQL语句![img](https://github.com/CyC2018/CS-Notes/raw/master/pics/master-slave.png)

读写分离：主服务器读（实时性要求较高的读操作）/写 从服务器读

## 索引

聚集索引：聚集索引规定数据在表中的物理存储顺序，InnoDB引擎支持聚集索引

非聚集索引：使用二叉树，且叶子结点不存储数据行，而是存储指向数据行的指针

主索引和辅助索引，主索引为主键索引，叶子结点存储数据行，辅助索引叶子结点存放主键的值

覆盖索引：可以只通过辅助索引就可以查询到的记录，而不需要通过主键索引

联合索引：通过多个列组成索引

* 索引顺序是ab 则查询a或者 ab可以用联合索引 select from where a =  and b = 
* 联合索引的第二个好处是对第二个键值做了排序 。select from where a = order by b.