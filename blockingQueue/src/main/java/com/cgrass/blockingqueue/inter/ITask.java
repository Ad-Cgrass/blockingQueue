package com.cgrass.blockingqueue.inter;


import com.cgrass.blockingqueue.bean.TaskPriority;

/**
 * Copyright (c) 2019. cq Inc. All rights reserved.
 * Down, kageyuki anchor. Though not to, the heart yearning.
 *
 * @Describe
 * @Notice
 * @Author cq.
 * @Date 2019-10-22 : 11:38
 */
public interface ITask extends Comparable<ITask> {
    //任务标识,可以通过tag移除任务.
    String getTaskFlag();

    // 将该任务插入队列
    void enqueue();

    // 执行具体任务的方法
    void doTask();

    // 任务执行完成后的回调方法
    void finishTask();

    // 设置任务优先级
    ITask setPriority(TaskPriority mTaskPriority);

    // 获取任务优先级
    TaskPriority getPriority();

    // 当优先级相同 按照插入顺序 先入先出 该方法用来标记插入顺序
    void setSequence(int mSequence);

    // 获取入队次序
    int getSequence();

    // 每个任务的状态，就是标记完成和未完成
    boolean getStatus();

    // 设置每个任务的执行时间，该方法用于任务执行时间确定的情况
    ITask setDuration(int duration);

    ITask continueTask();

    // 获取每个任务执行的时间
    int getDuration();

    // 阻塞任务执行，该方法用于任务执行时间不确定的情况
    void blockTask() throws Exception;

    // 解除阻塞任务，该方法用于任务执行时间不确定的情况
    void unLockBlock();


}
