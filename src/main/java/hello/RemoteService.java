package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by line play on 2017-03-26.
 */
@SpringBootApplication
public class RemoteService {

    @RestController // responsebody를 안붙여도 됨 - 원래는 view로 리턴하지만 @RestController를 사용하면 body로 리턴값을 보내준다.
    public static class MyController {
        @GetMapping("/service")
        public String service(String req) throws InterruptedException {
            Thread.sleep(2000);
            return req + "/service";
        }
    }

    public static void main(String[] args) {
        System.setProperty("SERVER_PORT", "8081");
        System.setProperty("server.tomcat.max-threads", "1000");
        SpringApplication.run(RemoteService.class, args);
    }
}
