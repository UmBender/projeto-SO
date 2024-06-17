//IO Modules
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

//Instructions data structure
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Vector;

public class ParserBNF {
    final private Vector<String> instructions;

    public ParserBNF(){
        this.instructions = new Vector<String>();
    }

    public Vector<String> parse(String fileName) {
        File programFile = new File(fileName);

        try {
            Scanner reader = new Scanner(programFile);

            reader.nextLine(); //Program name
            reader.nextLine(); //Program begin

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                instructions.add(data);
            }
            reader.close();
            this.print();
        } catch (FileNotFoundException e) {
            System.err.println("[Error] Arquivo não encontrado: " + fileName);
        }
        return instructions;
    }

    private void print() {
        int n = instructions.size();
        Iterator<String> instructionsIterator = instructions.iterator();

        System.out.println("[Parser] Read the follow instructions:" );

        for (int i = 0; i < n; i++) {
            String output = String.format("-> (%d) %s",
                    i, instructionsIterator.next());
            System.out.println(output);
        }
    }
}
