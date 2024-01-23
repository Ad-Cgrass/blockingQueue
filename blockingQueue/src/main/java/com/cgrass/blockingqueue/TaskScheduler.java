package com.cgrass.blockingqueue;


import com.cgrass.blockingqueue.base.BaseTask;
import com.cgrass.blockingqueue.inter.ITask;

/**
 * Copyright (c) 2019. cq Inc. All rights reserved.
 * Down, kageyuki anchor. Though not to, the heart yearning.
 *
 * @Describe
 * @Notice
 * @Author cq.
 * @Date 2019-10-22 : 11:47
 */
public class TaskScheduler {
    private final String TAG = "TaskScheduler";
    private BlockTaskQueue mTaskQueue = BlockTaskQueue.getInstance();
    private ShowTaskExecutor mExecutor;

    public void removeTask(BaseTask task) {
        System.out.println("BaseTask Size:"+mTaskQueue.size()+" flag:"+task.getTaskFlag());
        mTaskQueue.removeFlag(task.getTaskFlag());
        System.out.println("BaseTask Size:"+mTaskQueue.size());
    }


    private static class ShowDurationHolder {
        private final static TaskScheduler INSTANCE = new TaskScheduler();
    }

    private TaskScheduler() {
        initExecutor();
    }

    private void initExecutor() {
        mExecutor = new ShowTaskExecutor(mTaskQueue);
        mExecutor.start();
    }

    public static TaskScheduler getInstance() {
        return ShowDurationHolder.INSTANCE;
    }

    public void enqueue(ITask task) {
        //因为TaskScheduler这里写成单例，如果isRunning改成false的话，不判断一下，就会一直都是false
        if (!mExecutor.isRunning()) {
            mExecutor.startRunning();
        }
        //按照优先级插入队列 依次播放
        mTaskQueue.add(task);
        System.out.println("BaseTask Size:"+mTaskQueue.size());
    }

    public void resetExecutor() {
        mExecutor.resetExecutor();
    }


    public void resume() {
        mExecutor.resume();
    }

    public void pause() {
        mExecutor.pause();
    }

    public void clearExecutor() {
        mExecutor.clearExecutor();
    }
}
