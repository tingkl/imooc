## spring-boot-devtool热部署

mybatis的mapper.xml文件修改也会热更新

1. 加入依赖

    ```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
    ```
2. 配置idea

    https://blog.csdn.net/u013938484/article/details/77541050


## 集成jsp

1. 加入依赖

    ```
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
    </dependency>
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
    </dependency>
    ```
2. 更改为starter-tomcat

   ```
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-tomcat</artifactId>
       <scope>provided</scope>
   </dependency>
   <!--<dependency>-->
       <!--<groupId>org.springframework.boot</groupId>-->
       <!--<artifactId>spring-boot-starter</artifactId>-->
       <!--<scope>provided</scope>-->
   <!--</dependency>-->
   ```
        
2. TplApplication继承SpringBootServletInitializer

    ```
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TplApplication.class);
    }
    ```
3. 配置jsp目录

   application.properties
   
   spring.mvc.view.prefix=/pages/
   spring.mvc.view.suffix=.jsp   
 
 
## idea打jar包

```
使用InteliJ IDEA导出jar包执行报错：找不到主清单属性
2016年09月20日 10:34:37 Quin-champion 阅读数：9479 标签： idea jar maven  更多
个人分类： IDE
报错原因是因为MANIFEST.MF文件下找不到MAIN-CLASS的属性
可以打卡导出的jar包MANIFEST.MF文件查看验证

解决方法：

再添加jar包的时候，修改DIRECT FOR MANIFEST.MF

idea默认是src/main/java

我们需要设置为src目录即可
``` 

## spring-boot打成jar包访问不了jsp

可以访问到html

https://blog.csdn.net/suph1990/article/details/77484800

需要打成war包

## tomcat部署war

```
Windows下启动tomcat，一般直接运行startup.bat，启动后能看到日志

Linux下直接启动./startup.sh但是查看不到启动日志信息，通过ps –ef|grep tomcat查看，实际已经启动

但是可以通过运行./catalina.sh run启动，就可以像windows中一样查看tomcat启动信息了
```
