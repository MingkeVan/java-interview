# spring

# 1. spring 循环依赖

## 1.1 循环依赖场景

### 1.1.1 构造器注入

```java
@Service
public class A {
		public A (B b) {
    }
}

@Service
public class B {
		public B(A a) {
    }
}
```

构造器注入引起的循环依赖，spring无法解决。 构造器注入的优势？

> 原因：spring解决循环依赖依靠的是bean的中间态，而这个中间态指的是已经实例化，但还没初始化的状态。而构造器是完成实例化的东西，spring没法解决。

### 1.1.2 field属性注入(setter 方法注入)

spring通过三级缓存解决循环依赖

### 1.1.3 prototype field属性注入循环依赖

```java
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class A {
		@Autowired
		private B b;
}

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class B {
    @Autowired
    private A a;
}
```

上面这种方式启动时是不会报错的，因为scope为多例时，默认不会初始化，只有使用时才会初始化。

使用@lazy是延迟初始化，真正用到的时候，还是会报异常。

## spring创建bean的流程

- createBeanInstance: 调用构造方法实例化对象
- populateBean： 填充属性，对bean的依赖属性进行注入，@Autowired注入
- initializeBean： 初始化属性（postConstruct,initializeBean,initMethod）

从上面步骤可以看出，field属性注入情况有可能在第2步发生循环依赖。

## Spring容器的三级缓存

- singletonObjects: 用于存放完全初始化的bean
- earlySingletonObjects: 提前曝光的单例对象的cache，存放原始的bean对象（尚未填充属性），用于解决循环依赖；
- singletonFactories：单例对象工厂的cache，存在bean工厂对象，用于解决循环依赖。

reference：

[一文告诉你Spring是如何利用"三级缓存"巧妙解决Bean的循环依赖问题的【享学Spring】](https://cloud.tencent.com/developer/article/1497692)