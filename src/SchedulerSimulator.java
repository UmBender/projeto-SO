import UserInterface.UserInterface;

import ca.uqac.lif.bullwinkle.BnfParser;
import ca.uqac.lif.bullwinkle.ParseNode;
import ca.uqac.lif.bullwinkle.output.GraphvizVisitor;

import java.io.InputStream;

public class SchedulerSimulator {
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		UserInterface ui = new UserInterface();
		javax.swing.SwingUtilities.invokeLater(ui);

		LongTermScheduler longTermScheduler = new LongTermScheduler();
		ShortTermScheduler shortTermScheduler = new ShortTermScheduler();

		ui.setThreads(shortTermScheduler, longTermScheduler);
		longTermScheduler.setThreads(ui, shortTermScheduler);
		shortTermScheduler.setThreads(ui);


		Process process = new Process("C:\\Users\\Tigrocomputer\\Documents\\code-workspace (local)\\SchedulerSimulator\\src\\resources\\teste.txt");
		shortTermScheduler.addProcess(process);
	}
	private static void useBullwinkle(){
		// Load the resource file
		InputStream inputStream = SchedulerSimulator.class.getResourceAsStream("/Simple-Math.bnf");
		if (inputStream == null) {
			System.out.println("Resource not found!");
			return;
		}

		try
		{
			BnfParser parser = new BnfParser(inputStream);
			ParseNode node2 = parser.parse("10 + (3 - 4)");
			GraphvizVisitor visitor = new GraphvizVisitor();
			node2.prefixAccept(visitor);
			System.out.println(visitor.toOutputString());
		}
		catch (Exception e)
		{
			System.err.println("Some error occurred");
			e.printStackTrace();
		}
	}
}
