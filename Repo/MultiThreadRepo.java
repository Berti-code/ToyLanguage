package Repo;

import Model.PrgState;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadRepo implements IRepo{
    private List<PrgState> programStates;
    private String logFilePath;

    public MultiThreadRepo() {
        this.programStates = new ArrayList<>();
    }

    @Override
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }


    @Override
    public void addProgramState(PrgState newProgramState) {
        programStates.add(newProgramState);
    }

    @Override
    public List<PrgState> getProgramStateList() {
        return programStates;
    }

    @Override
    public void setProgramStateList(List<PrgState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void logProgramStateExecution(PrgState programState) {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException e) {
            throw new RuntimeException("cannot log program state\n");
        }
        logFile.write(programState.toString());
        logFile.close();
    }
}
