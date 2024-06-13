import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ShortTermScheduler implements Runnable {

    private Queue<Process> readyQueue;    // Fila de processos prontos
    private Queue<Process> blockedQueue;  // Fila de processos bloqueados
    private Queue<Process> terminatedQueue; // Fila de processos terminados
    private int quantum; // Quantum de tempo

    public ShortTermScheduler(int quantum) {
        this.readyQueue = new ConcurrentLinkedQueue<>();
        this.blockedQueue = new ConcurrentLinkedQueue<>();
        this.terminatedQueue = new ConcurrentLinkedQueue<>();
        this.quantum = quantum;
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
        while (true) {
            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                if (currentProcess != null) {
                    executeProcess(currentProcess);
                }
            }
            checkBlockedQueue();
            if (readyQueue.isEmpty() && blockedQueue.isEmpty()) {
                break; // Termina a simulação quando não há mais processos prontos ou bloqueados
            }
        }
    }

    // Executa um processo
    private void executeProcess(Process process) {
        int remainingTime = process.getRemainingTime();
        if (remainingTime > quantum) {
            process.setRemainingTime(remainingTime - quantum);
            System.out.println("Executing process: " + process.getId());
            try {
                Thread.sleep(quantum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (process.getNextInstruction().equals("block")) {
                processBlock(process);
            } else {
                readyQueue.add(process);
            }
        } else {
            System.out.println("Process " + process.getId() + " finished execution.");
            terminatedQueue.add(process);
        }
    }

    // Verifica a fila de processos bloqueados e move processos de volta para a fila de prontos se desbloqueados
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

    // Bloqueia um processo
    private void processBlock(Process process) {
        process.setBlockTime(process.getNextBlockPeriod());
        blockedQueue.add(process);
        System.out.println("Process " + process.getId() + " blocked for " + process.getBlockTime() + " quanta.");
    }
}
