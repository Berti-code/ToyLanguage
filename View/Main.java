package View;

import Controller.Controller;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Repo.IRepo;
import Repo.MultiThreadRepo;
import View.Commands.ExitCommand;
import View.Commands.RunExampleCommand;

public class Main {

    //int v; v=2; Print(v)
    private static Controller example1() {
        IRepo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        IDict<String, Type> typeEnvironment = new Dict<>();
        IStmt program1 =
                new CompStmt(
                        new VarDecStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("v", new ValExp(new IntValue(2))),
                                new PrintStmt(new VarExp("v"))
                        )
                );

        program1.typeChecker(typeEnvironment);

        IStack<IStmt> executionStack = new MyStack<>();
        executionStack.push(program1);

        IDict<String, Value> symbolTable = new Dict<>();
        IList<Value> out = new List<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);

        return controller;
    }

    //int a; int b; a=2+3*5; b=a+1; Print(b)
    private static Controller example2() {
        IRepo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        IDict<String, Type> typeEnvironment = new Dict<>();
        IStmt program1 =
                new CompStmt(
                        new VarDecStmt("a", new IntType()),
                        new CompStmt(
                                new VarDecStmt("b",new IntType()),
                                new CompStmt(
                                        new AssignStmt("a",
                                                new ArithExp(
                                                        new ValExp(new IntValue(2)),
                                                        new ArithExp(
                                                                new ValExp(new IntValue(3)),
                                                                new ValExp(new IntValue(5)),
                                                                3),
                                                        1)
                                        ),
                                        new CompStmt(
                                                new AssignStmt("b",
                                                        new ArithExp(
                                                                new VarExp("a"),
                                                                new ValExp(new IntValue(1)),
                                                                1
                                                        )
                                                ),
                                                new PrintStmt(new VarExp("b"))
                                        )
                                )
                        )
                );

        program1.typeChecker(typeEnvironment);

        IStack<IStmt> executionStack = new MyStack<>();
        executionStack.push(program1);

        IDict<String, Value> symbolTable = new Dict<>();
        IList<Value> out = new List<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);
        return controller;
    }

    //bool a; int v; a=true; If(a)Then(v=2)Else(v=3); Print(v)
    private static Controller example3() {
        IRepo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        IDict<String, Type> typeEnvironment = new Dict<>();
        IStmt program1 =
                new CompStmt(
                        new VarDecStmt("a", new BoolType()),
                        new CompStmt(
                                new VarDecStmt("v",new IntType()),
                                new CompStmt(
                                        new AssignStmt("a",new ValExp(new BoolValue(true))),
                                        new CompStmt(
                                                new IfStmt(
                                                        new VarExp("a"),
                                                        new AssignStmt("v",new ValExp(new IntValue(2))),
                                                        new AssignStmt("v",new ValExp(new IntValue(3)))
                                                ),
                                                new PrintStmt(new VarExp("v"))
                                        )
                                )
                        )
                );

        program1.typeChecker(typeEnvironment);

        IStack<IStmt> executionStack = new MyStack<>();
        executionStack.push(program1);

        IDict<String, Value> symbolTable = new Dict<>();
        IList<Value> out = new List<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);
        return controller;
    }

    //int v; v=4; while(v>0) print(v);v=v-1;
    private static Controller example4() {
        IRepo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        IDict<String, Type> typeEnvironment = new Dict<>();
        IStmt program1 =
                new CompStmt(
                        new VarDecStmt("v",new IntType()),
                        new CompStmt(
                                new AssignStmt("v",new ValExp(new IntValue(4))),
                                new WhileStmt(
                                        new RelExp(
                                                new VarExp("v"),
                                                new ValExp(new IntValue(0)),
                                                new StringValue(">")
                                        ),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new AssignStmt(
                                                        "v",
                                                        new ArithExp(
                                                                new VarExp("v"),
                                                                new ValExp(new IntValue(1)),
                                                                2
                                                        )
                                                )
                                        )
                                )
                        )
                );

        program1.typeChecker(typeEnvironment);

        IStack<IStmt> executionStack = new MyStack<>();
        executionStack.push(program1);

        IDict<String, Value> symbolTable = new Dict<>();
        IList<Value> out = new List<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);
        return controller;
    }

    //Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); printf(rH(rH(a)))
    private static Controller example5() {
        IRepo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        IDict<String, Type> typeEnvironment = new Dict<>();
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

        program1.typeChecker(typeEnvironment);

        IStack<IStmt> executionStack = new MyStack<>();
        executionStack.push(program1);

        IDict<String, Value> symbolTable = new Dict<>();
        IList<Value> out = new List<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);
        return controller;
    }

    //int v; Ref int a; v=10; new(a,22); fork(wH(a,30);v=32;printf(v);print(rH(a)) ); printf(v); print(rH(a))
    private static Controller example6() {
        IRepo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        IDict<String, Type> typeEnvironment = new Dict<>();
        IStmt program = new CompStmt(
                new VarDecStmt("v",new IntType()),
                new CompStmt(
                        new VarDecStmt("a",new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v",new ValExp(new IntValue(10))),
                                new CompStmt(
                                        new AllocStmt("a",new ValExp(new IntValue(10))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new WriteToHeapStmt("a",new ValExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v",new ValExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new ReadHeap(new VarExp("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeap(new VarExp("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        program.typeChecker(typeEnvironment);

        IStack<IStmt> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new Dict<>();
        IList<Value> out = new List<>();

        executionStack.push(program);
        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);
        return controller;
    }

    // Ref int a; new(a,20); (for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a))); print(rh(a))
    private static Controller example7() {
        IRepo repository = new MultiThreadRepo();
        Controller controller = new Controller(repository);
        controller.setMuteLogProgramStateExecution(true);
        IDict<String, Type> typeEnvironment = new Dict<>();
        IStmt program =
                new CompStmt(
                        new VarDecStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AllocStmt("a",new ValExp(new IntValue(20))),
                                new CompStmt(
                                       new CompStmt(
                                                new VarDecStmt("v", new IntType()),
                                        new ForStmt(
                                                new ValExp(new IntValue(0)),
                                                new ValExp(new IntValue(3)),
                                                new ValExp(new IntValue(1)),
                                                "v",
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new PrintStmt(new VarExp("v")),
                                                                new AssignStmt("v", new ArithExp(new VarExp("v"), new ReadHeap(new VarExp("a")), 3))
                                                        )
                                                )
                                        )
                                        ),
                                        new PrintStmt(new ReadHeap(new VarExp("a")))
                                )
                        )
                );

        program.typeChecker(typeEnvironment);

        IStack<IStmt> executionStack = new MyStack<>();
        IDict<String, Value> symbolTable = new Dict<>();
        IList<Value> out = new List<>();

        executionStack.push(program);
        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        controller.addProgram(myProgramState);
        return controller;

    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.addCommand(new ExitCommand("0","exit"));
        menu.addCommand(new RunExampleCommand("1","int v; v=2; Print(v)",example1()) );
        menu.addCommand(new RunExampleCommand("2","int a; int b; a=2+3*5; b=a+1; Print(b)",example2()) );
        menu.addCommand(new RunExampleCommand("3","bool a; int v; a=true; If(a)Then(v=2)Else(v=3); Print(v)",example3()) );
        menu.addCommand(new RunExampleCommand("4","int v; v=4; while(v>0) print(v);v=v-1",example4()));
        menu.addCommand(new RunExampleCommand("5","Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); printf(rH(rH(a)))",example5()));
        menu.addCommand(new RunExampleCommand("6","int v; Ref int a; v=10; new(a,22); fork(wH(a,30);v=32;printf(v);print(rH(a)) ); printf(v); print(rH(a))",example6()));
        menu.addCommand(new RunExampleCommand("7","Ref int a; new(a,20); (for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a))); print(rh(a))",example7()));

        menu.show();
    }
}
