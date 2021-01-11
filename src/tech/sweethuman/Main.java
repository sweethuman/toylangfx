package tech.sweethuman;

import tech.sweethuman.adt.Heap;
import tech.sweethuman.adt.MyDictionary;
import tech.sweethuman.adt.MyList;
import tech.sweethuman.adt.MyStack;
import tech.sweethuman.controller.Controller;
import tech.sweethuman.domain.ProgramState;
import tech.sweethuman.domain.expression.*;
import tech.sweethuman.domain.statement.*;
import tech.sweethuman.domain.value.BoolValue;
import tech.sweethuman.domain.value.IntValue;
import tech.sweethuman.domain.value.StringValue;
import tech.sweethuman.domain.vartype.BoolType;
import tech.sweethuman.domain.vartype.IntType;
import tech.sweethuman.domain.vartype.RefType;
import tech.sweethuman.domain.vartype.StringType;
import tech.sweethuman.repository.IRepo;
import tech.sweethuman.repository.Repository;
import tech.sweethuman.view.TextMenu;
import tech.sweethuman.view.commands.ExitCommand;
import tech.sweethuman.view.commands.RunExample;

public class Main {
    static IStatement example1() {
        return new CompStmt(new VarDecl("x", new IntType()),
                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(20))), new PrintStmt(new Var("x"))));
    }

    static IStatement example1Error() {
        return new CompStmt(new VarDecl("x", new IntType()),
                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(20))), new PrintStmt(new Var("f"))));
    }

    static IStatement example2() {
        return new CompStmt(
                new VarDecl("a", new IntType()),
                new CompStmt(
                        new AssignStmt(
                                "a",
                                new ArithExp(
                                        1, new ValueExp(new IntValue(2)),
                                        new ArithExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)))
                                )
                        ),
                        new CompStmt(
                                new VarDecl("b", new IntType()),
                                new CompStmt(
                                        new AssignStmt("b", new ArithExp(1, new Var("a"), new ValueExp(new IntValue(1)))),
                                        new CompStmt(new PrintStmt(new Var("a")), new PrintStmt(new Var("b")))
                                )
                        )
                )
        );
    }

    static IStatement example3() {
        return new CompStmt(
                new VarDecl("a", new BoolType()),
                new CompStmt(
                        new VarDecl("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(new Var("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new CompStmt(
                                                new CompStmt(
                                                        new PrintStmt(new ValueExp(new StringValue("a = "))),
                                                        new CompStmt(
                                                                new PrintStmt(new Var("a")),
                                                                new PrintStmt(new ValueExp(new StringValue("\n")))
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new ValueExp(new StringValue("v = "))),
                                                        new CompStmt(
                                                                new PrintStmt(new Var("v")),
                                                                new PrintStmt(new ValueExp(new StringValue("\n")))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    static IStatement example4() {
        return new CompStmt(
                new VarDecl("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenFileStatement(new Var("varf")),
                                new CompStmt(
                                        new VarDecl("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFileStatement(new Var("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new Var("varc")),
                                                        new CompStmt(
                                                                new PrintStmt(new ValueExp(new StringValue("\n"))),
                                                                new CompStmt(
                                                                        new ReadFileStatement(new Var("varf"), "varc"),
                                                                        new CompStmt(
                                                                                new PrintStmt(new Var("varc")),
                                                                                new CompStmt(
                                                                                        new PrintStmt(new ValueExp(new StringValue("\n"))),
                                                                                        new CloseFileStatement(new Var("varf"))))))))))));
    }

    static IStatement example5() {
        return new CompStmt(
                new VarDecl("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenFileStatement(new Var("varf")),
                                new CompStmt(
                                        new VarDecl("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFileStatement(new Var("varf"), "varc"),
                                                new CompStmt(
                                                        new VarDecl("vard", new IntType()),
                                                        new CompStmt(
                                                                new ReadFileStatement(new Var("varf"), "vard"),
                                                                new CompStmt(
                                                                        new PrintStmt(new RelExp(new Var("varc"), "<", new Var("vard"))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new ValueExp(new StringValue("\n"))),
                                                                                new CloseFileStatement(new Var("varf")))))))))));

    }

    static IStatement example6() {
        return new CompStmt(
                new VarDecl("v", new RefType(new IntType())),
                new CompStmt(
                        new New("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDecl("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new New("v", new ValueExp(new IntValue(5))),
                                        new CompStmt(
                                                new PrintStmt(new Var("v")),
                                                new PrintStmt(new Var("a"))
                                        )
                                )
                        )
                )
        );
    }

    static IStatement example7() {
        return new CompStmt(
                new VarDecl("v", new RefType(new IntType())),
                new CompStmt(
                        new New("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDecl("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new New("a", new Var("v")),
                                        new CompStmt(
                                                new PrintStmt(new RH(new Var("v"))),
                                                new PrintStmt(new ArithExp(1, new RH(new Var("v")), new ValueExp(new IntValue(5))))
                                        )
                                )
                        )
                )
        );
    }

    static IStatement example8() {
        return new CompStmt(
                new VarDecl("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelExp(new Var("v"), ">", new ValueExp(new IntValue(0))),
                                        new CompStmt(
                                                new PrintStmt(new Var("v")),
                                                new CompStmt(
                                                        new PrintStmt(new ValueExp(new StringValue("\n"))),
                                                        new AssignStmt("v", new ArithExp(2, new Var("v"), new ValueExp(new IntValue(1)))))
                                        )),
                                new PrintStmt(new Var("v"))

                        )
                )
        );


    }

    static IStatement example9() {
        return new CompStmt(
                new VarDecl("v", new IntType()),
                new CompStmt(
                        new VarDecl("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new New("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new WH("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new Var("v")),
                                                                                new CompStmt(
                                                                                        new PrintStmt(new ValueExp(new StringValue("\n"))),
                                                                                        new PrintStmt(new RH(new Var("a")))
                                                                                )

                                                                        )
                                                                )

                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new Var("v")),
                                                        new CompStmt(
                                                                new PrintStmt(new ValueExp(new StringValue("\n"))),
                                                                new PrintStmt(new RH(new Var("a"))))
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static void main(String[] args) throws Exception {
        var stack1 = new MyStack<IStatement>();
        stack1.push(example1());
        example1().typecheck(new MyDictionary<>());
        ProgramState prg1 = new ProgramState(stack1, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap<>());
        IRepo repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        var stack2 = new MyStack<IStatement>();
        stack2.push(example2());
        example2().typecheck(new MyDictionary<>());
        ProgramState prg2 = new ProgramState(stack2, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap<>());
        IRepo repo2 = new Repository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        var stack3 = new MyStack<IStatement>();
        stack3.push(example3());
        example3().typecheck(new MyDictionary<>());
        ProgramState prg3 = new ProgramState(stack3, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap<>());
        IRepo repo3 = new Repository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

        var stack4 = new MyStack<IStatement>();
        stack4.push(example4());
        example4().typecheck(new MyDictionary<>());
        ProgramState prg4 = new ProgramState(stack4, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap<>());
        IRepo repo4 = new Repository(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);

        example5().typecheck(new MyDictionary<>());
        var stack5 = new MyStack<IStatement>();
        stack5.push(example5());
        ProgramState prg5 = new ProgramState(stack5, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap<>());
        IRepo repo5 = new Repository(prg5, "log5.txt");
        Controller ctr5 = new Controller(repo5);

        example6().typecheck(new MyDictionary<>());
        var stack6 = new MyStack<IStatement>();
        stack6.push(example6());
        ProgramState prg6 = new ProgramState(stack6, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap<>());
        IRepo repo6 = new Repository(prg6, "log6.txt");
        Controller ctr6 = new Controller(repo6);

        example7().typecheck(new MyDictionary<>());
        var stack7 = new MyStack<IStatement>();
        stack7.push(example7());
        ProgramState prg7 = new ProgramState(stack7, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap<>());
        IRepo repo7 = new Repository(prg7, "log7.txt");
        Controller ctr7 = new Controller(repo7);

        example8().typecheck(new MyDictionary<>());
        var stack8 = new MyStack<IStatement>();
        stack8.push(example8());
        ProgramState prg8 = new ProgramState(stack8, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap<>());
        IRepo repo8 = new Repository(prg8, "log8.txt");
        Controller ctr8 = new Controller(repo8);

        example9().typecheck(new MyDictionary<>());
        var stack9 = new MyStack<IStatement>();
        stack9.push(example9());
        ProgramState prg9 = new ProgramState(stack9, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap<>());
        IRepo repo9 = new Repository(prg9, "log9.txt");
        Controller ctr9 = new Controller(repo9);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", example1().toString(), ctr1));
        menu.addCommand(new RunExample("2", example2().toString(), ctr2));
        menu.addCommand(new RunExample("3", example3().toString(), ctr3));
        menu.addCommand(new RunExample("4", example4().toString(), ctr4));
        menu.addCommand(new RunExample("5", example5().toString(), ctr5));
        menu.addCommand(new RunExample("6", example6().toString(), ctr6));
        menu.addCommand(new RunExample("7", example7().toString(), ctr7));
        menu.addCommand(new RunExample("8", example8().toString(), ctr8));
        menu.addCommand(new RunExample("9", example9().toString(), ctr9));
        menu.show();
    }
}
