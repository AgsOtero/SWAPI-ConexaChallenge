package conexa.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .groupName("SWAPI-ConexaChallenge")
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SWAPI-ConexaChallenge")
                .description("App cliente para consumir la Star Wars API")
                .version("1.0")
                .termsOfServiceUrl("Terms of service URL")
                .license("License")
                .licenseUrl("License URL")
                .contact(new Contact("Agustin Otero", "https://www.linkedin.com/in/agustin-otero-727391189/", "oteroagustin95@gmail.com"))
                .build();
    }
}