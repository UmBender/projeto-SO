import UserInterface.ControlInterface;
import UserInterface.NotificationInterface;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ShortTermScheduler implements Runnable, ControlInterface, InterSchedulerInterface {
    private NotificationInterface userInterface;
    private ConcurrentLinkedQueue<Process> prontosQueue;
    private ConcurrentLinkedQueue<Process> bloqueadosQueue;
    private ConcurrentLinkedQueue<Process> terminados;

    public ShortTermScheduler() {
        userInterface = null;
    }


    public void setThreads(NotificationInterface userInterface){
        this.userInterface = userInterface;
        this.prontosQueue = new ConcurrentLinkedQueue<>();
        this.bloqueadosQueue = new ConcurrentLinkedQueue<>();
        this.terminados = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
        /*
        TODO
         */

    }

    @Override
    public void startSimulation() {
        /*
        TODO
         */

    }

    @Override
    public void suspendSimulation() {
        /*
        TODO
         */
    }

    @Override
    public void resumeSimulation() {
        /*
        TODO
         */
    }

    @Override
    public void stopSimulation() {
        /*
        TODO
         */
    }

    @Override
    public void displayProcessQueues() {
        /*
        TODO
         */
    }

    @Override
    public void addProcess(Process bcp) {
        /*
        TODO
         */
    }

    @Override
    public int getProcessLoad() {
        /*
        TODO
         */
        return 0;
    }
}
