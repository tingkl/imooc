## 集成html

1. 使用starter即可

   ```
   <!--<dependency>-->
       <!--<groupId>org.springframework.boot</groupId>-->
       <!--<artifactId>spring-boot-starter-tomcat</artifactId>-->
       <!--<scope>provided</scope>-->
   <!--</dependency>-->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter</artifactId>
       <scope>provided</scope>
   </dependency>
   ```
2. 配置html目录

   application.properties
   ```
   # html
   spring.mvc.view.prefix=/
   spring.mvc.view.suffix=.html 
   ```
3. post form

   应该redirect:xxx返回html, 不能直接返回xxx.html

 
 