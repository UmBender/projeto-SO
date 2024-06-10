import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Process {
    final private int pid;
    final private String file_name;
    final private Vector<String> program_statement;
    private int quantum;
    private int process_status;

    private void setProgram_statemnt() {
        File programa_file = new File(file_name);
        try {
            Scanner reader = new Scanner(programa_file);
            while(reader.hasNextLine()) {
                String data = reader.nextLine();
                program_statement.add(data);
            }
            program_statement.remove(0);
            program_statement.remove(0);
            program_statement.remove(program_statement.lastElement());
            reader.close();
        }catch(FileNotFoundException e) {
            System.err.println("O arquivo com este nome não existe: " + file_name);
            e.printStackTrace();
        }

    }

    public int getQuantum() {
        return quantum;
    }

    public void quantumUpgrade() {
        if(quantum >= 400) {
            return;
        }
        quantum *= 2;

    }

    public void quantumDowngrade() {
        if (quantum <= 25) {
            return;
        }
        quantum /=2;
    }

    public Process(String file_name) {
        pid = 100; //TODO PID Generator
        process_status = 0; // TODO Status
        program_statement = new Vector();
        quantum = 200;
        this.file_name = file_name;
        this.setProgram_statemnt();

        for(String i: program_statement) {
            System.out.println(i);
        }

    }
}
