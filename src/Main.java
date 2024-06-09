import UserInterface.UserInterface;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new UserInterface());

		BnfParser parser = new BnfParser("Examples/Simple-Math.bnf");
		ParseNode node1 = parser.parse("3+4");
		ParseNode node2 = parser.parse("(10 + (3 - 4))");
	}

}
