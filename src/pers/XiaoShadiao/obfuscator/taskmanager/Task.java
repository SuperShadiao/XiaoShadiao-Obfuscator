package pers.XiaoShadiao.obfuscator.taskmanager;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Task {

    private final Runnable runnable;
    private final String taskName;
    private final CountDownLatch latch = new CountDownLatch(1);

    public Task(Runnable runnable, String taskName) {
        this.runnable = runnable;
        this.taskName = taskName;
    }

    public void run() {
        runnable.run();
    }

    public String getTaskName() {
        return taskName;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public boolean isDone() {
        return latch.getCount() == 0;
    }

    public void setDone() {
        latch.countDown();
    }

    public void await() throws InterruptedException {
        latch.await();
    }

    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return latch.await(timeout, unit);
    }

}
