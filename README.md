# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.0-SNAPSHOT/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.0-SNAPSHOT/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.0-SNAPSHOT/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

# 笔记

### 如何取出存以路径形式（url）存在数据库中的图片

- 图片存储在本地文件中

- 需要在SpringBoot中配置，资源映射配置，因为现在的浏览器不允许直接访问本地资源

  ```java
  import org.springframework.context.annotation.Configuration;
  import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
  import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
  @Configuration
  public class WebMvcConfig implements WebMvcConfigurer {
  
      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
          /*
          * 图片上传问题？
          * addResourceHandler("/file/**")：访问映射的路径。
          * addResourceLocations(“file:D:/manager/”)：资源的绝对路径。
          * */
          registry.addResourceHandler("/data/**").addResourceLocations("file:E:/Java/data/");
      }
  }
  ```

- 在数据库中存储的图片路径为访问资源的映射路径（"/data/"）

- 从数据中取出访问资源的映射路径后，需要在img标签src上赋值 （协议+IP+端口+映射地址）

  ```vue
  <img :src="axios.defaults.baseURL + user.avatarUrl">
  ```

### AXIOS

- post请求

  ```javascript
  axios({method: 'POST', url: '/xxxxx', data: param,}) // data表示参数以对象的形式进行传递
  
  axios({method: 'POST', url: '/xxxxx', params: param,})// params: 表示参数拼接到url地址上(即query的形式)
  ```

- get请求

  ```javascript
  axios({method: 'GET', url: 'xxxxx', params: param,}) // params:表示将参数拼接到路径上。
  
  axios({method: 'GET', url: '/xxx?message=' + msg,})
  ```

  

### 注解

- @Resource
  - @Resource和@Autowired注解都是用来实现依赖注入的。只是@AutoWried按by type自动注入，而@Resource默认按byName自动注入。
  - @Resource有两个重要属性，分别是name和type，spring将name属性解析为bean的名字，而type属性则被解析为bean的类型。所以如果使用name属性，则使用byName的自动注入策略，如果使用type属性则使用byType的自动注入策略。如果都没有指定，则通过反射机制使用byName自动注入策略。
    - 既不指定name属性，也不指定type属性，则自动按byName方式进行查找。如果没有找到符合的bean，则回退为一个原始类型进行查找，如果找到就注入。
    - 只是指定了@Resource注解的name，则按name后的名字去bean元素里查找有与之相等的name属性的bean。

### Json Web Token

