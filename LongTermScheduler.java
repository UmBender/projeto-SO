import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LongTermScheduler implements Runnable, SubmissionInterface {
    private NotificationInterface userInterface;
    private InterSchedulerInterface shortTermScheduler;
    private ConcurrentLinkedQueue<Process> createdProcessQueue;

    public LongTermScheduler() {
        createdProcessQueue = new ConcurrentLinkedQueue<Process>();

    }

    public void setThreads(NotificationInterface userInterface, InterSchedulerInterface shortTermScheduler) {
        this.userInterface = userInterface;
        this.shortTermScheduler = shortTermScheduler;
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
