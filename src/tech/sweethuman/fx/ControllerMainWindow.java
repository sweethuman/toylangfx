package tech.sweethuman.fx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tech.sweethuman.adt.Heap;
import tech.sweethuman.adt.MyDictionary;
import tech.sweethuman.adt.MyList;
import tech.sweethuman.adt.MyStack;
import tech.sweethuman.controller.Controller;
import tech.sweethuman.controller.IController;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.ProgramState;
import tech.sweethuman.domain.statement.IStatement;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.repository.IRepo;
import tech.sweethuman.repository.Repository;

import java.io.IOException;
import java.util.*;

public class ControllerMainWindow {

    private IController controller;
    private IRepo repository;

    @FXML
    private TextField numberOfProgramStates;

    @FXML
    private TableView<TableRow> HeapTable;

    @FXML
    private ListView<IValue> out;

    @FXML
    private ListView<String> fileTable;

    @FXML
    private ListView<Integer> programStateIdentifiers;

    @FXML
    private TableView<TableRow> symbolTable;

    @FXML
    private ListView<IStatement> executionStack;

    @FXML
    private Button switchToPrograms;



    @FXML
    public void handleButtonSwitchToPrograms() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) switchToPrograms.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Programs.fxml"));

        Scene scene = new Scene(root, 900, 900);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void handleButtonRunOneStep() throws GeneralException {
        controller.executeOneStep();

        Map<Integer, IValue> heapTableTemp = new HashMap<>();
        ArrayList<IValue> outputTemp = new ArrayList<>();
        Set<String> fileTableTemp = new HashSet<>();
        ArrayList<Integer> programStateIdentifierTemp = new ArrayList<>();
        Map<String, IValue> symbolTableTemp = new HashMap<>();
        ArrayList<IStatement> executionStackTemp = new ArrayList<>();


        for (var x : repository.getProgramStatesList()) {
            heapTableTemp.putAll(x.getMemoryHeap().toMap());
            outputTemp.addAll(x.getOutput().toArrayList());
            fileTableTemp.addAll(x.getFileTable().toMap().keySet());
            programStateIdentifierTemp.add(x.getId());
            symbolTableTemp.putAll(x.getSymbolTable().toMap());
            executionStackTemp.addAll(x.getExecutionStack().toArrayList());
        }
        for (var y : controller.getFinishedPrograms()) {
//            heapTableTemp.putAll(y.getMemoryHeap().toMap());
            outputTemp.addAll(y.getOutput().toArrayList());
//            fileTableTemp.addAll(y.getFileTable().toMap().keySet());
//            programStateIdentifierTemp.add(y.getId());
//            symbolTableTemp.putAll(y.getSymbolTable().toMap());
//            executionStackTemp.addAll(y.getExecutionStack().toArrayList());
        }

        ArrayList<TableRow> row = new ArrayList<>();
        for (var zz : heapTableTemp.entrySet()) {
            row.add(new TableRow(zz.getKey().toString(), zz.getValue().toString()));
        }

        ArrayList<TableRow> row2 = new ArrayList<>();
        for (var zz : symbolTableTemp.entrySet()) {
            row2.add(new TableRow(zz.getKey().toString(), zz.getValue().toString()));
        }

        HeapTable.setItems(FXCollections.observableList(row));
        out.setItems(FXCollections.observableList(outputTemp));
        fileTable.setItems(FXCollections.observableList(new ArrayList<>(fileTableTemp)));
        programStateIdentifiers.setItems(FXCollections.observableList(programStateIdentifierTemp));
        symbolTable.setItems(FXCollections.observableList(row2));
        executionStack.setItems(FXCollections.observableList(executionStackTemp));

        numberOfProgramStates.setText(Integer.toString(repository.getProgramStatesList().size()));


    }


    public void initData(IStatement selectedItem) throws GeneralException {
        selectedItem.typecheck(new MyDictionary<>());
        var stack = new MyStack<IStatement>();
        stack.push(selectedItem);
        IProgramState program = new ProgramState(stack, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap<>());
        repository = new Repository(program, "log11.txt");
        controller = new Controller(repository);
        numberOfProgramStates.setText(Integer.toString(repository.getProgramStatesList().size()));
        executionStack.setItems(FXCollections.observableList(program.getExecutionStack().toArrayList()));
    }


    @FXML
    public void initialize() {


    }


}
