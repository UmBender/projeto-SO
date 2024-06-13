import java.util.Queue;

public class Process {
    private String id;
    private int remainingTime;
    private int blockTime;
    private Queue<String> instructions;

    public Process(String id, int remainingTime, Queue<String> instructions) {
        this.id = id;
        this.remainingTime = remainingTime;
        this.instructions = instructions;
    }

    public String getId() {
        return id;
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

    public String getNextInstruction() {
        return instructions.peek();
    }

    public int getNextBlockPeriod() {
        String instruction = instructions.poll();
        if (instruction.startsWith("block")) {
            return Integer.parseInt(instruction.split(" ")[1]);
        }
        return 0;
    }
}
