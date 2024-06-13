import UserInterface.NotificationInterface;
import UserInterface.SubmissionInterface;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LongTermScheduler implements Runnable, SubmissionInterface {
    private NotificationInterface userInterface;
    private InterSchedulerInterface shortTermScheduler;
    private ConcurrentLinkedQueue<Process> createdProcessQueue;

    public LongTermScheduler() {
        createdProcessQueue = new ConcurrentLinkedQueue<Process>();

    }

    @Override
    public boolean submitJob(String fileName) {
        /*
        TODO
         */
        return false;
    }

    @Override
    public void displaySubmissionQueue() {
        /*
        TODO
         */

    }

    @Override
    public void run() {
        /*
        TODO
         */
    }
}
