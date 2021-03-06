[https://blog.csdn.net/zhangjin530/article/details/53306708](https://blog.csdn.net/zhangjin530/article/details/53306708)

[JVM调优总结](http://www.importnew.com/19275.html)

[线上问题排查技巧](https://blog.csdn.net/u011734144/article/details/77568871?locationNum=8&fps=1)

[Java线上服务问题排查](https://www.cnblogs.com/mfmdaoyou/p/7349117.html)

[线上问题排查](https://hacpai.com/article/1472285294159)

[垃圾优先型垃圾回收器](http://www.oracle.com/technetwork/cn/articles/java/g1gc-1984535-zhs.html)

[MAT](http://inter12.iteye.com/blog/1407492)

Sun HotSpot VM，是JDK和Open JDK中自带的虚拟机，也是目前使用范围最广的Java虚拟机。<br/>

### JVM内存分布<br/>
程序计数器：是一块较小的内存空间，可以看作是当前线程所执行的字节码的行号指示器。程序中的分支、循环、跳转、异常处理、线程恢复等基础功能都需要依赖这个计数器完成。由于多线程是通过线程轮流切换并分配处理器执行时间的方式来实现的，故该区域为线程私有的内存。<br/>
虚拟机栈：描述的是Java方法执行的内存模型，用于存储局部变量表、操作数栈、动态链接、方法出口等<br/>
堆：是Java虚拟机所管理的内存中最大的一块，Java堆是被所有线程共享的一块内存区域，在虚拟机启动时创建，存放所实例，也是垃圾收集器管理的主要<br/>
方法区：用于存放已被虚拟机加载的类信息、常量、静态变量、即时编译后的代码等数据。HotSVM针对该区域也进行GC，主要是常量回收以及类<br/>

### JVM内存分配策略<br/>
对象的内存分配，在大方向上，是在Java堆上进行分配。<br/>
大多数情况下，对象在新生代Eden区中分配，当Eden区没有足够空间进行分配时，虚拟机将发起一次Minor GC。<br/>
大多数情况下，大对象直接进入老年代，虚拟机提供了参数来定义大对象的阀值，超过阀值的对象都会直接进入老年代。<br/>
经过多次Minor GC后仍然存活的对象（长期存活的对象），将进入老年代。虚拟机提供了参数，可以设置阀值。<br/>

### JVM垃圾回收算法<br/>
标记-清除算法：首先标记出所有需要回收的对象，在标记完成后统一回收所有被标记的对象。<br/>
复制算法：将可用内存按容量划分为大小相等的两块，每次只使用其中的一块。当一块内存用完了，将还存另外一块上面，然后在把已使用过的内存空间一次清理掉。<br/>
标记-整理算法：标记过程与“标记-清除”算法一样，但后续步骤不是直接对可回收对象进行清理，而是让所一端移动，然后直接清理掉端边界以外的内存。<br/>
分代收集算法：一般是把Java堆分为新生代和老年代，根据各个年代的特点采用最适当的收集算法。新生代都发现有大批对象死去，选用复制算法。老年代中因为对象存活率高，必须使用“标记-清理”或“标记-整理”算法来进行回收。<br/>

### 垃圾收集器<br/>
Serial收集器：是一个单线程的收集器，只会使用一个CPU或一条收集线程去完成垃圾收集工作，在进行垃圾收集时，必须暂停其他所有的工作线程，直到它收集结束。<br/>
ParNew收集器：是Serial收集器的多线程版本，除了使用多条线程进行垃圾收集之外，其余行为与Serial收集器完全一样。<br/>
CMS收集器：是一种以获取最短回收停顿时间为目标的收集器。过程分为以下四个步骤：<br/>
    初始标记<br/>
    并发标记<br/>
    重新标记<br/>
    并发清除<br/>

### JVM常见启动参数<br/>
-Xms / -Xmx — 堆的初始大小 / 堆的最大大小<br/>
-Xmn — 堆中年轻代的大小<br/>
-XX:-DisableExplicitGC — 让System.gc()不产生任何作用<br/>
-XX:+PrintGCDetails — 打印GC的细节<br/>
-XX:+PrintGCDateStamps — 打印GC操作的时间戳<br/>
-XX:NewSize / XX:MaxNewSize — 设置新生代大小/新生代最大大小<br/>
-XX:NewRatio — 可以设置老生代和新生代的比例<br/>
-XX:PrintTenuringDistribution — 设置每次新生代GC后输出幸存者乐园中对象年龄的分布<br/>
-XX:InitialTenuringThreshold / -XX:MaxTenuringThreshold：设置老年代阀值的初始值和最大值<br/>
-XX:TargetSurvivorRatio：设置幸存区的目标使用率<br/>

### JAVA类生命周期<br/>
Java类从被加载到虚拟机内存中开始，到卸载出内存为止，它的整个生命周期包括：加载、验证、准备、解析、初始化、使用、卸载七个阶段。

### JVM类加载<br/>
启动（Bootstrap）类加载器：是用本地代码实现的类装入器，它负责将 <Java_Runtime_Home>/lib下面的类库加载到内存中（比如rt.jar）。由于引导类加载器涉及到虚拟机本地实现细节，开发者无法直接获取到启动类加载器的引用，所以不允许直接通过引用进行操作。<br/>
标准扩展（Extension）类加载器：是由 Sun 的 ExtClassLoader（sun.misc.Launcher$ExtClassLoader）实现Java_Runtime_Home >/lib/extjava.ext.dir指定位置中的类库加载到内存中。开发者可以直接使用标准扩展类加载器。<br/>
系统（System）类加载器：是由 Sun 的 AppClassLoader（sun.misc.Launcher$AppClassLoader）实现的。径（CLASSPATH）中指定的类库加载到内存中。开发者可以直接使用系统类加<br/>
双亲委派机制描述 ：某个特定的类加载器在接到加载类的请求时，首先将加载任务委托给父类加载器，依次递归，如果父类加载器可以完成类加载任务，就成功返回；只有父类加载器无法完成此加载任务时，才自己去加载。

### JVM调优<br/>
查看堆空间大小分配（年轻代、年老代、持久代分配）<br/>
垃圾回收监控（长时间监控回收情况）<br/>
线程信息监控：系统线程数量<br/>
线程状态监控：各个线程都处在什么样的状态下<br/>
线程详细信息：查看线程内部运行情况，死锁检查<br/>
CPU热点：检查系统哪些方法占用了大量CPU时间<br/>
内存热点：检查哪些对象在系统中数量最大<br/>