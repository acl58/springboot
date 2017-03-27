package hello;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;

/**
 * 쓰레드풀이 가득찼을때 응답이 느려지는 문제를 스프링에서 어떻게 해결했는지 알아보자
 * 쓰레드 생성은 cpu와 메모리를 많이 사용한다.
 *
 * Created by line play on 2017-03-26.
 */
@SpringBootApplication
public class TobytvBApplication3 {

    @RestController // responsebody를 안붙여도 됨 - 원래는 view로 리턴하지만 @RestController를 사용하면 body로 리턴값을 보내준다.
    public static class MyController {

//        RestTemplate rt = new RestTemplate();
        AsyncRestTemplate art = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1))); // 네티를 활용하면 추가 쓰레드를 거의 만들지 않는다.

        @GetMapping("/rest")
        public ListenableFuture<ResponseEntity<String>> rest(int idx) {

            // api 대기하지 않도록 비동기로 변경하여야 한다. 스프링3.X 에서는 해결을 못함
//            String res = rt.getForObject("http://127.0.0.1:8081/service?req={req}", String.class, "hello" + idx);
            ListenableFuture<ResponseEntity<String>> res = art.getForEntity("http://127.0.0.1:8081/service?req={req}", String.class, "hello" + idx);
            return res;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(TobytvBApplication3.class, args);
    }

}
