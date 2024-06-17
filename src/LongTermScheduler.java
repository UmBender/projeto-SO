import UserInterface.NotificationInterface;
import UserInterface.SubmissionInterface;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Vector;
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
        ParserBNF parser = new ParserBNF();

        // Só não se esquece de mudar aqui
        Vector<String> instructions;
        try {
            instructions = parser.parse(
                    fileName
            );
        }catch (FileNotFoundException | ParseException e) {
            /*
            TODO Mudar para utilizar o objeto Notification
             */
            userInterface.display(e.getMessage());
            return false;
        }

        Process p1 = new Process("Process1", 600, instructions);

        shortTermScheduler.addProcess(p1);

        return true;
    }

    @Override
    public void displaySubmissionQueue() {
        /*
        TODO
         */
        userInterface.display(createdProcessQueue.toString());

    }

    @Override
    public void run() {
        /*
        TODO Aqui tem que checkar a carga do ShortTermScheduler para ver se ele ta com muitos processos CPU
        ou se pode mandar mais alguns pois ta com pouca carga ou muito IO bound
         */
    }
}
