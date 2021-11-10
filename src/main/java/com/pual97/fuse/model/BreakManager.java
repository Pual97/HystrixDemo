package com.pual97.fuse.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: BreakManager
 * @description: TODO
 * @author: pual(xuyi)
 * @create: 2021-11-10 15:52
 **/
public class BreakManager {

    public Lock lock = new ReentrantLock();
    public int timeout;
    public int failureCount;
    public int consecutiveSuccessCount;
    public int failureThreshold;
    public int consecutiveSuccessThreshold;

    public AbstractBreakerState state;


    public boolean isOpen() {
        return state instanceof  OpenState;
    }

    public boolean isClosed() {
        return state instanceof  CloseState;
    }

    public boolean isHalfOpened() {
        return state instanceof  HalfOpenState;
    }


    public void increseSuccessCount() {
        consecutiveSuccessCount ++;
    }

    public void increseFailCount() {
        failureCount++;
    }

    public void resetConsecutiveSuccessCount() {
        consecutiveSuccessCount  = 0;
    }

    public void resetFailCount() {
        failureCount = 0;
    }

    public boolean failThresholdReached() {
        return failureCount >= failureThreshold;
    }

    public void moveToOpenState() {
        try {
            lock.lock();
            System.out.println("业务异常数量达到 ："+failureThreshold+" ,熔断打开");
            state = new OpenState(this);
        }finally {
            lock.unlock();
        }
    }

    public void moveToHalfOpenState() {
        try {
            lock.lock();
            System.out.println("熔断保护时间结束，熔断开始半开======");
            state = new HalfOpenState(this);
        }finally {
            lock.unlock();
        }
    }

    public void moveToCloseState() {
        try {
            lock.lock();
            System.out.println("业务成功数量达到："+consecutiveSuccessThreshold+"，熔断关闭，正常接入服务");
            state = new CloseState(this);
        }finally {
            lock.unlock();
        }
    }

    public boolean consecutiveSuccessThresholdReached() {
        return  consecutiveSuccessCount >= consecutiveSuccessThreshold;
    }

    public BreakManager(int failureThreshold, int consecutiveSuccessThreshold, int timeout){
        if(failureThreshold < 1 || consecutiveSuccessThreshold < 1){
            throw new RuntimeException("The maximum number of closed fuses and the maximum number of consecutive successful fuses must be greater than 0");
        }
        if(timeout < 1){
            throw new RuntimeException("The timeout period of the fuse disconnect status must be greater than 0");
        }

        this.failureThreshold = failureThreshold;
        this.consecutiveSuccessThreshold = consecutiveSuccessThreshold;
        this.moveToCloseState();
    }

    /**
     * test method
     * @param rs
     * @param times
     */
//    public void attempCall(boolean rs, int times){
//        for (int i = 0; i < times; i++) {
//            state.isFuseNow();
//            try{
//                if(!rs){
//                    throw new Exception();
//                }else {
//                    System.out.println("第"+(i+1)+"次服务调用成功！");
//                }
//
//            }catch (Exception e){
//                System.out.println("第"+(i+1)+"次服务调用失败！");
//                state.invokeException();
//            }
//
//            state.invokeSuccess();
//        }
//    }


}
