package pers.XiaoShadiao.obfuscator.taskmanager;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private static final List<TaskThread> taskThreads = new ArrayList<>();

    static {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors() / 1.2 + 1; i++) {
            taskThreads.add(new TaskThread());
        }
    }

    public static synchronized Task addTask(Task task) {
        TaskThread current = null;
        int count = Integer.MAX_VALUE;
        for (TaskThread thread : taskThreads) {
            if(thread.getTaskCount() < count) {
                current = thread;
                count = thread.getTaskCount();
            }
        }
        assert current != null;
        current.runTask(task);
        return task;
    }

    public static String buildTaskInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========XiaoShadiao Obfuscator Tasks (").append(getTaskCount()).append(")=========");
        for (TaskThread thread : taskThreads) {
            sb.append("\n> ").append(thread.getCurrentTaskName());
        }
        return sb.toString();
    }

    public static int getTaskCount() {
        int count = 0;
        for (TaskThread thread : taskThreads) {
            count += thread.getTaskCount();
        }
        return count;
    }

}
