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

