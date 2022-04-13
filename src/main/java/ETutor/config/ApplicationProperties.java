package ETutor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Used to retrieve the application properties
 */
@ConfigurationProperties(value = "application")
public class ApplicationProperties {
    private String testString;
    

}
