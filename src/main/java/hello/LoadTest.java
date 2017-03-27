package hello;

import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 100개의 요청을 받았을때 쓰레드가 어떻게 반응하는지 테스트
 * Created by line play on 2017-03-26.
 */
public class LoadTest {

    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        ExecutorService es = Executors.newFixedThreadPool(100);
        RestTemplate rt = new RestTemplate();

        String url = "http://127.0.0.1:8080/rest?idx={idx}";
//        String url = "http://127.0.0.1:8080/callable";
//        String url = "http://127.0.0.1:8080/async";

        CyclicBarrier barrier = new CyclicBarrier(101);

        for (int i = 0; i < 100; i++) {

            es.submit(() -> {
                int idx = counter.addAndGet(1);

                barrier.await(); // 쓰레드 동기화가 가능하다.

                System.out.println("Thread: " + idx);

                StopWatch sw = new StopWatch();

                sw.start();
                String res = rt.getForObject(url, String.class, idx);

                sw.stop();

                System.out.println("Elapsed: " + idx + " -> " + sw.getTotalTimeMillis() + " / " + res);

                return null; // 구현하는 인터페이스를 runable이 아닌 callable 로 변경 - 컴파일이러 return을 보고 자동으로 인식
            });
        }

        barrier.await();
        StopWatch main = new StopWatch();
        main.start();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);

        main.stop();
        System.out.println("total: " + main.getTotalTimeSeconds());
    }
}
