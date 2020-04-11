# Linux

## 指令

ls -l : 按行打开所有文件

ls -a: 显示包含隐藏文件在内的所有文件

ls -la : 显示所有

ls -lh:可以显示文件大小

ls -lS: 根据文件大小排序

ls -ltr：根据修改日期排序

Linux相关：

top查看进程资源占用情况；

netstat 查看端口

ps aux | grep java 查看进程

kill -9 pid 强制终止 kill -3 pid 生成线程dump文件

使用JDK自带的Jconsole，可视化工具，可以检测内存、死锁等

Jstack是Jdk自带的命令行工具，可以查看线程dump

jmap打印堆dump

jvisualvm用来加载dump文件进行分析，也可以远程监控服务器。



**inode：**

硬盘格式化的时候，操作系统会将硬盘分为两个区域、数据区和inode区（存放指向block的索引）

df -i 查看inode节点总数和已经使用的数量

stat命令查看某个文件的inode信息 ，

通过文件名打开文件过程：

系统内部这个过程分成三步：首先，系统找到这个文件名对应的inode号码；其次，通过inode号码，获取inode信息；最后，根据inode信息，找到文件数据所在的block，读出数据。

读权限r只能获取文件名；执行权限x可以读取inode节点的信息

硬链接可以使多个文件名指向相同inode，软连接相当于快捷方式
