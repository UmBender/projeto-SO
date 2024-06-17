import java.util.Vector;

public class Process {
    final private String pid;
    private int remainingTime;
    private int blockTime;
    private final Vector<String> instructions;
    private int blockPeriod;

    public Process(String pid, int remainingTime, Vector<String> instructions) {
        this.pid = pid;
        this.remainingTime = remainingTime;
        this.instructions = instructions;
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
        /*
        Corrigir pois aqui utilizava queue ao invés de vector
        String instruction = instructions.poll();
         */
        String instruction = instructions.get(0);
        String pid = this.getId();

        assert instruction != null;
        System.out.printf("PID (%s). %s\n", pid, instruction);
        return getCommand(instruction);
    }
}
