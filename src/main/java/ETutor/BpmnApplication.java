package ETutor;

import ETutor.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@EnableProcessApplication
@EnableConfigurationProperties({ApplicationProperties.class})
@SpringBootApplication
public class BpmnApplication {

    public static void main(String... args) {
        SpringApplication.run(BpmnApplication.class, args);
    }

}
