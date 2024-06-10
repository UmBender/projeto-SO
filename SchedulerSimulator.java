public class SchedulerSimulator {
	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		LongTermScheduler longTermScheduler = new LongTermScheduler();
		ShortTermScheduler shortTermScheduler = new ShortTermScheduler();

		ui.setThreads(shortTermScheduler, longTermScheduler);
		longTermScheduler.setThreads(ui, shortTermScheduler);
		shortTermScheduler.setThreads(ui);

		Process process = new Process("/home/bender/USP5/SO/exercicios/projeto_java/teste.txt");
		shortTermScheduler.addProcess(process);
	}
}
