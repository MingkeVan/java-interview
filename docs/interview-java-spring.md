# Spring

## Spring经典课程

spring 基于注解驱动课程，强烈推荐，讲透spring基于注解的使用和原理。 https://www.bilibili.com/video/av71252572?p=7

##  什么是IOC和DI，优缺点？

IOC，控制反转。顾名思义，控制权发生了反转。以前A依赖B，是由A主动创建B对象，现在则是A需要B的时候，由IOC容器主动创建一个B对象给A。

DI，依赖注入。实现IOC的方式就通过依赖注入。

**优点**：便于解耦

**缺点**：引入第三方IOC容器，且对象通过反射生成，损失运行效率，需要额外配置。

## spring 容器有几种，有什么区别？

BeanFactory 工厂类

ApplicationContext - ApplicationContext扩展了BeanFactory接口。

| BeanFactory          | ApplicationContext |
| -------------------- | ------------------ |
| 懒加载               | 即时加载           |
| 显式提供资源         | 自己创建和管理     |
| 不支持国际化         | 支持国际化         |
| 不支持基于依赖的注解 | 支持基于依赖的注解 |

## Spring bean的生命周期

* 容器调用构造函数创建对象
* 设置对象属性
* XXXAware接口执行对应的setXXX方法
  * BeanNameAware
  * BeanFactoryAware
  * ApplicationContextAware
* 后置处理器 在初始化方法之前
* 初始化的几种方式，按照从上到下的顺序：
  * postconstructor注解
  * 继承intializingBean接口 afterPropertiesSet方法
  * @Bean(initMethod="") 指定初始化方法
* 后置处理器 在初始化方法之后
* 销毁的几种方法，按照从上到下的顺序：
  * @PreDestroy
  * 继承disposabBean接口 
  * @Bean(destroyMethod="") 指定销毁方法

https://github.com/Snailclimb/JavaGuide/blob/master/docs/system-design/framework/spring/SpringInterviewQuestions.md#55-spring-%E4%B8%AD%E7%9A%84-bean-%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F

![img](https://pic1.zhimg.com/80/v2-baaf7d50702f6d0935820b9415ff364c_hd.jpg)

![img](https://pic3.zhimg.com/80/754a34e03cfaa40008de8e2b9c1b815c_hd.jpg)

## Spring Bean的五种作用域

singleton prototype request sesssion global-session

## spring bean的注入方式

* 属性注入方式

* 构造函数注入

* 工厂方法注入

* XXXaware注入。继承XXXaware可以获取iioc容器中的某些底层bean，获取方法是通过XXXAwareProcessor后置处理器取出bean。

  autowired 可以用在变量、方法、构造函数上。

## spring-boot 注册bean的几种方式

https://blog.csdn.net/majunzhu/article/details/79199752

* @Configuration+@Bean的方式注入 
* (@Controller @Service @Repository @Component) +@ComponentScan
* @Import注解可以导入其他类中bean
  * 导入其他类，bean的默认id是全类名
  * 导入继承ImportSelector的class，通过selectImport方法返回组件的全类名数组
  * 导入ImportBeanDefinitionRegister.class,手动获取IOC容器，并注入自定义id的bean
* 通过spring提供的FactoryBean
  * 默认获取到的是工厂bean调用getObject创建的对象
  * 要获得工厂bean本身，需要在id前加个&。

## Spring AOP原理

https://www.imooc.com/video/15690