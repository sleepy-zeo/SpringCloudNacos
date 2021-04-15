# scn spring boot

```text
设置
spring.mvc.throw-exception-if-no-handler-found=true
spring.resources.add-mappings=false
这样例如/info这种没有对应handler的请求时会抛出NoHandlerFoundException异常，然后再GlobalExceptionHandler中捕获即可

但是设置
spring.resources.add-mappings=false
会导致访问不到具体的静态资源，所以需要
@Configuration
public class SCNWebMvcConfigurer implements WebMvcConfigurer {

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*.ico").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/*.jpg").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/*.jpeg").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/*.png").addResourceLocations("classpath:/static/");
    }

}
暴露具体的静态资源，

但是这样设置会导致访问某些不存在的资源例如/info.png报错，因为static/下就没有该资源图片，这种会调用默认的错误处理机制，我们通过
@Controller
public class SCNErrorController extends BasicErrorController {
    // ...
}
覆盖默认的错误处理机制
```

@Component
public class ErrorPageConfiguration implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
        errorPageRegistry.addErrorPages(
                new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"),
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500")
        );
    }

}