import UserInterface.ControlInterface;
import UserInterface.NotificationInterface;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ShortTermScheduler implements Runnable, ControlInterface, InterSchedulerInterface {
    private Queue<Process> readyQueue;
    private Queue<Process> blockedQueue;
    private Queue<Process> terminatedQueue;
    private NotificationInterface userInterface;
    private int quantum;

    public ShortTermScheduler(int quantum) {
        this.readyQueue = new ConcurrentLinkedQueue<>();
        this.blockedQueue = new ConcurrentLinkedQueue<>();
        this.terminatedQueue = new ConcurrentLinkedQueue<>();
        this.quantum = quantum;
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
        userInterface.display("[ShortTermScheduler] Pronto.");
        while (true) {
            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                if (currentProcess != null) {
                    executeProcess(currentProcess);
                }
            }
            checkBlockedQueue();
            if (readyQueue.isEmpty() && blockedQueue.isEmpty()) {
                // Termina a simulação quando não há mais processos prontos ou bloqueados
                break;
            }
        }
    }

    private void executeProcess(Process process) {
        int remainingTime = process.getRemainingTime();

        if (remainingTime > quantum) {
            process.setRemainingTime(remainingTime - quantum);

            try {
                Thread.sleep(quantum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String command = process.getNextInstruction();

            switch (command) {
                case "block":
                    processBlock(process);
                    break;
                case "execute":
                    readyQueue.add(process);
                    break;
                case "end":
                    break;
                default:
                    userInterface.display("[ShortTermScheduler] Comando desconhecido: " + command);
                    terminatedQueue.add(process);
            }

        } else {
            userInterface.display("[ShortTermScheduler] Process " + process.getId() + " finished execution.");
            terminatedQueue.add(process);
        }
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
        userInterface.display(readyQueue.toString());
        userInterface.display(blockedQueue.toString());
        userInterface.display(terminatedQueue.toString());
        /*
        TODO Fazer com formatação bonitinha;
         */
    }
}
