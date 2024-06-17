import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Vector;

public class SchedulerSimulator {
	public static void main(String[] args) {
		int quantum = 200; // Defina o quantum de tempo
		ShortTermScheduler scheduler = new ShortTermScheduler(quantum);
		ParserBNF parser = new ParserBNF();

		// Só não se esquece de mudar aqui
		Vector<String> instructions = parser.parse(
				"/home/bender/USP5/SO/exercicios/projeto-SO/src/resources/teste.txt"
		);

		Process p1 = new Process("Process1", 600, instructions);

		scheduler.addProcess(p1);

		Thread schedulerThread = new Thread(scheduler);
		schedulerThread.start();
	}
}


