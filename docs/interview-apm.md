# APM概述

## 1、监控目标

需求：

1. 响应时间性能调优，根据调用链获得请求耗时瓶颈，是数据库还是某个业务方法。（可视化）
2. 调用链和日志分析的结合（比如监控到ERROR事件后，需要同时查看日志和调用链查找具体细节）
3. 服务健康情况
4. 服务依赖情况（最近一个月，最近一周？）

问题：

1. 调用链如何和日志分析结合（如日志能否记录APM中的traceID，或者APM中能直接看到日志）
2. 运维复杂度（存储，内存，依赖的组件）
3. 与前端如何打通结合

## 2、监控内容

1. Metrics
2. Logging
3. Tracing

## 3、监控对象

1. 点（指标）
   * 性能指标
     * 吞吐量（skywalking）
     * 服务可用性（skywalking）
     * 响应时间（skywalking/splunk）
     * 慢接口（skywalking/splunk）
   * 业务指标
     * 接口调用次数(splunk)
     * 访问最多的ip(splunk)
     * 访问最多的userId(splunk)
     * ...
2. 线（trace）
   * 调用链
     * traceId查询（skywalking/splunk）
     * 请求参数（skywalking/splunk）
     * 链路各部分耗时占比（skywalking）
   * 服务依赖（skywalking）
   * 服务健康（skywalking）

## skywalking

传统监控：进程监控+日志收集分析