package Tests;

import Model.PrgState;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;
import Model.adt.*;
import Model.exp.ArithExp;
import Model.exp.ReadHeap;
import Model.exp.ValExp;
import Model.exp.VarExp;
import Model.stmt.*;
import Repo.IRepo;
import Repo.MultiThreadRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Controller.Controller;

public class TestController {

    @Test
    public void shouldUpdateOutTable() {
        IRepo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        controller.setMutePrintProgramStateExecution(true);
        IStmt originalProgram =
                new PrintStmt(new ValExp(new IntValue(50)));

        IStack<IStmt> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new Dict<>();
        IList<Value> out = new List<>();

        executionStack.push(originalProgram);

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);
        controller.allStep();

        Assertions.assertEquals(out.pop(),new IntValue(50));
    }

    @Test
    public void shouldUpdateSymbolTable() {
        IRepo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        controller.setMutePrintProgramStateExecution(true);
        IStmt originalProgram =
            new CompStmt(
                new VarDecStmt("v", new IntType()),
                new IfStmt(
                    new ValExp(new BoolValue(true)),
                    new CompStmt(
                        new AssignStmt("v", new ValExp(new IntValue(5))),
                        new PrintStmt(
                                new ArithExp(
                                    new VarExp("v"),
                                    new ValExp(new IntValue(3)),
                                    1
                        ) ) ) ,
                    new PrintStmt(new ValExp(new IntValue(100) ) ) )
            );

        IStack<IStmt> executionStack = new MyStack<>();
        executionStack.push(originalProgram);

        IDict<String, Value> symbolTable = new Dict<>();
        IList<Value> out = new List<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);

        controller.allStep();

        Assertions.assertEquals(symbolTable.lookup("v"), new IntValue(5));

    }


    @Test
    public void shouldUpdateStack() {
        IRepo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        controller.setMutePrintProgramStateExecution(true);
        IStmt originalProgram =
                new CompStmt(
                        new VarDecStmt("v", new IntType()),
                        new IfStmt(
                                new ValExp(new BoolValue(true)),
                                new CompStmt(
                                        new AssignStmt("v", new ValExp(new IntValue(5))),
                                        new PrintStmt(
                                                new ArithExp(
                                                        new VarExp("v"),
                                                        new ValExp(new IntValue(3)),
                                                        1
                                                ) ) ) ,
                                new PrintStmt(new ValExp(new IntValue(100) ) ) )
                );

        IStack<IStmt> executionStack = new MyStack<>();
        executionStack.push(originalProgram);

        IDict<String, Value> symbolTable = new Dict<>();
        IList<Value> out = new List<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);

        controller.allStep();

        Assertions.assertTrue(executionStack.isEmpty());

    }

    @Test
    void shouldUpdateHeap() {
        IRepo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        controller.setMutePrintProgramStateExecution(true);
        IStmt program1 =
                new CompStmt(
                        new VarDecStmt("v", new RefType(new IntType())),
                        new CompStmt(
                                new AllocStmt("v",new ValExp(new IntValue(20))),
                                new CompStmt(
                                        new VarDecStmt("a",new RefType(new RefType(new IntType()))),
                                        new CompStmt(
                                                new AllocStmt("a",new VarExp("v")),
                                                new CompStmt(
                                                        new AllocStmt("v",new ValExp(new IntValue(30))),
                                                        new PrintStmt(new ReadHeap(new ReadHeap(new VarExp("a"))))
                                                )

                                        )
                                )
                        )
                );

        IStack<IStmt> executionStack = new MyStack<>();
        executionStack.push(program1);

        IDict<String, Value> symbolTable = new Dict<>();
        IList<Value> out = new List<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);
        controller.allStep();
    }
}
