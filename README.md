# springcloud-microservices-template

Spring Cloud 微服务项目骨架

## TODO

功能点 | 功能描述 |  完成状态
---|---|---
API网关 | 身份验证、监控、限流、熔断 | ✔️
Job | 定时任务模块 | 开发中

## 组织结构

```bash
demo-parent
├── README.md        # 项目自述文档
├── common-dao       # 持久层，自动生成，一般不需要改动
├── common-lib       # 共同模块（util、redis、异常处理等）
├── config           # 通用配置
├── gateway          # 应用网关
├── auth             # 权限服务
└── pom.xml          # Parent pom
```

## 后端技术栈

技术 | 说明 | 官网
---|---|---
JDK  | JAVA8 开发工具包 | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
MySQL | 关系型数据库 | https://www.mysql.com
MyBatis | ORM 框架  | http://www.mybatis.org/mybatis-3/zh/index.html
MyBatis-plus | MyBatis 增强工具  | https://mp.baomidou.com
MyBatisGenerator | 数据层代码生成 | http://www.mybatis.org/generator/index.html
PageHelper | MyBatis 物理分页插件 | https://pagehelper.github.io
Druid | 数据库连接池 | https://github.com/alibaba/druid
Redis| 分布式缓存 | https://redis.io/
OSS | 对象存储  | https://github.com/aliyun/aliyun-oss-java-sdk
Lombok | 简化对象封装工具 | https://github.com/rzwitserloot/lombok
Maven | JAVA8 开发工具包 | https://maven.apache.org/download.cgi
FastJson | 快速的 JSON 解析器 | https://github.com/alibaba/fastjson
slf4j | Simple Logging Facade for Java  | http://www.slf4j.org
JWT | JWT登录支持 | https://github.com/jwtk/jjwt
Hibernator-Validator | 验证框架 | http://hibernate.org/validator/
Jenkins | 自动化部署工具 | https://github.com/jenkinsci/jenkins
Gitlab | 代码托管平台（私有）| https://gitlab.com/gitlab-org/gitlab
Spring Security | 认证和授权框架 OAuth2 | https://spring.io/projects/spring-security
Docker | 应用容器引擎 | https://www.docker.com/
Portainer | Docker Swarm 集群管理平台 | https://github.com/portainer/portainer
Spring Boot | 容器+MVC 框架 | https://spring.io/projects/spring-boot
Spring Cloud Gateway | 网关 | https://spring.io/projects/spring-cloud-gateway
Spring Cloud OpenFeign | 声明式服务调用 | https://cloud.spring.io/spring-cloud-openfeign/reference/html/
SpringFox | 自动生成文档框架 | http://springfox.github.io/springfox/
Swagger-UI | 文档生产工具 | https://github.com/swagger-api/swagger-ui
Knife4j | Swagger 文档聚合工具 | https://gitee.com/xiaoym/knife4j
p3c | JAVA 开发规范  | https://github.com/alibaba/p3c
hutool | 通用工具类库  | https://github.com/looly/hutool
modelmapper | dto 映射工具 | https://github.com/modelmapper/modelmapper
jetcache | 缓存框架 | https://github.com/alibaba/jetcache
easyexcel | Excel 工具库 | https://github.com/alibaba/easyexcel
K8s | 容器编排平台 | https://github.com/kubernetes
istio | ServiceMesh(服务网格) | https://github.com/istio/istio
Helm | Kubernetes 包管理 | https://github.com/helm/helm
Elasticsearch | 搜索引擎 | https://github.com/elastic/elasticsearch
RabbitMq | 消息队列 | https://www.rabbitmq.com/
Apollo | 分布式配置中心 | https://github.com/ctripcorp/apollo
ShardingSphere | 分库分表代理 | https://shardingsphere.apache.org/
seata | 分布式事务管理 | https://github.com/seata/seata
Sentinel | 熔断限流 | https://github.com/alibaba/Sentinel
Spring Cloud Bus | 消息总线 | https://spring.io/projects/spring-cloud-bus
Spring Boot Admin | 微服务应用监控 | https://github.com/codecentric/spring-boot-admin
Prometheus | Metrics 监控系统、告警系统、时序数据库 | https://github.com/prometheus
SkyWalking | Tracing 分布式追踪系统 | https://github.com/apache/skywalking
Grafana | 可视化监控工具 | https://github.com/grafana/grafana
EFK | 日志系统 | Elasticsearch+Filebeat/Fluentd+Kibana
rest assured | e2e API 测试 | https://github.com/rest-assured/rest-assured

## 前端技术栈

技术 | 说明 | 官网
----|----|----
Vue | 前端框架 | https://vuejs.org/
Vue-router | 路由框架 | https://router.vuejs.org/
Vuex | 全局状态管理框架 |	https://vuex.vuejs.org/
Element | 前端UI框架 | https://element.eleme.io
Axios | 前端HTTP框架| https://github.com/axios/axios
v-charts | 基于Echarts的图表框架 | https://v-charts.js.org/
Js-cookie | cookie管理工具 | https://github.com/js-cookie/js-cookie
single spa | 微前端解决方案 | https://github.com/single-spa/single-spa
乾坤 | 微前端解决方案 | https://github.com/umijs/qiankun
jest | 测试框架 | https://github.com/facebook/jest
