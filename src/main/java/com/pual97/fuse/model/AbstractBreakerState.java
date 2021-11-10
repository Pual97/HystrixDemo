package com.pual97.fuse.model;

import lombok.AllArgsConstructor;

/**
 * @ClassName: AbstractBreakerState
 * @description: TODO
 * @author: pual(xuyi)
 * @create: 2021-11-10 15:43
 **/
@AllArgsConstructor
public abstract class AbstractBreakerState {
    public BreakManager breakManager;

    /**
     * Judge the fuse state before invoke
     */
    public void isFuseNow(){
        if (breakManager.isOpen()){
            throw new RuntimeException("服务已经熔断，稍后重试！");
        }
    }

    /**
     * invoke after success
     */
    public void invokeSuccess(){
        breakManager.increseSuccessCount();
    }

    public void invokeException(){
        breakManager.increseFailCount();
        breakManager.resetConsecutiveSuccessCount();
    }



}
