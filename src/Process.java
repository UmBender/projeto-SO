import UserInterface.NotificationInterface;

import java.util.Vector;

public class Process {
    final private String pid;
    private int remainingTime;
    private int blockTime;
    private final Vector<String> instructions;
    private int blockPeriod;
    private int instruction_number;
    private NotificationInterface userInterface;

    public Process(String pid, int remainingTime, Vector<String> instructions, NotificationInterface userInterface) {
        this.pid = pid;
        this.remainingTime = remainingTime;
        this.instructions = instructions;
        this.instruction_number = 0;
        this.userInterface = userInterface;
    }
    public String getId() {
        return pid;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(int blockTime) {
        this.blockTime = blockTime;
    }

    public void decrementBlockTime() {
        if (blockTime > 0) {
            blockTime--;
        }
    }

    public String getCommand(String instruction){
        String[] substrings = instruction.split(" ");
        String command = substrings[0];

        if(substrings.length > 1){
            this.blockPeriod = Integer.parseInt(substrings[1]);
        }

        return command;
    }

    public int getNextBlockPeriod(){
        return this.blockPeriod;
    }

    public String getNextInstruction() {
        String instruction = instructions.get(instruction_number);
        instruction_number++;
        String pid = this.getId();

        assert instruction != null;
        userInterface.display(String.format("PID (%s). %s\n", pid, instruction));
        return getCommand(instruction);
    }

    @Override
    public String toString(){
        return "\nPid: " +pid + "\nRemainingTime: " + remainingTime + "\nBlockTime: " + blockTime + "\n";

    }
}
