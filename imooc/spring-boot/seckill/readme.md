

Error:java: Internal compiler error: java.lang.Exception: java.lang.NoClassDefFoundError解决

分下看这个跟Eclipse有关，可是这个不是Eclipse项目（没有Eclipse的Workspeace信息），后来查找发现跟Eclipse有关设置就是之前自己设置锅编译器，改过来问题得到解决。解决步骤：IDEA中File-->settings-->Bulid Execution Deloyment-->Complier-->Java Complier中的user complier有原来的Eclipse改为javac即可