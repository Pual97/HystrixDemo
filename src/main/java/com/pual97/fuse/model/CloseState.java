package com.pual97.fuse.model;

/**
 * @ClassName: CloseState
 * @description: TODO
 * @author: pual(xuyi)
 * @create: 2021-11-10 15:50
 **/
public class CloseState extends AbstractBreakerState{
    public CloseState(BreakManager breakManager) {
        super(breakManager);

        breakManager.resetFailCount();
    }

    @Override
    public void invokeException(){
        super.invokeException();

        if(breakManager.failThresholdReached()){
            breakManager.moveToOpenState();
        }
    }
}
