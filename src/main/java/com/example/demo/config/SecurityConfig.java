package com.example.demo.config;
import org.springframework.context.annotation.Configuration; import org.springframework.web.servlet.config.annotation.*;
@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    @Override public void addCorsMappings(CorsRegistry registry){ registry.addMapping("/api/**").allowedOrigins("http://localhost:8080").allowedMethods("GET","POST","PUT","PATCH","DELETE").allowedHeaders("*"); }
    @Override public void addResourceHandlers(ResourceHandlerRegistry registry){registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");}
}
