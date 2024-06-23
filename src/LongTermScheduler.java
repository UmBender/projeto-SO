import Parser.ParserBNF;
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
    final private int quantum;

    public LongTermScheduler(int quantum) {
        createdProcessQueue = new ConcurrentLinkedQueue<Process>();
        this.quantum = quantum;
    }

    public void setThreads(NotificationInterface userInterface, InterSchedulerInterface shortTermScheduler) {
        this.userInterface = userInterface;
        this.shortTermScheduler = shortTermScheduler;

        userInterface.display("<ls> [LongTerm Scheduler] Pronto.");
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

        Process p1 = new Process(fileName, this.quantum, instructions, userInterface);

        createdProcessQueue.add(p1);

        return true;
    }

    @Override
    public void displaySubmissionQueue() {
        /*
        TODO fazer fica bonito na interface
         */
        userInterface.display("<ls> [LongTermScheduler] Created Process Queue:\n"+ createdProcessQueue.toString());

    }

    @Override
    public void run() {
        /*
        TODO Aqui tem que checkar a carga do ShortTermScheduler para ver se ele ta com muitos processos CPU
        ou se pode mandar mais alguns pois ta com pouca carga ou muito IO bound
         */
        while(true) {
            if(shortTermScheduler.getProcessLoad() < 10) {
                Process p = createdProcessQueue.poll();
                if (p != null) {
                    shortTermScheduler.addProcess(p);
                }else {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ie){
                        System.out.println(ie.getMessage());
                    }
                }
            }else {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ie){
                    System.out.println(ie.getMessage());
                }
            }

        }
    }
}
