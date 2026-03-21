package pers.XiaoShadiao.obfuscator.taskmanager;

import java.util.concurrent.ArrayBlockingQueue;

public class TaskThread extends Thread {

    private static int threadCount = 0;
    private boolean isRunning = false;
    private Task task;
    private final ArrayBlockingQueue<Task> taskQueue = new ArrayBlockingQueue<>(5000);

    public TaskThread() {
        setPriority(MAX_PRIORITY);
        setName("TaskThread-" + (++threadCount));
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                if ((task = taskQueue.take()) == null) break;
            } catch (InterruptedException e) {

            }
            try {
                isRunning = true;
                task.run();
                task.setDone();
                isRunning = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void runTask(Task task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            throw new Error("Impossible to throw this exception");
        }
    }

    public boolean hasTask() {
        return !taskQueue.isEmpty() && isRunning;
    }

    public int getTaskCount() {
        return taskQueue.size() + (isRunning ? 1 : 0);
    }

    public String getCurrentTaskName() {
        String taskName = "空闲中";
        // System.out.println(getState());
        if(task != null && isRunning) taskName = task.getTaskName();
        return taskName;
    }

}
