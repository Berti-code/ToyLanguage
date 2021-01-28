package View.Commands;

import Controller.Controller;

import java.io.IOError;
import java.io.IOException;

public class RunExampleCommand extends Command {
    private final Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        } catch (RuntimeException e) {
            System.out.println(e.toString());
        }
    }
}
