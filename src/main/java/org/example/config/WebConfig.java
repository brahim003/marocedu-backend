package org.example.config; // 👈 لاحظ السمية د الباكيج

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // هاد السطر كيعطي الصلاحية للفرونت باش يقرا التصاور من المجلد نيشان
        registry.addResourceHandler("/logos/**")
                .addResourceLocations("file:src/main/resources/static/logos/");
    }
}