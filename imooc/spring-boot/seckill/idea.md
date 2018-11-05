# spring boot idea 自动编译

https://blog.csdn.net/diaomeng11/article/details/73826564

## 方案1

```$xslt
设置File ->Setting ->Compile：

勾选“Make project automatically”选项

使用快捷键ctrl+alt+shift+/，选择选项Registry

并找到选项"compller.automake.allow.when.app.running"并勾选

```

缺点: 有一点改动devtool就重启springboot了

## 方案2

### 1.确保F1-F9功能键好用

https://blog.csdn.net/gogdizzy/article/details/77943701

系统偏好设置》键盘》键盘tab页，勾选"将F1,F2等等用作标准功能键"

### 2.录制宏并设置快捷键

https://my.oschina.net/fdblog/blog/172229?from=singlemessage&isappinstalled=0

# annotation.processor

Error:java: Internal compiler error: java.lang.Exception: java.lang.NoClassDefFoundError解决

分下看这个跟Eclipse有关，可是这个不是Eclipse项目（没有Eclipse的Workspeace信息），后来查找发现跟Eclipse有关设置就是之前自己设置锅编译器，改过来问题得到解决。解决步骤：IDEA中File-->settings-->Bulid Execution Deloyment-->Complier-->Java Complier中的user complier有原来的Eclipse改为javac即可
