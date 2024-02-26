package myProject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.builders.ApiInfoBuilder;


@Configuration
@EnableSwagger2
public class SpringFoxConfig {                                    

	@Bean
	public Docket myDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("myProject"))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AR_server")
				.description("Hi, welcome to the backend of the Coms402c AR_project, here is the API document!")
				.termsOfServiceUrl("https://coms-402-sd-8.cs.iastate.edu:8080/terms")
				.contact(new Contact("YizhenXu","https://github.com/519045752/ComS402C_SD3","yizhenx@iastate.edu"))
				.version("0.0.2")
				.build();
	}

}
