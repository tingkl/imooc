## spring boot打war包 

1. 添加spring-boot-starter-tomcat的provided依赖

    provided运行时是不会打进来的，因为运行时是有tomcat的，provided是一个编译时依赖

    ```
     <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
    ```
2. 添加maven-war-plugin插件

    ```
    <build>
        <finalName>${project.artifactId}</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <configuration>
                    <failOnMissingWebXml>false</failOnMissingWebXml>
                </configuration>
            </plugin>
        </plugins>
    </build>

    ```

3. packaging->war

    ```
    <packaging>war</packaging>
    ```
    
4. extends SpringBootServletInitializer   

    ```
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MiaoshaApplication.class);
    }
    ```
    
5. mvn clean package

6. tomcat部署war

   miaosha.war会解压到miaosha目录，默认context-path为miaosha
   
   http://localhost:8080/miaosha/login/to_login
   
   miaosha重命名为ROOT或者更改context-path
   
   http://localhost:8080/login/to_login
   
   
## spring boot打jar包   

1. 删除spring-boot-starter-tomcat的依赖

   jar不需要这个依赖
   
2. 添加spring-boot-maven-plugin插件
   
   不需要打war包的插件了，需要打jar包的

    ```
    <build>
        <finalName>${project.artifactId}</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    ```   
    
3. packaging->jar

    ```
    <packaging>jar</packaging>
    ```
4. 入口不需要继承SpringBootServletInitializer   

5. mvn clean package
   
6. java -jar miaosha.jar
   

## jar包依赖分离   

https://www.cnblogs.com/jifeng/p/9263675.html
    
1. 插件配置

    不再使用spring-boot-maven-plugin，改为maven-compiler-plugin
    
    ```
    <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
            <source>${java.version}</source>
            <target>${java.version}</target>
            <encoding>UTF-8</encoding>
        </configuration>
    </plugin>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-dependency-plugin</artifactId>
        <executions>
            <execution>
                <id>copy-dependencies</id>
                <phase>prepare-package</phase>
                <goals>
                    <goal>copy-dependencies</goal>
                </goals>
                <configuration>
                    <outputDirectory>${project.build.directory}/lib</outputDirectory>
                    <overWriteReleases>false</overWriteReleases>
                    <overWriteSnapshots>false</overWriteSnapshots>
                    <overWriteIfNewer>true</overWriteIfNewer>
                </configuration>
            </execution>
        </executions>
    </plugin>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <configuration>
            <archive>
                <manifest>
                    <mainClass>com.mix.miaosha.MiaoshaApplication</mainClass>
                    <!-- 入口程序 -->
                    <addClasspath>true</addClasspath>
                    <!-- 添加依赖jar路径 -->
                    <classpathPrefix>lib/</classpathPrefix>
                </manifest>
            </archive>
        </configuration>
    </plugin>
    ```    
2. mvn clean package
   
3. java -jar miaosha.jar    