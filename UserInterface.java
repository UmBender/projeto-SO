public class UserInterface implements Runnable, NotificationInterface {
    private SubmissionInterface longTermScheduler;
    private ControlInterface shortTermScheduler;
    public UserInterface(){}

    public void setThreads(ControlInterface shortTermScheduler, SubmissionInterface longTermScheduler){
        this.shortTermScheduler = shortTermScheduler;
        this.longTermScheduler = longTermScheduler;
    }

    @Override
    public void display(String message) {
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
