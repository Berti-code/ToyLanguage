package View;

import View.Commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private final Map<String, Command> commands;
    private final static boolean exitCommandNotGiven = true;

    public Menu() {
        this.commands = new HashMap<>();
    }

    public void addCommand(Command command){
        commands.put(command.getKey(),command);
    }

    public void printMenu(){
        System.out.println("\t\tMenu");
        for(Command command : commands.values()){
            String line = String.format("%4s : %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    @SuppressWarnings("all")
    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(exitCommandNotGiven){
            printMenu();
            System.out.println("> ");
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if(command == null){
                System.out.println("[!] Invalid option");
                continue;
            }
            command.execute();
        }
    }
}
