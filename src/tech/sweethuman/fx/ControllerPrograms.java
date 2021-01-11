package tech.sweethuman.fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.expression.*;
import tech.sweethuman.domain.statement.*;
import tech.sweethuman.domain.value.BoolValue;
import tech.sweethuman.domain.value.IntValue;
import tech.sweethuman.domain.value.StringValue;
import tech.sweethuman.domain.vartype.BoolType;
import tech.sweethuman.domain.vartype.IntType;
import tech.sweethuman.domain.vartype.RefType;
import tech.sweethuman.domain.vartype.StringType;

import java.io.IOException;

public class ControllerPrograms {


    @FXML
    private ListView<IStatement> listView;

    @FXML
    private Button switchToMainWindow;


    @FXML
    public void initialize() {

        listView.setItems(getCommands());

        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listView.getSelectionModel().selectIndices(0);

    }

    @FXML
    public void handleButtonSwitchToMainWindow(javafx.event.ActionEvent event) throws IOException, GeneralException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "Debug.fxml"
                )
        );
        Stage stage;
        Parent root;
        stage=(Stage) switchToMainWindow.getScene().getWindow();
        root = loader.load();

        ControllerMainWindow controller = loader.getController();
        controller.initData(listView.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(root, 900, 900);
        stage.setScene(scene);
        stage.show();

    }

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

    private ObservableList<IStatement> getCommands() {
        ObservableList<IStatement> commands = FXCollections.observableArrayList(example1(), example2(), example3(), example4(), example5(), example6(), example7(), example8(), example9());
        return commands;
    }



}
