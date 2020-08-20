package spring.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
//to create the database tables
@EntityScan(basePackages = {"spring.api.rest.model"})
//general project settings
@ComponentScan(basePackages = {"spring.*"})
//persistence interfaces folder
@EnableJpaRepositories(basePackages = {"spring.api.rest.repository"})
//activates transaction control
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
@EnableCaching
public class SpringrestapiApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringrestapiApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
	
	//Global mapping for the entire system
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		//registry.addMapping("/user/**").allowedMethods("POST","DELETE").allowedOrigins("www.jdevtreinamento.com.br","localhost:8080");
		registry.addMapping("/user/**").allowedMethods("*").allowedOrigins("*");
	}

}
