import UserInterface.ControlInterface;
import UserInterface.NotificationInterface;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ShortTermScheduler implements Runnable, ControlInterface, InterSchedulerInterface {
    private Queue<Process> readyQueue;
    private Queue<Process> blockedQueue;
    private Queue<Process> terminatedQueue;
    private Process executionProcess;
    private NotificationInterface userInterface;
    private boolean suspended = false;
    private int quantum;

    public ShortTermScheduler(int quantum) {
        this.readyQueue = new ConcurrentLinkedQueue<>();
        this.blockedQueue = new ConcurrentLinkedQueue<>();
        this.terminatedQueue = new ConcurrentLinkedQueue<>();
        this.quantum = quantum;
        this.suspended = false;
        this.executionProcess = null;
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
        userInterface.display("<ss> <2> [ShortTermScheduler] Pronto.");
        while (true) {
            if(!suspended) {
                if (!readyQueue.isEmpty()) {
                    Process currentProcess = readyQueue.poll();
                    if (currentProcess != null) {
                        executeProcess(currentProcess);
                    }
                }else {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ie) {
                        System.err.println("<ss> <2> [ShortTermScheduler] Interrompido.");
                    }
                }
                checkBlockedQueue();
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ie) {
                    System.err.println("<ss> <2> [ShortTermScheduler] Interrompido.");
                }
            }
            if (readyQueue.isEmpty() && blockedQueue.isEmpty()) {
                // Termina a simulação quando não há mais processos prontos ou bloqueados
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    System.err.println("<ss> <2> [ShortTermScheduler] Interrompido.");
                }
            }
        }
    }

    private void executeProcess(Process process) {
        executionProcess = process;
        int remainingTime = process.getRemainingTime();

        while(remainingTime > 0){

            process.setRemainingTime(remainingTime-1);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e){
                System.err.println("<ss> <2> [ShortTermScheduler] Interrupted while waiting for process to finish.");
            }
            while(suspended) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e){
                    System.err.println("<ss> <2> [ShortTermScheduler] Interrupted while waiting for process to finish.");
                }
            }
            String command = process.getNextInstruction();
            userInterface.display("<ss> <1> Processo: " + process.getId() + " executa comando: " + command);
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
            executionProcess = process;
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
        userInterface.display("<ss> <2> [ShortTermScheduler] Process " + process.getId() + " blocked for " + process.getBlockTime() + " quanta.");
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
        userInterface.display("<ss> <2> [ShortTermScheduler] Simulation was suspended.");
        suspended = true;
    }

    @Override
    public void resumeSimulation() {
        userInterface.display("<ss> <3> [ShortTermScheduler] Simulation was resumed.");
        suspended = false;
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
        userInterface.display("<ss> <3> [ShortTermScheduler] Ready Process Queue: \n"+ readyQueue.toString());
        userInterface.display("<ss> <3> [ShortTermScheduler] Blocked Process Queue: \n"+ blockedQueue.toString());
        userInterface.display("<ss> <3> [ShortTermScheduler] Terminated Process Queue: \n"+ terminatedQueue.toString());
        userInterface.display("<ss> <3> [ShortTermScheduler] Execution Process: \n"+ executionProcess.toString());
    }
}
