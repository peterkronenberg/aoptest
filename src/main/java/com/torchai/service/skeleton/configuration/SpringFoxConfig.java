package com.torchai.service.skeleton.configuration;

import com.torchai.service.common.configuration.SpringFoxConfigBase;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig extends SpringFoxConfigBase {

    @Override
    public String getTitle() {
        return "Skeleton service";
    }

    @Override
    public String getDescription() {
        return "This is a skeleton for building new microservices";
    }
}
