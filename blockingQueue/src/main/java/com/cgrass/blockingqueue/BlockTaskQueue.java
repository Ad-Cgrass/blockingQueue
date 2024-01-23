package com.cgrass.blockingqueue;

import android.os.Build;


import com.cgrass.blockingqueue.inter.ITask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright (c) 2019. cq Inc. All rights reserved.
 * Down, kageyuki anchor. Though not to, the heart yearning.
 *
 * @Describe
 * @Notice
 * @Author cq.
 * @Date 2019-10-22 : 11:40
 */
public class BlockTaskQueue {
    private String TAG = "BlockTaskQueue";
    private AtomicInteger mAtomicInteger = new AtomicInteger();
    //阻塞队列
    private final BlockingQueue<ITask> mTaskQueue = new PriorityBlockingQueue<>();

    private BlockTaskQueue() {
    }

    //单例模式
    private static class BlockTaskQueueHolder {
        private final static BlockTaskQueue INSTANCE = new BlockTaskQueue();
    }

    public static BlockTaskQueue getInstance() {
        return BlockTaskQueueHolder.INSTANCE;
    }

    /**
     * 插入时 因为每一个Task都实现了comparable接口 所以队列会按照Task复写的compare()方法定义的优先级次序进行插入
     * 当优先级相同时，使用AtomicInteger原子类自增 来为每一个task 设置sequence，
     * sequence的作用是标记两个相同优先级的任务入队的次序
     */
    public <T extends ITask> int add(T task) {
        if (!mTaskQueue.contains(task)) {
            task.setSequence(mAtomicInteger.incrementAndGet());
            mTaskQueue.add(task);
            System.out.println("\n add task " + task.toString());
        }
        return mTaskQueue.size();
    }

    public <T extends ITask> void remove(T task) {
        if (mTaskQueue.contains(task)) {
            System.out.println("\n" + "task has been finished. remove it from task queue");
            mTaskQueue.remove(task);
        }
        if (mTaskQueue.size() == 0) {
            mAtomicInteger.set(0);
        }
    }

    public <T extends ITask> void removeFlag(String flag) {
        if (mTaskQueue.isEmpty()) {
            System.out.println("Task queue is empty.");
            return;
        }
        ITask element = mTaskQueue.peek();
        if (element != null) {
            System.out.println("\nTask has been finished. Remove it from the task queue.");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTaskQueue.removeIf(iTask -> iTask.getTaskFlag().contains(flag));
            }
            System.out.println("Task queue is remove "+flag);
        }
        if (mTaskQueue.isEmpty()) {
            mAtomicInteger.set(0);
            System.out.println("Task queue is now empty.");
        }
    }

    public ITask poll() {
        return mTaskQueue.poll();
    }

    public ITask take() throws InterruptedException {
        return mTaskQueue.take();
    }

    public void clear() {
        mTaskQueue.clear();
    }

    public int size() {
        return mTaskQueue.size();
    }
}
