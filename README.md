## 自用，阻塞队列
### 使用方式，如下
- 1.任务基层BaseTask
- 2.BaseTask里面做完任务主动unlock

代码如下:
```
public class SoundTask extends BaseTask {

    @Override
    public void doTask() {
        super.doTask();
        System.out.println("id:" + id + "   playText:" + playText);
        SoundPoolUtils.getInstance().setListener(() -> {
            unLockBlock();
            AppRxBusMessage message = new AppRxBusMessage();
            message.setMessageType(AppRxBusMessage.MessageType.CLICK_CALL_ITEM);
            Bundle bundle = new Bundle();
            bundle.putString("playText", playText);
            bundle.putInt("status", -3);
            bundle.putLong("id", id);
            message.setData(bundle);
            RxBus.getDefault().post(message);
        }).playCall(playText);
    }

    @Override
    public void finishTask() {
        super.finishTask();
    }

    @Override
    public String getTaskFlag() {
        return playText;
    }

    private String playText;
    private long id;

    public interface OnCompletionListener {
        void onFinish();
    }

    public SoundTask(String playText, long id) {
        this.playText = playText;
        this.id = id;
    }
}

```

如何调用
```
   TaskScheduler.getInstance().pause();//任务暂停
   TaskScheduler.getInstance().removeTask(new SoundTask(callRecord.getName(), callRecord.getId()));//移除任务
   TaskScheduler.getInstance().resume();//任务恢复
```
