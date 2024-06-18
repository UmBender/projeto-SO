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

        userInterface.display("[LongTerm Scheduler] Pronto.");
    }

    @Override
    public boolean submitJob(String fileName) {
        ParserBNF parser = new ParserBNF(userInterface);

        Vector<String> instructions;
        try {
            instructions = parser.parse(
                    fileName
            );
        } catch (FileNotFoundException | ParseException e) {
            userInterface.display(e.getMessage());
            return false;
        }

        Process p1 = new Process("Process1", 600, instructions, userInterface);

        createdProcessQueue.add(p1);

        return true;
    }

    @Override
    public void displaySubmissionQueue() {
        /*
        TODO fazer fica bonito na interface
         */
        userInterface.display("[LongTermScheduler] Created Process Queue:\n"+ createdProcessQueue.toString());

    }

    @Override
    public void run() {
        /*
        TODO Aqui tem que checkar a carga do ShortTermScheduler para ver se ele ta com muitos processos CPU
        ou se pode mandar mais alguns pois ta com pouca carga ou muito IO bound
         */

        Process p = createdProcessQueue.poll();
        if (p != null) {
            shortTermScheduler.addProcess(p);
        }
    }
}
