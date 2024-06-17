import UserInterface.UserInterface;

public class SchedulerSimulator {
	public static void main(String[] args) {
		int quantum = 200; // Defina o quantum de tempo

		// Estancia as classes das threads
		ShortTermScheduler shortTermScheduler = new ShortTermScheduler(quantum);
		LongTermScheduler longTermScheduler = new LongTermScheduler();
		UserInterface userInterface = new UserInterface();

		// Passa as interfaces para as outros objetos
		shortTermScheduler.setThreads(userInterface);
		longTermScheduler.setThreads(userInterface,shortTermScheduler);
		userInterface.setThreads(shortTermScheduler, longTermScheduler);

		// Criar as threads
		Thread shortTermSchedulerThread = new Thread(shortTermScheduler);
		Thread longTermSchedulerThread = new Thread(longTermScheduler);
		Thread userInterfaceThread = new Thread(userInterface);

		longTermScheduler.submitJob("/home/bender/USP5/SO/exercicios/projeto-SO/src/resources/teste.txt");

		// Inicia as threads
		shortTermSchedulerThread.start();
		longTermSchedulerThread.start();
		userInterfaceThread.start();

		try {
			shortTermScheduler.displayProcessQueues();
			shortTermSchedulerThread.join();
			longTermSchedulerThread.join();
			userInterfaceThread.join();
		} catch (InterruptedException ie){
			System.err.println(ie.getMessage());

		}

		System.out.println("Acabou");
	}
}


