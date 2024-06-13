import java.util.concurrent.ConcurrentLinkedQueue;

public class SchedulerSimulator {
	public static void main(String[] args) {
		int quantum = 200; // Defina o quantum de tempo
		ShortTermScheduler scheduler = new ShortTermScheduler(quantum);
		ParserBNF parser = new ParserBNF();

		ConcurrentLinkedQueue<String> instructions = parser.parse(
				"C:\\Users\\Tigrocomputer\\Documents\\code-workspace (local)\\SchedulerSimulator\\src\\resources\\teste.txt"
		);

		Process p1 = new Process("Process1", 600, instructions);

		scheduler.addProcess(p1);

		Thread schedulerThread = new Thread(scheduler);
		schedulerThread.start();
	}
}


