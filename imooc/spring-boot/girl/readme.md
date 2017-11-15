# 设置maven阿里镜像仓库

vi .m2/setting.xml

```
<?xml version="1.0" encoding="UTF-8"?>  
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"   
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"   
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0  
                              http://maven.apache.org/xsd/settings-1.0.0.xsd">  
    <mirrors>  
        <mirror>  
            <id>alimaven</id>  
            <name>aliyun maven</name>  
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>  
            <mirrorOf>central</mirrorOf>          
        </mirror>  
        <mirror>      
            <id>ibiblio</id>      
            <mirrorOf>central</mirrorOf>      
            <name>Human Readable Name for this Mirror.</name>      
            <url>http://mirrors.ibiblio.org/pub/mirrors/maven2/</url>      
        </mirror>    
    </mirrors>  
</settings>  
```

# mvn启动方式

项目目录下：/Users/tingkl/Desktop/java/imooc/spring-boot/girl

mvn spring-boot:run