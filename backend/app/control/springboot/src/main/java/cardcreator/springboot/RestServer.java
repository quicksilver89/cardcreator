package cardcreator.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RestServer extends SpringBootServletInitializer
{
    public static void main( String[] args )
    {
        SpringApplication.run( RestServer.class, args );
    }
}
