import UserInterface.ControlInterface;
import UserInterface.NotificationInterface;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ShortTermScheduler implements Runnable, ControlInterface, InterSchedulerInterface {
    private Queue<Process> readyQueue;
    private Queue<Process> blockedQueue;
    private Queue<Process> terminatedQueue;
    private NotificationInterface userInterface;
    private boolean suspended = false;
    private int quantum;

    public ShortTermScheduler(int quantum) {
        this.readyQueue = new ConcurrentLinkedQueue<>();
        this.blockedQueue = new ConcurrentLinkedQueue<>();
        this.terminatedQueue = new ConcurrentLinkedQueue<>();
        this.quantum = quantum;
        this.suspended = false;
    }

    public void setThreads(NotificationInterface userInterface){
        this.userInterface = userInterface;
    }

    // Adiciona um processo à fila de prontos
    public synchronized void addProcess(Process process) {
        readyQueue.add(process);
    }

    // Obtém a carga atual de processos na fila de prontos
    public synchronized int getProcessLoad() {
        return readyQueue.size();
    }

    @Override
    public void run() {
        userInterface.display("<ss> [ShortTermScheduler] Pronto.");
        while (true) {
            if(!suspended) {
                if (!readyQueue.isEmpty()) {
                    Process currentProcess = readyQueue.poll();
                    if (currentProcess != null) {
                        executeProcess(currentProcess);
                    }
                }
                checkBlockedQueue();
            } else {
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException ie) {
                    System.err.println("<ss> [ShortTermScheduler] Interrompido.");
                }
            }
            if (readyQueue.isEmpty() && blockedQueue.isEmpty()) {
                // Termina a simulação quando não há mais processos prontos ou bloqueados
                userInterface.display("<ss> [ShortTermScheduler] Vazio.");
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException ie) {
                    System.err.println("<ss> [ShortTermScheduler] Interrompido.");
                }
            }
        }
    }

    private void executeProcess(Process process) {
        int remainingTime = process.getRemainingTime();

        while(remainingTime > 0){
            while(suspended) {
                try {
                    Thread.sleep(300);
                }catch (InterruptedException e){
                    System.err.println("<ss> [ShortTermScheduler] Interrupted while waiting for process to finish.");
                }
            }
            process.setRemainingTime(remainingTime-1);

            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                System.err.println("<ss> [ShortTermScheduler] Interrupted while waiting for process to finish.");
            }

            String command = process.getNextInstruction();
            userInterface.display("Processo: " + process.getId() + " executa comando:" +command);
            switch (command) {
                case "block":
                    process.downgradeNumberQuantum();
                    processBlock(process);
                    process.resetRemaningTime();
                    return;
                case "execute":
                    break;
                default:
                    terminatedQueue.add(process);
                    return;
            }

            remainingTime = process.getRemainingTime();
        }
        process.upgradeNumberQuantum();
        process.resetRemaningTime();
        readyQueue.add(process);
    }


    private void checkBlockedQueue() {
        Queue<Process> tempQueue = new ConcurrentLinkedQueue<>();
        while (!blockedQueue.isEmpty()) {
            Process process = blockedQueue.poll();
            if (process != null) {
                if (process.getBlockTime() > 0) {
                    process.decrementBlockTime();
                    tempQueue.add(process);
                } else {
                    readyQueue.add(process);
                }
            }
        }
        blockedQueue.addAll(tempQueue);
    }

    private void processBlock(Process process) {
        int blockPeriod = process.getNextBlockPeriod();
        process.setBlockTime(blockPeriod);
        blockedQueue.add(process);
        userInterface.display("[ShortTermScheduler] Process " + process.getId() + " blocked for " + process.getBlockTime() + " quanta.");
    }

    @Override
    public void startSimulation() {
        suspended = false;
        /*
        TODO
         */
    }

    @Override
    public void suspendSimulation() {
        suspended = true;
        /*
        TODO
         */
    }

    @Override
    public void resumeSimulation() {
        suspended = false;
        /*
        TODO
         */
    }

    @Override
    public void stopSimulation() {
        suspended = true;
        /*
        TODO
         */
    }

    @Override
    public void displayProcessQueues() {
        userInterface.display(readyQueue.toString());
        userInterface.display(blockedQueue.toString());
        userInterface.display(terminatedQueue.toString());
        /*
        TODO Fazer com formatação bonitinha;
         */
    }
}
