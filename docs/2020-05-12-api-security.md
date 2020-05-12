# 微服务架构下的鉴权

## 场景

* 用户-服务的鉴权
* 服务-服务的鉴权
* 外部应用接入

## 四种方案

* 单点登录

* 分布式session

* 客户端token。
  * 通过短期令牌解决身份注销问题。
  * 基于JWT实现

* 客户端token与API网关结合
  * 所有请求经过网关，请求时网关将原始用户token转换我内部会话Id的token。
  * 通过网关注销用户token

## 微服务常见安全认证方案

* http基本认证，将用户名密码放在Authorization头中
* 基于session的认证，将认证过的session id存在客户端的cookie中
* 基于token的认证。将认证过的token放在http请求头中进行调用
  * 好处是服务端不用存储session，性能较好
  * 支持移动设备
  * 支持跨程序调用。（cookie不能跨域）

基于Token有两种方案，jwt和OAuth2.0。

## token认证之jwt

jwt token注销，常采用短期令牌。避免注销后token依旧可用的风险。

## token认证之Oauth 2.0

> 参考http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html

四种模式：

* 授权码模式
* 简化模式
* 密码模式
* 客户端模式

> https://www.jianshu.com/p/15d0a1c366b3
>
> 总结：服务间鉴权采用jwt，对外权限通过api网关和OAuth结合管理。

## 权限系统的设计

> https://blog.csdn.net/OmniStack/article/details/77881185?locationNum=10&fps=1

### 权限模型

RBAC模型：Role Based Access Control

### 场景需求

1. 用户系统多样
2. 不能影响服务的吞吐量以及可用性
3. 老系统接入

### 设计思路

* 权限系统微服务化
* 权限微服务本身也需接入原有体系，走阿里云SLB->API 网关->权限微服务。如果是web端的权限严重，还需走Nginx代理。
* 通过自定义注解，减少对业务代码的侵入。
* 高可用，分布式的权限缓存。基本权限和角色等数据存在mysql数据库。权限验证数据则通过redis集群缓存。**但是当用户关联的角色，权限更新时，需要让对应的缓存失效**
* 对于业务服务的API权限，支持接口加注解进行拦截验证；对于界面权限，通过权限系统控制。

### 包含内容

* 录入和绑定界面
* 权限数据导入导出
* 权限扩展，结合OAuth和Spring session等

## API权限的实现

### 注解

@Permission定义接口权限name、label和description

@Group定义权限组，如每个接口下的所有方法归到一个组

@UserId，业务服务需要在API上加入用户Id的参数，用于切面验证权限。

@UserType，用户类型。支持多个用户系统。

### 扫描入库和拦截

基于Spring AutoProxy实现

* 创建PermissionAutoProxy.java，继承Spring的org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator类，步骤如下：

1. 在构造方法里设置好Interceptor通用代理器（即实现了MethodInterceptor接口的拦截类PermissionInterceptor.java）；

2. shouldProxyTargetClass用来决定是接口代理，还是类代理。在权限定义的时候，其实我们还支持把注解加在实现类上，而不仅仅在接口上，这样灵活运用注解放置的方式。

3. getAdvicesAndAdvisorsForBean是最核心的方法，用来决定哪个类、哪个方法上的注解要被扫描入库，也决定哪个类、哪个方法要被代理。

   ```java
   // 返回拦截类，拦截类必须实现MethodInterceptor接口，即PermissionInterceptor
   protected abstract Class<? extendsMethodInterceptor> getInterceptorClass();
   
   // 返回接口或者类的方法名上的注解，如果接口或者类中方法名上存在该注解，即认为该接口或者类需要被代理
   protected abstract Class<? extendsAnnotation> getMethodAnnotationClass();
   
   // 扫描到接口或者类的方法名上的注解后，所要做的处理
   protected abstract voidmethodAnnotationScanned(Class<?> targetClass);
   ```

* 创建PermissionScanListener.java，实现Spring的org.springframework.context.ApplicationListener.ApplicationListener<ContextRefreshedEvent>接口，步骤如下:

1. onApplicationEvent(ContextRefreshedEvent event)方法里实现入库代码。
2. 在业务服务的Spring容器启动的时候，将自动触发权限数据入库的事件。

## 角色管理

角色是一组API权限的集合，角色与微服务名挂钩，角色组包含若干角色。

角色管理通过界面操作。

角色和权限是多对多的关系，用户和角色是多对多的关系。

## 权限验证

通过远程**RPC方式**的调用，即通过权限API的方式注入，进行远程调用。

**Rest调用**的验证方式

```http
http://host:port/authorization/authorize/{userId}/{userType}/{permissionName}/{PermissionType}/{serviceName}
```

通过User ID、User Type、Permission Name（权限名，映射于对应的方法名）、Permission Type（区别是**API权限还是界面权限**），Service Name（应用名）来判断是否被授权，返回结果是true或者false。

## 待优化

userId和userType需要侵入每一个接口，如何解决？

## 参考

* https://coolshell.cn/articles/19395.html
* https://blog.csdn.net/OmniStack/article/details/77881185?locationNum=10&fps=1
* https://www.jianshu.com/p/15d0a1c366b3
* http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html