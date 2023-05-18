package org.chodura.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Stoppable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Stoppable.class);
    protected volatile boolean stopRequested = false;

    private volatile long lastActivityTime;

    public void requestStop() {
        LOGGER.info("request to stop thread, value before request: {}", stopRequested);
        stopRequested = true;
    }

    public long getLastActivityTime() {
        return this.lastActivityTime;
    }

    protected void setLastActivityTime(long lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }
}
