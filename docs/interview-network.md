# 网络

## cookie/session的机制与安全

* https://harttle.land/2015/08/10/cookie-session.html（机制与安全 讲的非常透彻）
* https://blog.csdn.net/malimingwq/article/details/79689050
* https://juejin.im/post/59d1f59bf265da06700b0934（有例子）
* session存在服务器端 内存 或者 Redis 甚至数据库
* session存储完会生成sessionID 并通过setcookie字段 添加cookie
* cookie防篡改 是通过签名技术 对每个cookie项添加签名
* 通常cookie中不存敏感信息 而是只存sessionID 敏感信息存在session中
* cookie不可跨域名使用 但是同一个一级域名下的二级域名(wenku.baidu.com和zhidao.baidu.com)可以通用 可以通过cookie.setDomain(".baidu.com")设置 ,这个cookie就可以在多个二级域名间共享，但是不影响其他cookie
* cookie的路径 比如登录cookie只能在登录页面使用 可以通过cookie.setPath("/login/")，这样这个cookie就只能在login页面使用
* cookie的安全性
  * cookie.setSecure(true) 确保cookie只能通过HTTPS传输
  * cookie.setHttpOnly(true)可以防止XSS攻击 即通过js脚本获取cookie 

## http1.0 http1.1 http2.0 https

* http1.0:需要使用keep-alive参数手动建立长连接。规定浏览器和服务器保持比较短时间的链接，浏览器每次请求都和服务器经过三次握手和慢启动建立TCP链接。
* http1.1：
  * 默认长连接，即允许多次利用单个tcp连接，避免重复握手、慢启动创建连接
  * 支持流水线：复用一个tcp连接，但是若干个请求是串行化处理的，一旦某请求超时，就会出现线头阻塞
  * 支持同时打开多个TCP连接
  * 支持虚拟主机：多个站点可以共享同一个ip和端口
  * 新增状态码100：支持只发送header信息。客户端接收到100，说明有权限，再接着发送请求body
  * 支持分块传输编码
* http2.0：
  * 多路复用：多个请求可以在一个连接上并行执行
  * header压缩：减少数据体积
  * 服务端推送：客户端请求数据时，顺便将一些客户端需要的资源推送到客户端，避免客户端创建更多连接

## http

http请求报文包含请求行 首部行 空行 实体主体

* 请求行（方法+url+http版本）
* 首部行
  * host：url所在的主机
  * connection：是否在连接后关闭
* 实体主体

**put和post的区别**：

使用两者取决于Restful服务时是否幂等，如果需要创建多个对象用post，需要创建单个对象用put

* put用于上传对象到服务器，是幂等操作，对一个uri传输一个对象，具体是创建还是更新（覆盖已有的对象）由返回状态码表示。

* post用于上传对象的时候，不是幂等操作，会产生额外的对象那个

**HTTP/1.0 与 HTTP/1.1**

* 默认长连接
* 支持同时打开多个tcp连接
* 增加100状态码
* 支持管线化处理
* 支持虚拟主机（可以使一台主机拥有多个域名，逻辑上可以看做多个服务器）
* 支持分块传输编码

**https**:

采用隧道进行通信，http先和ssl通信，ssl再和tcp通信

* http的安全性问题：
  * 使用明文，可能被窃听
  * 没有身份认证，通信方的身份可能遭遇伪装
  * 报文可能被篡改
* 校验ca证书：
  * ca证书中包括公钥和hash算法，通过公钥对私钥加密的hash值进行解密，再对该值通过hash算法生成新的hash值，与原hash值比较，相等则通过验证

* https通过认证仿伪装，通过加密防窃听和防篡改
  * 加密采用对称加密和非对称加密
    * https采用对称加密和非对称加密混合的方式进行加密，
      * 通过非对称加密产生三个随机数和ca证书校验，得到对话密钥
        * 客户端首先产生一个随机数1，并将自己支持的ssl版本号和加密算法告诉服务器
        * 服务器确认双方的加密算法，并生成随机数2，把ca证书和随机数2 一同返回给客户端
        * 客户端对ca证书进行校验，通过后产生随机数3，并用公钥对随机数3进行加密，发送给服务端
        * 服务端对随机数3 进行解密，得到真正的随机数3.客户端和服务端利用这三个随机数生成对话密钥，之后就利用对话密钥进行加解密，采用的加密算法是对称加密AES。
      * 之后采用对称加密AES算法进行通信
  * 认证采用证书的方式

## 网络

子网掩码：子网掩码是32位二进制值，它屏蔽掉IP地址中的一部分，可以分离处网络地址和主机地址 ip地址的前三个字节，末位为0.利用子网掩码可以分离出主机地址（子网掩码取反后与ip与运算）和网络地址（子网掩码与ip与运算）

要使用子网，必须配置子网掩码。一个 B 类地址的默认子网掩码为 255.255.0.0，如果 B 类地址的子网占两个比特，那么子网掩码为 11111111 11111111 11000000 00000000，也就是 255.255.192.0。

上面的子网共有2^2=4-2 = 2个 ，即01 和 10

缺省子网掩码：

* A类：255.0.0.0
* B类：255.255.0.0
* C类：255.255.255.0

ip地址编址方式：

* 分类ABCD
* 子网划分

## 计算机网络体系结构

![img](https://github.com/CyC2018/CS-Notes/raw/master/pics/426df589-6f97-4622-b74d-4a81fcb1da8e.png)

![img](https://github.com/CyC2018/CS-Notes/raw/master/pics/d4eef1e2-5703-4ca4-82ab-8dda93d6b81f.png)

## ping

ping是ICMP的一个重要应用，主要用来测试两台主机间的连通性。

ping的原理是通过向目标主机发送echo请求报文，目的主机收到报文后发送echo回答报文。

## TCP与UDP

UDP首部字段只有8个字节 ：源端口、目的端口、长度、checksum

TCP首部字段有32个字节：源端口 目的端口、序号（发送时） 确认号（接受时） 确认ack 同步syn 终止fin 窗口

tcp三次握手：

* 发送方发出SYN	seq =x  从closed状态进入SYN_SENT状态 
* 接收方收到发送方报文 发出 ACK和SYN seq=y ack=x+1 由listen状态进入SYN_RCVD状态
* 发送方收到 发出ACK seq = x+1 ack=y+1，并进入established状态
* 接收方收到后，进入established状态

第三次握手的原因是为了防止客户端的滞留请求多次发送到服务器，导致打开多个连接

tcp四次挥手：

> https://app.getpocket.com/read/2930773017
>
> * time_wait状态会持续2MSL时长，一是为了防止接受方未收到ack，重发fin报文；二是因为报文有效期为2MSL，避免重启发送方后收到之前的报文。
> * **LAST_ACK**会自动消失。
> * close_wait会占用接受方的文件描述符，当接受方出现大量close_wait状态的连接时，将无法建立新连接。（接收方需要监听发送方连接套接字的EPOLLRDHUP事件，并close文件描述符）

![img](https://pocket-image-cache.com//filters:no_upscale()/https%3A%2F%2Fpic4.zhimg.com%2Fv2-cca52ad65861f73fc6dbb5a80bf09fd3_r.jpg)

* 发送方发送fin 进入**FIN_WAIT1**状态
* 接收方发送ack ，并从**established**状态进入**close_wait**状态
* 发送方收到ack进入**FIN_WAIT2**状态
* 接收方发送fin报文，进入**LAST_ACK**状态(接受方主动调用close方法)
* 发送方发出ack报文，并进入time_wait状态（持续2MSL）。（若服务器没有收到ack，会重发fin报文；收到则进入closed状态）



tcp通过超时重传进行可靠传输，通过滑动窗口实现流量控制

tcp拥塞控制：慢开始、拥塞避免、快重传、快恢复

**TCP和UDP的区别：**

**DNS**：









# 博客

https://news.ycombinator.com/item?id=22273224

https://eli.thegreenplace.net/

jvm系列文章 https://shipilev.net/jvm/anatomy-quarks/

搜索引擎 https://www.iaria.org/conferences2018/filesDBKDA18/AndreasSchmidt_Tutorial_SearchEngine.pdf