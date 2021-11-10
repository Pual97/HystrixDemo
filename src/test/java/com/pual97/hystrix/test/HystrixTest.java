package com.pual97.hystrix.test;

import com.pual97.fuse.model.BreakManager;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author pual
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021/11/10
 */
public class HystrixTest {
    @Test
    public void Test(){
        int requestCount  = 200;
        CountDownLatch countDownLatch = new CountDownLatch(requestCount);
        BreakManager breakManager = new BreakManager(5, 3, 6000);

        for (int i = 0; i < requestCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(new Random().nextInt(2) * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        if(!breakManager.state.isFuseNow()){
                            // 模拟后期的服务恢复状态
                            if (countDownLatch.getCount() >= requestCount/2){
                                // 模拟随机失败
                                if (new Random().nextInt(2) == 1){
                                    throw new Exception("mock error");
                                }
                            }
                            System.out.println("开始正常执行业务。。。。");
                        }else {
                            System.out.println("服务已经熔断，稍后重试！");
                        }

                    } catch (Exception e) {
                        System.out.println("业务执行异常￥￥￥￥￥￥");
                        breakManager.state.invokeException();
                    }
                    breakManager.state.invokeSuccess();
                    countDownLatch.countDown();
                }
            }).start();
        }
    }
}
