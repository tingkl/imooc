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
 
 