# Java相关

## Java基础

### final的好处

> https://www.jianshu.com/p/1f4b0f98cbf1 final详解

* final修饰的变量不可变，可以保证线程安全；
* final可以避免指令重排序，进而避免变量从构造方法中溢出（详细见参考文章中的**final域重排序规则**）；
* final修饰局部变量，作为编译时常量，编译器会进行常量折叠。
* 使用上，访问局部变量比访问成员变量更快，且保证了该变量被误修改，增强代码可读性。

**java this逃逸问题**

this逃逸是指在构造函数完成之前，其他线程就持有该对象的引用。

``` java

public class FinalReferenceEscapeDemo {
    private final int a;
    private FinalReferenceEscapeDemo referenceDemo;

    public FinalReferenceEscapeDemo() {
        a = 1;  //1
        referenceDemo = this; //2 此处会导致this逃逸
    }

    public void writer() {
        new FinalReferenceEscapeDemo();
    }

    public void reader() {
        if (referenceDemo != null) {  //3
            int temp = referenceDemo.a; //4
        }
    }
}

```



通常出现在在构造函数中启动线程或注册监听器。

### 基本数据类型和封装类的区别

* int是基本数据类型，仅仅代表数的范围；integer是int的封装类，包含很多与数据有关的方法
* int的默认值是0；而integer的默认值是null
* int的变量名和数值存储在栈内存中；而integer是引用堆内存的一个对象
* int不需要实例化；integer需要实例化，实例化方法有三种
  * integer i = 12 自动装箱 
  * integer i = integer.valueof(12) 手动装箱
  * integer i = new Integer（12） 构造函数
* Integer.valueof(i) 当i的范围为-128 到127时，会存放在常量池中；成为Integer的常量缓冲池，实现是静态内部类IntegerCache

### 抽象类和接口的区别

* 抽象类和接口都不能被实例化

* 抽象类可以有具体 的方法实现，接口不能

* 抽象类可以有成员变量和普通的成员方法，且成员变量可以是任何类型的；而接口中的成员变量只能是public static final类型

* 抽象方法必须是public 或 protected，接口中方法必须是public

* 抽象类可以有静态代码块和静态方法；接口中不能含有静态代码块和静态方法

* 抽象类是单继承的

### Java子类的变量初始化顺序

父类静态变量、静态代码块-》子类静态变量 静态代码块-》父类构造代码块 构造函数-》子类构造代码块 构造函数

### Java的线程池和七个参数

* corePoolSize（核心线程数，线程数目达到corePoolIS则后，会把到达的任务放到缓存队列中）

* maximumPoolSize(最大线程数) 
* keepAliveTime 空闲线程保留时间
* TimeUnit 空闲线程的保留时间单位
* BlockingQueue 工作的阻塞队列
* queueCapacity 队列的容量
* ThreadFactory 用于创建线程的工厂类
* rejectedExecutionhandler 队列已满  而且任务量大于最大线程的异常处理策略 

### 线程的几种状态

* new

* runnable（running ready）获取时间片正在执行 或者调用yield放弃时间片
* waiting 调用Object.wait() thread.join() locksupport.unpark()
* timed_waiting Thread.sleep() object.waiting(time) Thread.join(time) locksupport.parkNanos
* blocked 等待获取锁 或者object.wait()后 重新竞争锁
* terminated（线程运行结束）

### sleep/wait/notify/notifyall区别

> https://juejin.im/post/5d7142d35188255a5c6247e9
>
> * wait: 释放当前锁，阻塞直到被notify或notifyAll唤醒，或者超时，或者线程被中断(InterruptedException)
>
> * notify: 任意选择一个（无法控制选哪个）正在这个对象上等待的线程把它唤醒，其它线程依然在等待被唤醒
>
> * notifyAll: 唤醒所有线程，让它们去竞争，不过也只有一个能抢到锁
>
> * sleep: 不是Object中的方法，而是Thread类的静态方法，让当前**线程持有锁**阻塞指定时间

> [https://mayinjian.wordpress.com/2017/11/24/java-%E4%B8%AD-wait-%E5%92%8C-sleep-%E7%9A%84%E5%8C%BA%E5%88%AB/](https://mayinjian.wordpress.com/2017/11/24/java-中-wait-和-sleep-的区别/)

1. Object类是java所有类的超类，有notify() 、notifyAll() 、wait()、wait(long)，如果调用了对象的wait()方法，那么所在的线程便会处于该对象的等待池中，会释放它锁持有的对象的管程和锁，等待池中的线程不会去竞争该对象的锁，当有线程调用了该对象的notifyAll()，被唤醒的线程便会进入该对象的锁池中，锁池中的线程会去竞争该对象，被唤醒的线程相比普通获取锁的线程没有任何优先权，所以这时候就会有不公平的竞争，不管先后，随机一个线程取得该对象。这非公平锁和synchronize 锁以及 ReentranceLock的默认构造函数一样，不过ReentrantLock支持传入true最为构造函数的参数，即可构造一个公平锁。
2. Thread.sleep不会导致锁行为的改变，如果当前线程是拥有锁的，那么Thread.sleep不会让线程释放锁。
3. Thread.sleep和Object.wait都会暂停当前的线程，对于CPU资源来说，不管是哪种方式暂停的线程，都表示它暂时不再需要CPU的执行时间。OS会将执行时间分配给其它线程。区别是，调用wait后，需要别的线程执行notify/notifyAll才能够重新获得CPU执行时间。但是sleep 只要休眠时间到了，直接继续运行，没有让出锁。

线程的状态切换

![img](https://img-blog.csdnimg.cn/20181120173640764.jpeg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3BhbmdlMTk5MQ==,size_16,color_FFFFFF,t_70)



### Java中的引用器（强弱软虚）

* 强引用：Object o = new Object() 只要强引用存在，GC永远不会回收被引用的对象

* 弱引用：用于描述非必需对象。WeakReference类。当垃圾回收器工作时，不管内存是否足够，都会回收只被弱引用关联的对象。

  - 实例：ThreadLocalMap类

  - ThreadLocal中，获取到线程私有对象是通过线程持有的一个threadLocalMap，然后传入ThreadLocal当做key获取到对象的，这时候就有个问题，**如果你在使用完ThreadLocal之后，将其置为null，这时候这个对象并不能被回收，因为他还有 ThreadLocalMap->entry->key的引用**，直到该线程被销毁，但是这个线程很可能会被放到线程池中不会被销毁，这就产生了内存泄露，jdk是通过弱引用来解决的这个问题的，**entry中对key的引用是弱引用，当你取消了ThreadLocal的强引用之后，他就只剩下一个弱引用了，所以也会被回收。**

    作者：王镜鑫

    链接：https://www.zhihu.com/question/37401125/answer/337717256

    来源：知乎

    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

* 软引用：还有用。但非必需。SoftReference类。当内存紧张时，可以被GC掉。可以很好的解决OOM的问题，适合用来做网页缓存和图片缓存等。现在已经不用，大多用LRUCache

* 虚引用：实际不影响对象的回收。 设置虚引用的目的是希望能在这个对象被收集器回收时收到一个系统通知。

### ThreadLocal实现

threadLocal内部维护了一个Entry数组，并基于开放地址法寻找插入地址

threadLocal使用不当会出现内存泄漏问题，原因是threadLocalMap的key为弱引用，当threadLocal本身被置为null后，key的弱引用就开始发挥作用，当key被垃圾回收掉之后，因为没有手动调用threadLocal.remove操作导致value没有被置为null,存在threadLocalMap指向value的强引用，所以出现内存泄漏。**threadLocal的get put remove等操作都会清理一部分key为null但value不为null的entry，可以一定程度上防止内存泄漏**

### 线程池

> 参考：https://redspider.gitbook.io/concurrent/di-san-pian-jdk-gong-ju-pian/12
>
> java线程池ThreadPoolExecutor八种拒绝策略浅析：http://www.kailing.pub/article/index/arcid/255.html

why？创建线程和销毁线程的花销比较大。因为创建线程会进行一次系统调用，从用户态切换到内核态。在内核中，操作系统会创建线程的管理单元，设置好线程的执行环境，然后切换回用户态执行线程函数；销毁线程时，需要再进入一次内核态，。。。。

how？从创建好一定数量的线程池中拿去线程；方便管理，可以编写线程池管理代码对池中的线程进行统一管理。

what？线程池主要包含任务组和工作队列

* newSingleThreadExecutor 

* newFixedThreadPool(控制线程最大并发数) 堆积的请求处理队列可能会耗费非常大的内存，甚至OOM，使用无界队列处理请求

* newCachedThreadPool
  * 创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。
  * （可缓存线程池）线程最大数为Integer.MAX_VALUE,可能会创建数量非常多的线程，甚至OOM。
* newScheduledThreadPool（定长线程池）

线程池的工作队列：

三种策略：

直接提交策略，即SynchronousQueue，新的请求优先加入队列，而不是直接创建新线程；当有第二个请求需要加入队列时，因为无法加入则需要创建新线程处理。

使用无界队列：新请求到来时优先创建线程，如果添加任务的速度远远超过处理任务的速度，可能很快就爆了。

使用有界队列：一般不用，但可以防止资源耗尽的发生。

* ArrayBlockingQueue 有界阻塞队列 FIFO
* LinkedBlockingQueue 吞吐量高于Arra.. FIFO（Executors.newFixedThreadPool()使用的队列，无界队列）
* SynchronousQueue 不存储元素的阻塞队列，某次添加元素后，必须等待其他线程取走才能继续添加。吞吐量高于LinkedBlockingQueue，用于Executors.newCachedThreadPool

**线程池线程数量**：

* **CPU密集型任务**：设置为N+1，减少上下文切换
* **I/O密集型任务**：设置为2N，因为处理IO时不占用CPU

## java集合类

**线程不安全**：ArrayList，HashMap通过操作数实现fail-fast机制

* hashmap arraylist等不能在循环中进行put或者remove操作，必须调用迭代器进行操作，才能保证modCount和expectedCount相等，否则会报并发异常。
* 不能多个线程同时操作进行遍历的put remove操作

**原因**：hashmap hasNext方法会判断操作数是否一致。用for循环倒序删除也可以。

### HashMap为什么线程不安全？

多线程遇到rehash的时候会出现循环连接，get，put的时候寻找next节点出现死循环，导致cpu100%

### HashMap、HashTable、ConcurrentHashMap区别

* 线程安全不同

### HashMap、LinkedHashMap区别

HashMap无法保证插入顺序和访问顺序一致，LinkedHashMap通过维护一条双向链表解决了该问题

### LinkedHashMap实现LRUCache

LRUcache添加元素主要逻辑：

* cache未满时，插入已存在元素，将元素移到最后；
* cache未满时，插入不存在元素，直接插入；
* cache已满时，删除头结点，然后插入。

实现可以同ArrayList模拟。

```java
import java.util.LinkedHashMap;  
import java.util.Map;  
public LRUCache<K, V> extends LinkedHashMap<K, V> {  
  private int cacheSize;  
  public LRUCache(int cacheSize) {  
    super(16, 0.75, true);  
    this.cacheSize = cacheSize;  
  }  

  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {  
    return size() >= cacheSize;  
  }  
} 
```

## java IO、NIO和AIO

> https://zhuanlan.zhihu.com/p/23488863 Java NIO浅析

* IO以流的方式处理数据，NIO以块的方式处理数据
* 通道：通道是双向的，流是单向的。通道包括套接字通道、filechannel、serversocketchannel（用于给新进的tcp连接创建socketchannel）
* 缓冲区buffer：BIO是将数据直接写入stream对象中，而NIO的数据操作都是在缓冲区中进行的。
* NIO被称作非阻塞IO，通过实现IO多路复用中的适配器模型。线程通过selector监听多个通道channel（设置为非阻塞）的事件，从而使一个线程可以处理多个连接。线程拿到就绪的channel集合后，需要同步通过selectionkey进行后续的IO操作。
  * 只有**套接字**channel才能配置为非阻塞，FileChannel不可以。
* AIO：在NIO基础上引入以异步通道的概念，并提供了异步文件通道和异步套接字通道的实现。进行读写操作，只需调用read或write方法就可以，异步完成。完成后会主动调用回调函数
  * BIO：同步阻塞，适用于连接数目比较小且固定的架构；
  * NIO：同步非阻塞，适用于连接数目多且连接比较短的架构；
  * AIO：异步非阻塞，适用于连接数目多且连接比较长的架构；
  * 同步阻塞IO : 用户进程发起一个IO操作以后，必须等待IO操作的真正完成后，才能继续运行。
  * 同步非阻塞IO: 用户进程发起一个IO操作以后，可做其它事情，但用户进程需要经常询问IO操作是否完成，这样造成不必要的CPU资源浪费。 
  * 异步非阻塞IO: 用户进程发起一个IO操作然后，立即返回，等IO操作真正的完成以后，应用程序会得到IO操作完成的通知。类比Future模式。

## java反编译

https://www.coderxing.com/javap-verbose.html javap -verbose -p

## java原子类

java7中原子类通过cas解决并发操作，但是若是多个线程竞争，则会由于循环重试造成性能降低；

java8中引入fetch-and-add操作,底层是Xadd指令，可以达到直接修改并使其他线程立即可见的目的。**注意下方代码是java8 AtomaticInteger的实现，其中虽然使用了CompareAndAwapInt,但是jvm虚拟机维护了一些内部函数(会将一系列自动生成的指令替换为原始函数调用)，并会进行相应的优化，getAndAddInt底层使用xadd。这样做的目的是为了防止底层不支持。** 

参考：

* http://ashkrit.blogspot.com/2014/02/atomicinteger-java-7-vs-java-8.html?m=1

* http://ashkrit.blogspot.com/2017/07/java-intrinsic-magic.html?m=1

```
/**
     * Atomically sets to the given value and returns the old value.
     *
     * @param newValue the new value
     * @return the previous value
     */
    public final int getAndSet(int newValue) {
        return unsafe.getAndSetInt(this, valueOffset, newValue);
    }
    
    public final int getAndSetInt(Object var1, long var2, int var4) {
        int var5;
        do {
            var5 = this.getIntVolatile(var1, var2);
        } while(!this.compareAndSwapInt(var1, var2, var5, var4));

        return var5;
    }
```

## Java内存模型

### 内存模型

Java内存模型定义了线程从内存中读写变量的规则。

java中线程不能直接操作内存中的变量，而是操作自己的工作内存。线程间交换变量要通过主内存进行。

### 内存区域

线程私有:程序计数器、虚拟机栈、本地方法栈

线程共有：堆、方法区

## Java 类的生命周期：加载、连接（验证、准备、解析）、初始化、卸载

* Java类加载（jvm虚拟机找到需要加载的类，把类的信息加载到jvm方法区，并在堆中实例化对应的class对象，作为方法区中这个类的信息入口）
  * 双亲委派机制
  * 类加载器
    * BootStrap类加载器：加载jvm自身需要的类
    * 扩展类加载器：加载`<JAVA_HOME>/lib/ext`目录下 
    * 系统类加载器（app classloader）：加载classpath下的类
* 连接
  * 验证：验证类是否合法，确保加载的类能被jvm执行
  * 准备：为类中的静态变量设置初始值（基本数据类型的0值）（**声明为final的静态变量会初始化为设置值并放入常量池**）
  * 解析：将类中所有的类名（接口名）、字段名、方法名转换成具体的内存地址
* Java类初始化
  * https://www.cnblogs.com/Qian123/p/5713440.html
    * 执行父类的静态代码块，并初始化父类静态成员变量
    * 执行子类的静态代码块，并初始化子类静态成员变量
    * 执行父类的构造代码块，执行父类的构造函数，并初始化父类普通成员变量
    * 执行子类的构造代码块， 执行子类的构造函数，并初始化子类普通成员变量
  * 直接引用和被动引用
    * 直接引用会引起类的初始化
      * 通过new实例化对象、读取或设置类中的静态变量、调用类的静态方法
      * 通过反射执行以上三种行为
      * 初始化子类的时候，触发父类的初始化
      * 作为程序入口（main方法）
    * 被动引用不会引起类的初始化
      * 引用父类的静态字段，只会引起父类的初始化，而不会引起子类的初始化。
      * 定义类数组，不会引起类的初始化。
      * 引用类的常量，不会引起类的初始化
* 类的卸载
  * 该类所有的实例都已经被回收，也就是java堆中不存在该类的任何实例。
  * 加载该类的ClassLoader已经被回收。
  * 该类对应的java.lang.Class对象没有任何地方被引用，无法在任何地方通过反射访问该类的方法。

## java 类加载

* 双亲委派模型
* 如何实现自定义类加载器
  * 重写loadclass和findclass方法，findclass中是自定义的类加载逻辑
* 如何破坏双亲委派模型
  * 自定义类加载器直接在loadclass中实现类加载逻辑，而不是首先交给父类加载器
  * JNDI、JDBC等服务是由启动类加载器加载的，但是需要反向访问子类加载器中的方法，此时引入了线程上下文类加载器，可以通过父类加载请求子类加载器完成加载动作；
  * OSGI实现模块化部署，是通过自定义类加载器机制，该机制不再是树状结构，而是网状结构。

## Lock与Synchronized

区别在于：lock支持可中断、可绑定多个条件、支持公平锁。

* http://blueskykong.com/2017/05/14/lock/

  * 锁的种类有：可重入锁 读写锁 可中断锁 公平锁 独占锁
  * ReentrantLock:可重入 可中断 公平或非公平 独占锁
  * Synchronized：可重入 不可中断 非公平锁 独占锁
  * Semaphore： 共享锁

* Lock实现：

  * ReentrantLock 内部使用CLH等待队列 当调用lock方法时 会将该线程放入等待队列中， 如果是公平锁 只有队头获得锁，如果是非公平，不管是不是队头都能获取锁
  * acquire 实现：首先调用tryacquire()尝试获取锁，成功则返回；失败则通过addWaiter()方法加入到CLH等待队列末尾，然后调用aquireQueued()获取锁，这时线程会进入CLH队列中休眠等待，直到获取锁了才返回；如果休眠中断，线程需要自己再产生一个中断selfInterrupt()
  * 休眠与唤醒是LockSupport.park(thread)和LockSupport.unpark(thread)。线程休眠是通过park挂起，等待其他线程unpark当前线程。当release一个锁时，会同时释放该线程节点的后继节点。
  * 由于线程被唤醒有两种方式：一种是被其他线程调用unpark();另一种是线程中断，这个时候如果获取不到锁，则需要重新产生中断
  * 非公平锁与公平锁的实现区别在于：非公平锁获取锁时，若锁处于空闲状态，则直接获取锁；若获取失败则加入CLH同步队列，进行排队等待；而公平锁获取锁时，即使锁处于空闲状态，线程也要判断自己是不是队头才能获取锁。**重要：非公平锁主要是针对最新尝试获取锁的线程，可以绕过排队，直接尝试获取锁，但是获取锁失败后依然需要排队等待**

* 锁升级：无锁状、偏向锁、轻量级锁（自旋锁)、重量级锁

  * 轻量级锁：使用CAS操作避免重量级锁使用互斥量的开销

  * 锁升级过程：

    * 偏向锁偏向于第一个获取锁对象的线程，锁进入偏向状态

    * 此时如果有另一个线程尝试获取锁对象，偏向状态结束。撤销偏向或者升级为轻量锁。

    * 升级为轻量锁时，修改标志位，且在虚拟机栈帧中创建lock record，并尝试更新对象的mark word指针指向该lock record；

    * 如果cas操作失败，会首先检查对象的mark word是否指向当前线程的虚拟机栈，是说明当前线程已经拥有锁对象，否说明锁对象被其他线程抢占。此时轻量锁不再有效，要膨胀为重量级锁。

      ![image-20200222173557300](C:\Users\YingpeiWu\AppData\Roaming\Typora\typora-user-images\image-20200222173557300.png)

## semaphore

* https://blog.csdn.net/javazejian/article/details/76167357

## AQS使用与原理

* https://www.cnblogs.com/chengxiao/p/7141160.html
* https://www.cnblogs.com/waterystone/p/4920797.html
* http://blog.11034.org/2016-06/reentrantlock.html
* https://www.jianshu.com/p/cc308d82cc71
* ![img](https://upload-images.jianshu.io/upload_images/2615789-02449dc316fe1de6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/837/format/webp)
* AQS锁的种类：
  * 独占锁:  ReentrantLock
  * 共享锁：Semaphore\ReentrantReadWriteLock.ReadLock\CyclicBarrier\CountdownPatch
* CLH同步队列：
  * 用于管理等待锁的线程队列
  * 非阻塞的FIFO队列，使用链表实现，并通过CAS和自旋锁保证节点插入和移除的原子性
  * 是CLH队列锁的一种变种，CLH队列锁自旋在前一个结点对象上，当前一个节点islocked=false，且前一个节点不为null的情况下，跳出自旋
  * condition.await()将线程加入condition等待队列中，而signal将等待队列中的队头取出放在CLH同步队列中，等待下次该线程的await()操作或者lock.unlock()才能解除线程的阻塞状态；
* Condition条件队列和CLH同步队列
  * Condition条件队列是一个单向不含头结点的队列，而CLH同步队列是双向的含头结点的队列
  * 条件队列只在condition。await和signal起作用。同步队列在获取锁的时候起作用，失败进入同步队列为，成功
  * condition.await 将已获得锁的头结点从同步队列转到条件等待队列。condition.signal则是从等待队列中取出，转到同步队列中。
  * https://www.jianshu.com/p/28387056eeb4 condition详解
  * 自旋获取锁示意图：![img](https://upload-images.jianshu.io/upload_images/2615789-3fe83cfaf03a02c8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/790/format/webp)

## 深入剖析java并发之阻塞队列LinkedBlockingQueue与ArrayBlockingQueue

* https://blog.csdn.net/javazejian/article/details/77410889
* https://www.cnblogs.com/dragonsuc/p/5167285.html blockingqueue详解
* blocking queue可以用于线程之间传输数据 一些线程作为生产者 一些线程用作消费者
* 位于current包下
* arrayBlockingQueue在生产者写入数据和消费者获取数据用的是同一个锁 这是因为写入和获取足够快，引入锁反而复杂，LinkedBlockingQueue用的两把锁 putlock和takelock，高并发下吞吐量较大
* arrayblockingQueue的长度是固定的，添加删除元素不能产生或销毁额外的对象实例；LinkedBlockingQueue的长度是可变的，添加删除元素需要生成新的node节点，在高并发下，gc工作会受较大影响
* lock关联了两个condition：notfull和notempty（通过lock.newcondition（）获得）。生产者线程添加元素时首先获取锁，再尝试添加元素，如果同步队列已满，调用notfull.await()方法（condition是当前lock的condition），挂起当前线程，释放锁，并把当前线程添加到notfull的condition等待队列中；当消费者线程调用condition.signal()唤醒线程时，会将等待队列的头结点取出，并添加到CLH同步队列中。lock会根据当前是公平锁还是非公平锁操作，公平锁的情况是lock从condition队列中取出线程，判断是不是队头，进行唤醒；非公平锁的情况下，从等待队列中取出的线程不管是不是队头都能获得锁

##  垃圾收集器

GC优化相关参数：-Xms，-Xmx，-XX：NewRatio,-XX:NewSize,-XX:SurvivorRatio

串行、并行、吞吐量优先、并发标记清除、G1

jdk1.7、1.8默认垃圾收集器是parallel Scavenge(新生代)+Parallel Old（老年代）

jdk1.9 G1

### 新生代收集器

* Serial收集器：简单而高效，是运行在client模式下的虚拟机的默认新生代收集器；
  * 主要用于单线程并且内存很小的环境
* ParNew收集器：运行在server模式的虚拟机首选的新生代收集器，Serial收集器的多线程版本
  * Serial和ParNew的新生代都是采用复制算法、老年代版本采用标记整理算法
  * jvm默认的收集器
* Parallel Scavenge收集器：“吞吐量优先”收集器，标记整理

### 老年代收集器

* CMS收集器：Concurrent Mark Sweep。第一款并发收集器，采用*标记-清除*算法
  * 初始标记（直接与root关联的对象，快）、并发标记（遍历，慢）、重新标记（较慢）、并发清除
  * 初始标记和重新标记时会暂停应用线程
  * 并发低停顿收集器。
  * **优点**：并发收集、低停顿
  * **缺点**：牺牲吞吐量、无法清除浮动垃圾
* G1收集器：Garbage First。相比CMS的改进为：
  * 基于"标记整理"，不会产生空间碎片。
  * 精确控制停顿。
  * 设计用来处理大于4GB内存的应用程序，适用于堆内存很大的情况，将堆内存分割成不同的区域，并发的进行垃圾回收
