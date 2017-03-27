package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Created by line play on 2017-03-25.
 */
@SpringBootApplication
@EnableAsync // @Async 를 이용하기 위해 사용
public
class TobytvBApplication {

    @Component
    public static class MyService {
        @Async(value="tp") // 비동기 작업이 됨 - 비동기 작업이 어떤 pool을 사용할지 설정
        public ListenableFuture<String> hello() throws InterruptedException {
            // 자바9에 ListenableFuture보다 더 좋은 혁명적인 것이 있음...

            System.out.println("hello()");
            Thread.sleep(2000);
            return new AsyncResult<>("Hello");
        }
    }

    @Bean
    ThreadPoolTaskExecutor tp() {
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
        te.setCorePoolSize(10);
        te.setMaxPoolSize(100);
        te.setQueueCapacity(200);
        te.setThreadNamePrefix("mythread");
        te.initialize();
        return te;
    }

    public static void main(String[] args) {
        try (ConfigurableApplicationContext c = SpringApplication.run(TobytvBApplication.class, args)) {
        }
    }

    @Autowired
    MyService myService;

    @Bean
    ApplicationRunner run() {
        return args -> {
            System.out.println("run()");
            ListenableFuture<String> f = myService.hello();
            f.addCallback(s -> System.out.println(s), e -> System.out.println(e.getMessage()));
//            f.cancel(true);
            System.out.println("exit");

//            System.out.println("Exit: " + f.isDone());
//            System.out.println("Result: " + f.get());
        };
    }
}
