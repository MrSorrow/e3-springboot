package guo.ping.e3mall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.concurrent.CountDownLatch;

@MapperScan(value = "guo.ping.e3mall.manager.mapper")
@SpringBootApplication
public class OrderServiceApplicationStarter {
    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder()
                .sources(OrderServiceApplicationStarter.class)
                .web(WebApplicationType.NONE)
                .run(args);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }
}
