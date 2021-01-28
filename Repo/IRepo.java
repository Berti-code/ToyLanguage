package Repo;
import Model.PrgState;


import java.io.IOException;
import java.util.List;


public interface IRepo {
    void addProgramState(PrgState newProgramState);
    void setLogFilePath(String filePath);

    void logProgramStateExecution(PrgState programState);
    List<PrgState> getProgramStateList();
    void setProgramStateList(List<PrgState> newProgramStateList);
    }
