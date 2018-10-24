# 热部署

## 方式1
直接ide启动
只要pom加入devtool即可

```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<optional>true</optional>
		</dependency>
```
## 方式2
用mvn插件
### 依赖
```
<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<dependencies>
					<dependency>
						<groupId>org.springframework</groupId>
						<artifactId>springloaded</artifactId>
						<version>1.2.7.RELEASE</version>
					</dependency>
				</dependencies>
			</plugin>
		</plugins>
	</build>
```

### 命令

mvn spring-boot:run


# 发布项目

## jar方式

```
mvn install 生成jar

java -jar /Users/tingkl/Desktop/java/imooc/spring-boot/hot-deploy/target/hot-deploy-0.0.1-SNAPSHOT.jar
```

## war方式

```
1. mvn clean

2. pom jar -> war

3. web -> tomcat

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>


		<!-- war包使用
		<dependency>-->
			<!--<groupId>org.springframework.boot</groupId>-->
			<!--<artifactId>spring-boot-starter-tomcat</artifactId>-->
		<!--</dependency>-->
4. update project

5. 
  public class HotDeployApplication extends SpringBootServletInitializer{
  
  	@Override
  	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
  		return builder.sources(HotDeployApplication.class);
  	}
6. mvn install生成war包  	
```


