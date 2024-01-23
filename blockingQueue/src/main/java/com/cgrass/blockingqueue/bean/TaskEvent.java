package com.cgrass.blockingqueue.bean;


import com.cgrass.blockingqueue.inter.ITask;

import java.lang.ref.WeakReference;

/**
 * Copyright (c) 2019. cq Inc. All rights reserved.
 * Down, kageyuki anchor. Though not to, the heart yearning.
 *
 * @Describe
 * @Notice
 * @Author cq.
 * @Date 2019-10-22 : 11:48
 */
public class TaskEvent {
    private WeakReference<ITask> mTask;
    int mEventType;

    public ITask getTask() {
        return mTask.get();
    }

    public void setTask(ITask mTask) {
        this.mTask = new WeakReference<>(mTask);
    }

    public int getEventType() {
        return mEventType;
    }

    public void setEventType(int mEventType) {
        this.mEventType = mEventType;
    }

    public static class EventType {
        public static final int DO = 0X00;
        public static final int FINISH = 0X01;
    }
}
