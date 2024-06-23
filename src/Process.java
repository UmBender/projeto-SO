import UserInterface.NotificationInterface;

import java.util.Vector;
import java.util.Random;

public class Process {
    final private String pid;
    final private int quantum;
    private int number_quantum;
    private int remainingTime;
    private int blockTime;
    private final Vector<String> instructions;
    private int blockPeriod;
    private int instruction_number;
    final private NotificationInterface userInterface;

    public Process(String pid, int quantum, Vector<String> instructions, NotificationInterface userInterface) {
        this.quantum = quantum;
        this.number_quantum = 4;
        this.remainingTime = this.number_quantum * this.quantum;
        this.instructions = instructions;
        this.instruction_number = 0;
        this.userInterface = userInterface;


        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        rand.nextInt(1024);
        this.pid = pid + "_" + Integer.toHexString(rand.nextInt());
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
        return getCommand(instruction);
    }

    @Override
    public String toString(){
        return "\nPid: " +pid + "\nRemainingTime: " + remainingTime + "\nBlockTime: " + blockTime + "\n";

    }

    public void resetRemaningTime() {
        if(number_quantum >=4 ){

            this.remainingTime = this.quantum * 4;
        }else{
            this.remainingTime = this.quantum;
        }
    }

    public void upgradeNumberQuantum() {
        if(number_quantum < 6) {
            this.number_quantum++;
        }
    }
    public void downgradeNumberQuantum() {
        if(number_quantum > 2) {
            number_quantum--;
        }
    }
}
