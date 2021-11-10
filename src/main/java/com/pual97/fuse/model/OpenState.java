package com.pual97.fuse.model;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName: OpenState
 * @description: TODO
 * @author: pual(xuyi)
 * @create: 2021-11-10 15:50
 **/
public class  OpenState extends AbstractBreakerState {

    public OpenState(BreakManager breakManager) {
        super(breakManager);

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeoutHasBeenReached();
                timer.cancel();
            }
        }, breakManager.timeout);
    }

    private void timeoutHasBeenReached() {
        breakManager.moveToHalfOpenState();
    }
}
