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
    public boolean isFuseNow(){
        try {
            breakManager.lock.lock();
            if (breakManager.isOpen()){
                return true;
            }
            return false;
        }finally {
            breakManager.lock.unlock();
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
