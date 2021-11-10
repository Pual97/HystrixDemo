package com.pual97.fuse.model;

/**
 * @ClassName: HalfOpenState
 * @description: TODO
 * @author: pual(xuyi)
 * @create: 2021-11-10 15:50
 **/
public class HalfOpenState extends AbstractBreakerState{

    public HalfOpenState(BreakManager breakManager) {
        super(breakManager);

        breakManager.resetConsecutiveSuccessCount();
    }

    @Override
    public void invokeException(){
        super.invokeException();

        breakManager.moveToOpenState();
    }

    @Override
    public void invokeSuccess(){
        super.invokeSuccess();

        if(breakManager.consecutiveSuccessThresholdReached()){
            breakManager.moveToCloseState();
        }
    }

}
