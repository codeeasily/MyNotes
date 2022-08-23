> https://snailclimb.gitee.io/javaguide/#/docs/java/io/io-design-patterns

# 设计模式
## 装饰者
装饰器（Decorator）模式 可以在不改变原有对象的情况下拓展其功能。

装饰器模式通过组合替代继承来扩展原始类的功能，在一些继承关系比较复杂的场景（IO 这一场景各种类的继承关系就比较复杂）更加实用。

对于字节流来说， FilterInputStream （对应输入流）和FilterOutputStream（对应输出流）是装饰器模式的核心，分别用于增强 InputStream 和OutputStream子类对象的功能。

我们常见的BufferedInputStream(字节缓冲输入流)、DataInputStream 等等都是FilterInputStream 的子类，BufferedOutputStream（字节缓冲输出流）、DataOutputStream等等都是FilterOutputStream的子类。
```java
public BufferedInputStream(InputStream in) {
    this(in, DEFAULT_BUFFER_SIZE);
}

public BufferedInputStream(InputStream in, int size) {
    super(in);
    if (size <= 0) {
        throw new IllegalArgumentException("Buffer size <= 0");
    }
    buf = new byte[size];
}

```
## 适配器
适配器（Adapter Pattern）模式 主要用于接口互不兼容的类的协调工作，你可以将其联想到我们日常经常使用的电源适配器。

适配器模式中存在被适配的对象或者类称为 适配者(Adaptee) ，作用于适配者的对象或者类称为适配器(Adapter) 。适配器分为对象适配器和类适配器。类适配器使用继承关系来实现，对象适配器使用组合关系来实现。

IO 流中的字符流和字节流的接口不同，它们之间可以协调工作就是基于适配器模式来做的，更准确点来说是对象适配器。通过适配器，我们可以将字节流对象适配成一个字符流对象，这样我们可以直接通过字节流对象来读取或者写入字符数据。

InputStreamReader 和 OutputStreamWriter 就是两个适配器(Adapter)， 同时，它们两个也是字节流和字符流之间的桥梁。InputStreamReader 使用 StreamDecoder （流解码器）对字节进行解码，实现字节流到字符流的转换， OutputStreamWriter 使用StreamEncoder（流编码器）对字符进行编码，实现字符流到字节流的转换。

InputStream 和 OutputStream 的子类是被适配者， InputStreamReader 和 OutputStreamWriter是适配器。
```java
// InputStreamReader 是适配器，FileInputStream 是被适配的类
InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
// BufferedReader 增强 InputStreamReader 的功能（装饰器模式）
BufferedReader bufferedReader = new BufferedReader(isr);
```
## 适配器模式和装饰者模式的区别
装饰器模式 更侧重于动态地增强原始类的功能，装饰器类需要跟原始类继承相同的抽象类或者实现相同的接口。并且，装饰器模式支持对原始类嵌套使用多个装饰器。

适配器模式 更侧重于让接口不兼容而不能交互的类可以一起工作，当我们调用适配器对应的方法时，适配器内部会调用适配者类或者和适配类相关的类的方法，这个过程透明的。就比如说 StreamDecoder （流解码器）和StreamEncoder（流编码器）就是分别基于 InputStream 和 OutputStream 来获取 FileChannel对象并调用对应的 read 方法和 write 方法进行字节数据的读取和写入。
```java
StreamDecoder(InputStream in, Object lock, CharsetDecoder dec) {
// 省略大部分代码
// 根据 InputStream 对象获取 FileChannel 对象
ch = getChannel((FileInputStream)in);
}
```
适配器和适配者两者不需要继承相同的抽象类或者实现相同的接口。


## 工厂
NIO 中大量用到了工厂模式，比如 Files 类的 newInputStream 方法用于创建 InputStream 对象（静态工厂）、 Paths 类的 get 方法创建 Path 对象（静态工厂）、ZipFileSystem 类（sun.nio包下的类，属于 java.nio 相关的一些内部实现）的 getPath 的方法创建 Path 对象（简单工厂）。
```java
InputStream is = Files.newInputStream(Paths.get(generatorLogoPath));
```
## 观察者
NIO 中的文件目录监听服务使用到了观察者模式
