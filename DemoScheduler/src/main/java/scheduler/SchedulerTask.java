package scheduler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public class SchedulerTask {

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void scheduleFixedDelayTaskString() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

}
