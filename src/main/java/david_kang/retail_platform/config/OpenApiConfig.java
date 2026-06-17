package david_kang.retail_platform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI retailPlatformOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Retail Platform API")
                                .description("Backend API for retail order processing.")
                                .version("1.0")
                );
    }
}