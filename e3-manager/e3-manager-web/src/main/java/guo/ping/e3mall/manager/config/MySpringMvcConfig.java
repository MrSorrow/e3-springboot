package guo.ping.e3mall.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MySpringMvcConfig implements WebMvcConfigurer {

    // WebMvcConfigurerAdapter在SpringBoot 2.x过时，不过还可以用
    // 然而推荐的WebMvcConfigurationSupport 自动配置全部不再生效

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
    }
}
