Spring-IOC注入方式和场景

xml
1: Bean实现类来自第三方类库,如:DataSource等
2: 需要命名空间配置,如:context, aop, mvc等

注解
项目中自身开发使用等类,可纸巾在代码中使用注解,如@Service @Controller等

java配置类

需要通过代码控制对象创建逻辑等场景,如:自定义修改依赖类库

Spring + SpringMVC + MyBatis