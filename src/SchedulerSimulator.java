import UserInterface.UserInterface;

public class SchedulerSimulator {
	public static void main(String[] args) {
		int quantum = 200; // Defina o quantum de tempo

		// Instancia as classes das threads
		ShortTermScheduler shortTermScheduler = new ShortTermScheduler(quantum);
		LongTermScheduler longTermScheduler = new LongTermScheduler();
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


