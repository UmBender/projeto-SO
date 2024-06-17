//IO Modules
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

//Instructions data structure
import java.util.Vector;

public class ParserBNF {
    final private Vector<String> instructions;


    public ParserBNF(){
        this.instructions = new Vector<String>();
    }

    public Vector<String> parse(String fileName) throws FileNotFoundException, ParseException {
        File programFile = new File(fileName);
        String program_name;
        String program_begin;
        String program_end;
        String realFileName;

        /*
        TODO Modifique aqui se for usar windows a forma que muda o parse
         */
        String[] splited_path = fileName.split("/");
        realFileName = splited_path[splited_path.length - 1];

        try {
            Scanner reader = new Scanner(programFile);

            program_name = reader.nextLine(); //Program name
            program_begin = reader.nextLine(); //Program begin

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                instructions.add(data);
            }

            program_end = instructions.removeLast();
            reader.close();
            this.print();
        } catch (FileNotFoundException e) {
            System.err.println("[Error] Arquivo não encontrado: " + fileName);
            throw new FileNotFoundException("[Error] Arquivo não encontrado: " + fileName);
        }

        if(!validateBegin(program_begin) || !validateEnd(program_end) ||
        !validateName(realFileName, program_name) || !validateProgram()){
            throw new ParseException("[Error] Arquivo não segue padrão de formação",0);
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

    private boolean validateName(String fileName, String name){
        String[] header = name.split(" ");
        if(header.length != 2) {
            return false;
        }
        return Objects.equals(header[0], "program") && Objects.equals(header[1], fileName);
    }

    private boolean validateBegin(String begin) {
        return Objects.equals(begin, "begin");
    }

    private boolean validateEnd(String end){
        return Objects.equals(end, "end");
    }

    private boolean validateProgram(){
        if(instructions.isEmpty()) {
            return false;
        }
        for(String i: instructions) {
            if(Objects.equals(i, "execute")) {
                continue;
            } else {
                String[] arr = i.split(" ");
                if(arr.length!=2) {
                    return false;
                }
                int value = -1;

                try {
                    value = Integer.parseInt(arr[1]);
                }catch (NumberFormatException nfe) {
                    return false;
                }

                if(!Objects.equals(arr[0], "block") || value > 5 || value < 1){
                    return false;
                }

            }
        }
        return true;
    }
}
