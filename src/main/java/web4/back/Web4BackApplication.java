package web4.back;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Web4BackApplication {

    private static final Logger log = Logger.getLogger(Web4BackApplication.class);

    public static void main(String[] args) {
        log.info("=====================Starting Spring Boot Application===============================");
        SpringApplication.run(Web4BackApplication.class, args);
    }

}
