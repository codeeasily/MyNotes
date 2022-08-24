Dubbo官方文档：https://dubbo.apache.org/zh/overview/what/overview/

# Dubbo介绍
Apache Dubbo 是一款 RPC 服务开发框架，用于解决微服务架构下的服务治理与通信问题，官方提供了 Java、Golang 等多语言 SDK 实现。

使用 Dubbo 开发的微服务原生具备相互之间的远程地址发现与通信能力， 利用 Dubbo 提供的丰富服务治理特性，可以实现诸如**服务发现、负载均衡、流量调度等**服务治理诉求。

Dubbo 被设计为高度可扩展，用户可以方便的实现流量拦截、选址的各种定制逻辑。

# 优势：
从设计之初就是为了解决超大规模微服务集群实践问题。

阿里巴巴、中国平安、携程等通过多年的大规模生产环境流量对 Dubbo 的稳定性与性能进行了充分验证。
- 开箱即用
  - 易用性高，如 Java 版本的面向接口代理特性能实现本地透明调用
  - 功能丰富，基于原生库或轻量扩展即可实现绝大多数的微服务治理能力
- 面向超大规模微服务集群设计
  - 极致性能，高性能的 RPC 通信协议设计与实现
  - 横向可扩展，轻松支持百万规模集群实例的地址发现与流量治理
- [高度可扩展](https://dubbo.apache.org/zh/overview/what/extensibility/)
  - 调用过程中对流量及协议的拦截扩展，如 Filter、Router、LB 等
  - 微服务治理组件扩展，如 Registry、Config Center、Metadata Center 等
- 企业级微服务治理能力
  - 国内共有云厂商支持的事实标准服务框架
  - 多年企业实践经验考验，[参考用户实践案例](https://dubbo.apache.org/zh/users/)