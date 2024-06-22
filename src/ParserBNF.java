//IO Modules
import UserInterface.NotificationInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

//Instructions data structure
import java.util.Vector;

public class ParserBNF {
    final private Vector<String> instructions;
    NotificationInterface userInterface;

    public ParserBNF(NotificationInterface userInterface){
        this.instructions = new Vector<String>();
        this.userInterface = userInterface;
    }

    public Vector<String> parse(String fileName) throws FileNotFoundException, ParseException {
        File programFile = new File(fileName);
        String program_name;
        String program_begin;
        String program_end;
        String realFileName;

        // Detecção do sistema operacional
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")){
            realFileName = "";
        }
        else if (os.contains("osx")){
            realFileName = "";
        }
        else if (os.contains("nix") || os.contains("aix") || os.contains("nux")){
            String[] splited_path = fileName.split("/");
            realFileName = splited_path[splited_path.length - 1];
        } else {
            realFileName = "";
        }


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
            userInterface.display("<is> [Parser Error] Arquivo não encontrado: " + fileName);
            throw new FileNotFoundException("<is> [Parser Error] Arquivo não encontrado: " + fileName);
        }

        // Validações para os Programs (Definidos pela BNF)
        List<NamedValidation> validations = new ArrayList<>();
        validations.add(new NamedValidation("validateBegin", () -> validateBegin(program_begin)));
        validations.add(new NamedValidation("validateEnd", () -> validateEnd(program_end)));
        validations.add(new NamedValidation("validateName", () -> validateName(realFileName, program_name)));
        validations.add(new NamedValidation("validateProgram", this::validateProgram));

        for (NamedValidation validation : validations) {
            if (!validation.getValidationFunction().get()) {
                throw new ParseException("<is> [Parser Error] Arquivo não segue padrão de formação: " + validation.getName(), 0);
            }
        }

        instructions.add(program_end);
        return instructions;
    }

    private void print() {
        int n = instructions.size();
        Iterator<String> instructionsIterator = instructions.iterator();

        userInterface.display("<is> [Parser] Lendo as seguintes instruções:" );

        for (int i = 0; i < n; i++) {
            String output = String.format("<is> -> (%d) %s",
                    i, instructionsIterator.next());
            userInterface.display(output);
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
