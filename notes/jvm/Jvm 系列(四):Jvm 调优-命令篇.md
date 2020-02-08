运用jvm自带的命令可以方便的在生产监控和打印堆栈的日志信息帮忙我们来定位问题！虽然jvm调优成熟的工具已经有很多：jconsole、大名鼎鼎的VisualVM，IBM的Memory Analyzer等等，但是在生产环境出现问题的时候，一方面工具的使用会有所限制，另一方面喜欢装X的我们，总喜欢在出现问题的时候在终端输入一些命令来解决。所有的工具几乎都是依赖于jdk的接口和底层的这些命令，研究这些命令的使用也让我们更能了解jvm构成和特性。

Sun JDK监控和故障处理命令有jps jstat jmap jhat jstack jinfo下面做一一介绍


## jps
JVM Process Status Tool,显示指定系统内所有的HotSpot虚拟机进程。

### 命令格式

```  xml
jps [options] [hostid]
```

### option参数
> - -l : 输出主类全名或jar路径
> - -q : 只输出LVMID
> - -m : 输出JVM启动时传递给main()的参数
> - -v : 输出JVM启动时显示指定的JVM参数

其中[option]、[hostid]参数也可以不写。

### 示例

```  xml
$ jps -l -m
  28920 org.apache.catalina.startup.Bootstrap start
  11589 org.apache.catalina.startup.Bootstrap start
  25816 sun.tools.jps.Jps -l -m
```

## jstat
jstat(JVM statistics Monitoring)是用于监视虚拟机运行时状态信息的命令，它可以显示出虚拟机进程中的类装载、内存、垃圾收集、JIT编译等运行数据。

### 命令格式

```  xml
jstat [option] LVMID [interval] [count]
```