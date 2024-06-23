import UserInterface.UserInterface;

public class SchedulerSimulator {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java -jar SchedulerSimulator <load>");
			return;
		}
		int short_term_scheduler_load = 0;
		try {
			short_term_scheduler_load = Integer.parseInt(args[0]);
		}catch (NumberFormatException nf) {
			System.out.println("Invalid load parameter");
			return;
		}
		if(short_term_scheduler_load <= 0)  {
			System.out.println("Invalid load parameter");
			return;
		}
		int quantum = 200; // Defina o quantum de tempo

		// Instancia as classes das threads
		ShortTermScheduler shortTermScheduler = new ShortTermScheduler(quantum);
		LongTermScheduler longTermScheduler = new LongTermScheduler(quantum, short_term_scheduler_load);
		UserInterface userInterface = new UserInterface();

		// Passa as interfaces para as outros objetos
		shortTermScheduler.setThreads(userInterface);
		longTermScheduler.setThreads(userInterface, shortTermScheduler);
		userInterface.setThreads(shortTermScheduler, longTermScheduler);

		// Criar as threads
		Thread shortTermSchedulerThread = new Thread(shortTermScheduler);
		Thread longTermSchedulerThread = new Thread(longTermScheduler);
		Thread userInterfaceThread = new Thread(userInterface);

		// Inicia as threads
		userInterfaceThread.start();
		shortTermSchedulerThread.start();
		longTermSchedulerThread.start();

		try {
			shortTermScheduler.displayProcessQueues();
			shortTermSchedulerThread.join();
			longTermSchedulerThread.join();
			userInterfaceThread.join();
		} catch (InterruptedException ie){
			userInterface.display(ie.getMessage());
		}
	}
}


