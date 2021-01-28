package Controller;
import Model.PrgState;
import Model.Values.RefValue;
import Model.Values.Value;
import Model.adt.IHeap;
import Repo.IRepo;


import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepo repository;
    private ExecutorService executorService;
    private boolean muteLogProgramStateExecution;
    private boolean mutePrintProgramStateExecution;

    public Controller(IRepo repository) {

        this.repository = repository;
        muteLogProgramStateExecution = false;
        mutePrintProgramStateExecution = false;
    }

    public void setMuteLogProgramStateExecution(boolean muteLogProgramStateExecution) {
        this.muteLogProgramStateExecution = muteLogProgramStateExecution;
    }

    public void setMutePrintProgramStateExecution(boolean mutePrintProgramStateExecution) {
        this.mutePrintProgramStateExecution = mutePrintProgramStateExecution;
    }

    public void addProgram(PrgState newPrg) {
        repository.addProgramState(newPrg);
    }

    private List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues){
        return  symbolTableValues.stream()
                .filter(value -> value instanceof RefValue)
                .map(value -> {
                    RefValue valueFromSymbolTable = (RefValue)value; return valueFromSymbolTable.getAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> getAddressesFromHeap(Collection<Value> heapValues){
        return heapValues.stream()
                .filter(value -> value instanceof RefValue)
                .map(value -> {RefValue valueFromHeap = (RefValue)value; return valueFromHeap.getAddress();})
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> garbageCollector(List<Integer> addressesFromSymbolTable, List<Integer> addressesFromHeapValues, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(entry -> addressesFromSymbolTable.contains(entry.getKey()) || addressesFromHeapValues.contains(entry.getKey()) )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<PrgState> removeCompletedPrograms(List<PrgState> programStateList) {
        return programStateList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void allStep() {
        repository.setLogFilePath("D:\\University\\MAP\\A6\\Interpretor_template1\\Output\\output.txt");
        executorService = Executors.newFixedThreadPool(2);

        // remove completed
        List<PrgState> programStateList = removeCompletedPrograms(repository.getProgramStateList());
        if (!mutePrintProgramStateExecution)
            System.out.println(programStateList.get(0).toStringAsCode());

        while(programStateList.size() > 0){
            // use garbageCollector
            // we can take the heap from the first programState since it's shared
            IHeap<Integer, Value> heap = programStateList.get(0).getHeap();
            for (PrgState programState: programStateList) {
                // for the symbol table we must take it for each due to it being a local state
                programState.getHeap().setContent(garbageCollector(
                        getAddressesFromSymbolTable(programState.getSymTable().getContent().values()),
                        getAddressesFromHeap(heap.getContent().values()),
                        heap.getContent()
                        )
                );
            }

            oneStepForAllPrg(programStateList);
            if (!mutePrintProgramStateExecution)
                for (PrgState programState: programStateList) {
                    System.out.println(programState.toString());
                }

            programStateList = removeCompletedPrograms(repository.getProgramStateList());
        }
        executorService.shutdown();

        repository.setProgramStateList(programStateList);
    }


    private void oneStepForAllPrg(List<PrgState> programStateList){
        if(!muteLogProgramStateExecution)
            programStateList.forEach(repository::logProgramStateExecution);

        // now we want to execute one step for each program states
        List<Callable<PrgState>> callableProgramStateList = programStateList
                .stream()
                .map(
                        // we change each programState into a Callable<ProgramState>
                        (PrgState programState) -> (Callable<PrgState>)(programState::oneStep)
                )
                .collect(Collectors.toList());

        try {
            List<PrgState> newProgramStateList = executorService.invokeAll(callableProgramStateList)
                    // in the stream we will have a Future for each program state containing it's execution result
                    .stream()
                    .map(programStateFuture -> {
                        try {
                            return programStateFuture.get();
                        } catch (InterruptedException e) {
                            throw new RuntimeException("InterruptedException()\n");
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                            throw new RuntimeException("ExecutionException()\n");
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // add the new ProgramStates
            programStateList.addAll(newProgramStateList);
            if(!muteLogProgramStateExecution)
                programStateList.forEach(repository::logProgramStateExecution);

            // save the modifications in the repository
            repository.setProgramStateList(programStateList);
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException\n");
        }
    }

}