package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;

/**
 * 서블릿은 기본적으로 블로킹 io방식이다 - httpRequest, httpResponse 가 블로킹 방식
 * Servlet 3.1 비동기 IO
 * Created by line play on 2017-03-25.
 */
@EnableAutoConfiguration
@SpringBootApplication
@EnableAsync // @Async 를 이용하기 위해 사용
public class TobytvBApplication2 {

    @RestController
    public static class MyController {
        @Autowired
        MyService myService;

        // 동기로 수행
        @GetMapping("async")
        public String async() throws InterruptedException {
            System.out.println("async");
            Thread.sleep(2000);
            return "Hello";
        }

        // 비동기로 수행
        // 스프링의 비동기 수행 - 서블릿쓰레드는 비동기로 수행되지만 작업쓰레드는 그만큼 만들어져서 처리됨
        @GetMapping("/callable")
        public Callable<String> callable() throws InterruptedException {


            System.out.println("Callable");
            return () -> {
                System.out.println("async");
                Thread.sleep(2000);
                return "Hello";
            };
        }

        Queue<DeferredResult<String>> results = new ConcurrentLinkedDeque<>();

        @GetMapping("/dr")
        public DeferredResult<String> dr() throws InterruptedException {
            System.out.println("dr");
            DeferredResult<String> dr = new DeferredResult<>(600000L);

            results.add(dr);
            return dr;
        }

        @GetMapping("/dr/count")
        public String drcount() {
            return String.valueOf(results.size());
        }

        @GetMapping("/dr/event")
        public String drevent(String msg) {

            for (DeferredResult<String> dr : results) {
                dr.setResult("Hello " + msg);
                results.remove(dr);
            }
            return "OK";
        }

        @GetMapping("/emitter")
        public ResponseBodyEmitter emitter() throws InterruptedException {

            ResponseBodyEmitter emitter = new ResponseBodyEmitter();

            Executors.newSingleThreadExecutor().submit(() -> {
                for (int i = 1; i < 50; i++) {
                    try {
                        // http sse 기술

                        emitter.send("<p>Stream " + i + "</p>");
                        Thread.sleep(100);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            });
            return emitter;
        }

    }


    @Component
    public static class MyService {
        @Async // 비동기 작업이 됨 - 비동기 작업이 어떤 pool을 사용할지 설정
        public ListenableFuture<String> hello() throws InterruptedException {
            // 자바9에 ListenableFuture보다 더 좋은 혁명적인 것이 있음...

            System.out.println("hello()");
            Thread.sleep(2000);
            return new AsyncResult<>("Hello");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(TobytvBApplication2.class, args);
    }
}

