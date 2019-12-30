# Redis

> Redis 是现在最受欢迎的 NoSQL 数据库之一，Redis 是一个使用 ANSI C 编写的开源、包含多种数据结构、支持网络、基于内存、可选持久性的键值对存储数据库，其具备如下特性：

- 基于内存运行，性能高效
- 支持分布式，理论上可以无限扩展
- key-value 存储系统
- 开源的使用 ANSI C 语言编写、遵守 BSD 协议、支持网络、可基于内存亦可持久化的日志型、Key-Value 数据库，并提供多种语言的 API

## 相比于其他数据库类型，Redis 具备的特点是：

- C/S 通讯模型
- 单进程单线程模型
- 丰富的数据类型
- 操作具有原子性
- 持久化
- 高并发读写
- 支持 lua 脚本

## Redis 的应用场景有哪些？

Redis 的应用场景包括：缓存系统（“热点”数据：高频读、低频写）、计数器、消息队列系统、排行榜、社交网络和实时系统。
![Redis的应用场景](https://mmbiz.qpic.cn/mmbiz_png/C1uDMDqjn1ibM7BQTKib3CG7Q6UOvU5pwDSJV4LMicVzYFibU5MeQRpxBfTg9cW1ibHyBL6SL4O0BaCOHMJ2K8F0Ficg/640?tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

## Redis 的数据类型及主要特性

Redis 提供的数据类型主要分为 5 种自有类型和一种自定义类型，这 5 种自有类型包括：String 类型、哈希类型、列表类型、集合类型和顺序集合类型。
![Redis的数据类型及主要特性](https://mmbiz.qpic.cn/mmbiz_png/C1uDMDqjn1ibM7BQTKib3CG7Q6UOvU5pwD9mxLyTxPNaiaCBTWrzOm6GicDos75Pu22jYPmzbhx4Ps4iaBCYQS57ziaw/640?tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

## Redis 入门篇

- [Redis 零基础入门视频教程](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038841&idx=2&sn=efba466896b243770eb1c1a5dbb32e4b&chksm=872bbc80b05c3596ee2ab5bb10917943690ef846d7e031c416ca44f200cef31bb006e49b2c0d&scene=21#wechat_redirect)

- [Linux 安装 Redis 图文教程](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038868&idx=2&sn=edcc359ef1b629daf425a07bf0182f39&chksm=872bbcedb05c35fb38162b150439fb32d0957756fbb779155a352b518d54025b4bb3c1b600af&scene=21#wechat_redirect)

- [再来聊聊 Redis 到底是什么？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038896&idx=3&sn=6954c12ff003ee62fe61dff0ed1f07a2&chksm=872bbcc9b05c35df918c691e772b456d6525ffe612064fb3eeb5ebc27bfc6b765f1700380ff1&scene=21#wechat_redirect)

- [详解 Redis AOF 持久化](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039359&idx=3&sn=5f3f40b220e2a58020b524346ccbe4ad&chksm=872bbe86b05c3790858204e62b53f6cb598907a3ab3e89832c6485114d0357213513a90ed161&scene=21#wechat_redirect)

- [详解 Redis RDB 持久化](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039384&idx=4&sn=ef4b07f8969718a4c397a59c5834b8db&chksm=872bbee1b05c37f7fa6310855866a1d251df870f43177862cca2f08a7b7fad90b04c03afc011&scene=21#wechat_redirect)

- [Redis 常见的几种缓存模式](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038631&idx=4&sn=265ff6255318538d5f575c6821635aa8&chksm=872bbddeb05c34c8a13aaa2ec3bfe1468d3131409a7c42d582aadb473ca3db4ee4d35b573943&scene=21#wechat_redirect)

- [玩转 Redis 集群的搭建、命令](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039199&idx=3&sn=ccf0ad0aaa38fbb72c4b01d8cd5b3943&chksm=872bbf26b05c3630ae5c92d060999e8633ba757834c543400f3fcb07aa19dcea5eb3f944cf8e&scene=21#wechat_redirect)

- [说说 Redis 数据结构和常用命令](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038953&idx=3&sn=8b2943e8ec552828abcfbbd7f2e5a70d&chksm=872bbc10b05c3506bdbb271ca418f23297add7c07beb06b03ae4d91da53a06977669f8fcc9a9&scene=21#wechat_redirect)

- [后端开发都应该掌握的 Redis 基础](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454037143&idx=2&sn=0bb28d89ef073aac8c91e632f44bb821&chksm=872bb72eb05c3e3854dfbd824505efabf80e02dc9aba0e0d4dee6f6f637659fca76067cc93b8&scene=21#wechat_redirect)

- [刚接触学 Redis，看这一篇就够了！](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038818&idx=3&sn=7f14a3691d9bd667a006b842fd6418e5&chksm=872bbc9bb05c358d78622f9e0c39baf6ef78689d94e23f2deccbe12f02475621613894d43bf4&scene=21#wechat_redirect)

- [阿里巴巴官方 Redis 开发规范！](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038905&idx=3&sn=a9531abd2b906799e879049e7334be5b&chksm=872bbcc0b05c35d6e8bb94ce85511224b5940c35f9c65fab17475fc95baeacbf2893d35f5e81&scene=21#wechat_redirect)

## Redis 提升篇

- [Redis 的字符串是怎么实现的？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039155&idx=3&sn=7d6bddaa1a64f9899312744f9d14d51e&chksm=872bbfcab05c36dcbd585948aa38c7f4b601a630866067cce1caa7be05689ffcb9e993d49b87&scene=21#wechat_redirect)

- [Redis 主从复制以及主从复制原理](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039073&idx=3&sn=0d130bfb9c837aa527186e65c075ebd3&chksm=872bbf98b05c368e2929970c178715b00feac29bd4d10b863a98d7784ecd76dfb3929e4693cb&scene=21#wechat_redirect)

- [史上最全 Redis 高可用解决方案总结](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039008&idx=3&sn=06fdcda07c82ee0d0eebf3b1b4c3265d&chksm=872bbc59b05c354f90160d5c1930c04eac88e1552c9139f3c4f32747b92743225e737ff02091&scene=21#wechat_redirect)

- [一文深入了解 Redis 内存模型！](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038722&idx=3&sn=b2c7953e5a8c682a3067b1e3120c2a5c&chksm=872bbd7bb05c346db08ace8097ccd4636143fc7d3cec56e80dac63b3b42fcb6d19bf8cb54d47&scene=21#wechat_redirect)

- [Redis 导致应用卡死 bug 全过程](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039230&idx=3&sn=7820b40339fe508fbaaff1486476cad2&chksm=872bbf07b05c3611d0b3518c6eb1f1fe19096ece129a69cbd909e54ba6da4d33268f57d8062e&scene=21#wechat_redirect)

- [史上最全 Redis 总结，干货满满！](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039313&idx=3&sn=2d326c7278612313482ace8c334bf23f&chksm=872bbea8b05c37be280a76e3bd81d8c6257cd5aa56fad7002887a1f8059751a2b1b0b4faee1f&scene=21#wechat_redirect)

- [10 个正确使用 Redis 的技巧](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039036&idx=3&sn=c6f3b3e580ddd84d5d9055e4bdbe257b&chksm=872bbc45b05c355332ddecc0bbe759d211d42fc256b4b9d595dd1829dfa5e35939bec0fc393e&scene=21#wechat_redirect)

- [Redis 分布式锁的正确实现方式](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038643&idx=3&sn=411932a81bfecb92122b662282c1fc86&chksm=872bbdcab05c34dc44042b491ef7c6aa27d5e087c337b49c707e1d44b79579c748e506ba8599&scene=21#wechat_redirect)

- [Redis 源码学习之 Redis 事务](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038751&idx=3&sn=ae126cdc4d822ec61e29e029bdfa759d&chksm=872bbd66b05c34707fb228513ee032e6a10d7669e1188bb9f65bcfa7d7fb96a6d7763e5804b1&scene=21#wechat_redirect)

- [详解 Redis 的内存淘汰策略](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038673&idx=3&sn=7101629674f42fa82a329d455c066364&chksm=872bbd28b05c343e4d9cb64f1557b4deaa6aead4201ba99244e07fc4306c753ba11dab95d7f1&scene=21#wechat_redirect)

- [超详细揭秘 Redis 持久化，建议收藏！](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038320&idx=3&sn=7d5a898234421fd791a434c9716936b3&chksm=872bb289b05c3b9fe7a0b8e2ace61743aacbbbed8aef9313b42422388d94eda97bc1e115e9af&scene=21#wechat_redirect)

- [为什么单线程的 Redis 却能支撑高并发？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038706&idx=3&sn=fc743154afdc07bbd04aab977b5b301d&chksm=872bbd0bb05c341de49c90a1e3570bcd2f0a1ed4a7f32e678fd1662531a6906b612a015b0503&scene=21#wechat_redirect)

## Redis 应用篇

- [Redis 是如何实现点赞、取消点赞的？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038790&idx=3&sn=d383cdf87c7c7e598906aad551032f20&chksm=872bbcbfb05c35a925c6ced0a6aba7a89e95276620b464bb7c8e1ac34a8052a8e0d8eb514e7c&scene=21#wechat_redirect)

- [Redis 实践：网站搜索的热搜词](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039132&idx=3&sn=df88daf40961d16468b8a697a105fb16&chksm=872bbfe5b05c36f30f830146989c1be17538be8ba96d9d4c1fee5b694a3303f65c624df49578&scene=21#wechat_redirect)

- [如何用 Redis 做实时订阅推送的？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039081&idx=3&sn=98c76b3da2219ea5736d8ce9215124c8&chksm=872bbf90b05c36868925ee0eee8e5cbccbbfe9549b599ecafc55b43150fee2459179c798b07d&scene=21#wechat_redirect)

- [如何访问 Redis 中的海量数据？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039025&idx=3&sn=fd11ddf803e12f80fd60b06611e0145f&chksm=872bbc48b05c355e22d960e15ac4746122b3b78554f18a181306246f13feeb81703c188a087c&scene=21#wechat_redirect)

- [如何借助 Redis 实现秒杀系统？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039104&idx=3&sn=99d1ee6f4c43f6d24b648aadf4305a3b&chksm=872bbff9b05c36ef7d6a71c763489565e9b84353891ee1af183f20a34278fa2682aa316e7c3e&scene=21#wechat_redirect)

- [如何用 Redis 实现微博关注关系？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039296&idx=2&sn=07225159a0d37ec92f258837e9141067&chksm=872bbeb9b05c37af6df38e60a79e98232a0346b3a677ca854b3f5daf7c387451062967ddeb12&scene=21#wechat_redirect)

- [如何优雅地在 Redis 中使用 Lua 脚本](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039414&idx=3&sn=d48f548d460fd98e8e0356cecf5d83c0&chksm=872bbecfb05c37d9341ce7e8afcc2f31d5286a89ce39cee9c78e5435ed418741f7409793b0ae&scene=21#wechat_redirect)

- [一个基于 Redis 的限流系统的设计！](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039247&idx=3&sn=3df101aefe46e267096650d4d8ea3c75&chksm=872bbf76b05c36609813491177713b190467d2c45b14a2279629349286dfc49a1712cd152c09&scene=21#wechat_redirect)

- [Redis 应用场景，实现功能 “附近的人”](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038774&idx=3&sn=d7f544e3285a817d668324e51809eb18&chksm=872bbd4fb05c3459ed3e7aee8d19e7de0d4eee98ab268d93c0f815546ead78d4e4737c7d9d81&scene=21#wechat_redirect)

- [如何借助 Redis 实现实现排行榜功能？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039334&idx=3&sn=fc0c08297a6ce9523a60002fc874cf67&chksm=872bbe9fb05c3789a26632247486381d7414fb0ba358f792e1dac61f39685fdcbedcc8b79faf&scene=21#wechat_redirect)

- [Redis 的各项功能，都解决了哪些问题？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038972&idx=3&sn=8f2484f2bdf642e6671c1acfb666276f&chksm=872bbc05b05c35132475f802506d9a8d6cfe24dc359983710dfe01289eea3dc96f1a2ec14c44&scene=21#wechat_redirect)

- [Python 操作 Redis 的最佳实践](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039401&idx=4&sn=2d741cac5b4fc3e4343a519fc66a45e9&chksm=872bbed0b05c37c62ec20d17cd9182b27ba15acc056fd09a2d3086e31addf4700b3461705f10&scene=21#wechat_redirect)

## Redis 面试篇

- [分享 30 道 Redis 面试题，面试官能问到的我都找到了](https://mp.weixin.qq.com/s/aYj5Gy_xFkIyPlR48M96_g)
- [Redis 是如何实现点赞、取消点赞的？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038790&idx=3&sn=d383cdf87c7c7e598906aad551032f20&chksm=872bbcbfb05c35a925c6ced0a6aba7a89e95276620b464bb7c8e1ac34a8052a8e0d8eb514e7c&scene=21#wechat_redirect)

- [Redis 实践：网站搜索的热搜词](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039132&idx=3&sn=df88daf40961d16468b8a697a105fb16&chksm=872bbfe5b05c36f30f830146989c1be17538be8ba96d9d4c1fee5b694a3303f65c624df49578&scene=21#wechat_redirect)

- [如何用 Redis 做实时订阅推送的？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039081&idx=3&sn=98c76b3da2219ea5736d8ce9215124c8&chksm=872bbf90b05c36868925ee0eee8e5cbccbbfe9549b599ecafc55b43150fee2459179c798b07d&scene=21#wechat_redirect)

- [如何访问 Redis 中的海量数据？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039025&idx=3&sn=fd11ddf803e12f80fd60b06611e0145f&chksm=872bbc48b05c355e22d960e15ac4746122b3b78554f18a181306246f13feeb81703c188a087c&scene=21#wechat_redirect)

- [如何借助 Redis 实现秒杀系统？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039104&idx=3&sn=99d1ee6f4c43f6d24b648aadf4305a3b&chksm=872bbff9b05c36ef7d6a71c763489565e9b84353891ee1af183f20a34278fa2682aa316e7c3e&scene=21#wechat_redirect)

- [如何用 Redis 实现微博关注关系？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039296&idx=2&sn=07225159a0d37ec92f258837e9141067&chksm=872bbeb9b05c37af6df38e60a79e98232a0346b3a677ca854b3f5daf7c387451062967ddeb12&scene=21#wechat_redirect)

- [如何优雅地在 Redis 中使用 Lua 脚本](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039414&idx=3&sn=d48f548d460fd98e8e0356cecf5d83c0&chksm=872bbecfb05c37d9341ce7e8afcc2f31d5286a89ce39cee9c78e5435ed418741f7409793b0ae&scene=21#wechat_redirect)

- [一个基于 Redis 的限流系统的设计！](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039247&idx=3&sn=3df101aefe46e267096650d4d8ea3c75&chksm=872bbf76b05c36609813491177713b190467d2c45b14a2279629349286dfc49a1712cd152c09&scene=21#wechat_redirect)

- [Redis 应用场景，实现功能 “附近的人”](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038774&idx=3&sn=d7f544e3285a817d668324e51809eb18&chksm=872bbd4fb05c3459ed3e7aee8d19e7de0d4eee98ab268d93c0f815546ead78d4e4737c7d9d81&scene=21#wechat_redirect)

- [如何借助 Redis 实现实现排行榜功能？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039334&idx=3&sn=fc0c08297a6ce9523a60002fc874cf67&chksm=872bbe9fb05c3789a26632247486381d7414fb0ba358f792e1dac61f39685fdcbedcc8b79faf&scene=21#wechat_redirect)

- [Redis 的各项功能，都解决了哪些问题？](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454038972&idx=3&sn=8f2484f2bdf642e6671c1acfb666276f&chksm=872bbc05b05c35132475f802506d9a8d6cfe24dc359983710dfe01289eea3dc96f1a2ec14c44&scene=21#wechat_redirect)

- [Python 操作 Redis 的最佳实践](http://mp.weixin.qq.com/s?__biz=MzA5NzgzODI5NA==&mid=2454039401&idx=4&sn=2d741cac5b4fc3e4343a519fc66a45e9&chksm=872bbed0b05c37c62ec20d17cd9182b27ba15acc056fd09a2d3086e31addf4700b3461705f10&scene=21#wechat_redirect)
