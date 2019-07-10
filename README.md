# mall
# 采用消息队列解决高并发下单案例, 基于Java语言来开发（JDK1.8） #

## Features ##
1. 采用微服务框架Spring Boot，Spring Cloud，消息队列RabbitMQ搭建解决高并发下单的案例.
2. 集成Spring Cloud的Eureka注册中心，Config分布式配置中心，Ribbon和RestTemplate，Feign负载均衡组件，Hystrix熔断隔离限流降级组件，Zipkin链路跟踪组件等，提高调用服务的简便性.
3. 基于Netty 4.x实现WebSocket长连接服务，支持在线聊天(群聊，私聊)功能，支持百度云LSS在线直播，支持高并发，高性能.
4. 集成Swagger-ui可视化界面可以快速体验.

## Prerequisite ##
1. JDK 1.8+
2. Maven 3.5.x
3. Git版本控制

## Quick Start ##
- Clone & Build
> git clone https://github.com/fengjingwei/mall.git
> 
> cd mall
> 
> mvn -DskipTests clean install -U

- execute sql
>  [https://github.com/fengjingwei/mall/blob/master/sql/mall_create_tables.sql](https://github.com/fengjingwei/mall/blob/master/sql/mall_create_tables.sql)

## Support ##
- 如有任何问题欢迎微我
- ![](https://i.imgur.com/8HEEH6x.jpg)
