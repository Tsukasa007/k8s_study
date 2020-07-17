#JDK 版本差异
###老的 JVM 版本（JDK 8u131以前）是无法感知容器的资源限制的。
###从JDK 8u131开始，在JDK 9中，JVM可以透明地了解Docker的CPU限制。
###顺着 JDK 8 Update Release Notes 查看，JDK 8u191 提供了更完善的 docker 容器支持。


Memory 限制
Java SE 8u131 和 JDK9
对于Docker内存限制，最大Java堆的透明设置还有一些工作要做。要告诉JVM在没有通过 -Xmx 设置最大Java堆的情况下注意Docker内存限制，需要两个JVM命令行选项：

-XX:+UnlockExperimentalVMOptions 和 -XX:+UseCGroupMemoryLimitForHeap
-XX:+UnlockExperimentalVMOptions 是必需的，因为在将来的版本中，目标是透明地标识Docker内存限制。

 java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap 

当使用这两个JVM命令行选项并且未指定 -Xmx 时，JVM将查看Linux cgroup配置，这是Docker容器用于设置内存限制的配置，以便透明地调整最大Java堆大小。

仅供参考，Docker容器也使用cGroup配置来限制CPU。

Java SE 8u191 和 JDK10
添加了三个新的JVM选项，以允许Docker容器用户更细粒度地控制将用于Java堆的系统内存量：

-XX:InitialRAMPercentage　　　　#初始百分比
-XX:MaxRAMPercentage　　　　　　 #最大百分比
-XX:MinRAMPercentage　　　　    #最小百分比
这些选项替换已弃用的分数形式（-XX:InitialRAMFraction、-XX:maxmRamFraction和-XX:MinRAMFraction）。


#总结
 - CPU
    + java5/6/7/8u131以前：手动设置jvm相关的选项，如：
        - ParallelGCThreads
        - ConcGCThreads
        - G1ConcRefinementThreads
        - CICompilerCount / CICompilerCountPerCPU
    + java8u131+ 和 java9+
        - java 8u131+ 和 java 8u191以前：--cpuset-cpus
        - java 8u191+： UseContainerSupport默认开启
    + java 10+:
        -使用最新版就好了，UseContainerSupport默认开启
 - Memory
    + java5/6/7/8u131以前：务必设置内存选项
    + java8u131+ 和 java9+
        - java 8u131+ 和 java 8u191以前：-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap
        - java 8u191+： UseContainerSupport默认开启
     + java10+
        - 使用最新版就好了，UseContainerSupport默认开启
