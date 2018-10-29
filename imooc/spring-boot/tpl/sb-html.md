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
3. post form返回html不支持

   只能用get 方式提交form，才能正常返回html   
   
   这只是针对form提交，ajax不所谓了，不会返回html 
 
 