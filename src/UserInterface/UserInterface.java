package UserInterface;

//Project modules
import Interfaces.ControlInterface;
import UserInterface.UIComponents.MenuLook;
import UserInterface.UIComponents.Slider;

//Java UI libs
import javax.swing.*;
import java.awt.BorderLayout;

//Java utils
import java.util.ArrayList;

public class UserInterface implements ControlInterface, Runnable {
    private static ArrayList<JButton> createButtons(){
        ArrayList<JButton> ButtonList = new ArrayList<JButton>();

        JButton b2 = new JButton("Middle button");
        b2.setVerticalTextPosition(AbstractButton.BOTTOM);
        b2.setHorizontalTextPosition(AbstractButton.CENTER);

        ButtonList.add(b2);

        return ButtonList;
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("UserIntarface GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Slider animator = new Slider();

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        frame.setJMenuBar(new MenuLook().createMenuBar());

        ArrayList<JButton> ButtonList =  createButtons();
        for (JButton jButton : ButtonList) {
           frame.add(jButton);
        }
        frame.add(animator, BorderLayout.CENTER);
        //frame.add(new TextArea());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        animator.startAnimation();
    }

    public void run() {
        createAndShowGUI();
    }

    public void startSimulaton(){

    }
    public void suspendSimulation(){

    }
    public void resumeSimulation(){

    }
    public void stopSimulation(){

    }
    public void displayProcessQueues(){

    }
}