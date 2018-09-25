# mall
# 采用消息队列解决高并发下单案例, 基于Java语言来开发（JDK1.8） #

## Features ##
1. 采用微服务框架spring boot，spring cloud，消息队列rabbitmq搭建解决高并发下单的案例.
2. 集成spring cloud的Eureka注册中心，config配置中心，Ribbon，Feign负载均衡组件提高调用服务的简便性.
3. 集成Netty 4.x实现websocket即时通讯模块，支持百度云LSS在线直播.
4. 集成swagger-ui可视化界面可以快速体验.

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
