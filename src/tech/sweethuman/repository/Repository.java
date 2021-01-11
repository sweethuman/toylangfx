package tech.sweethuman.repository;

import org.jetbrains.annotations.NotNull;
import tech.sweethuman.adt.MyIList;
import tech.sweethuman.adt.MyList;
import tech.sweethuman.domain.IProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class Repository implements IRepo {
    MyIList<IProgramState> programs;
    int currentProgramState;
    PrintWriter writer;

    public Repository(IProgramState program, @NotNull String filename) {
        this.programs = new MyList<>();
        this.programs.add(program);
        currentProgramState = 0;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
        } catch (IOException e) {
            System.out.println("LOGGING DISABLED! CANNOT WRITE TO FILE " + filename);
        }
    }

    @Override
    public void logProgramStateExecution(IProgramState program) {
        if (writer != null) {
            writer.println();
            writer.println(program.toString().replaceAll(".m", ""));
            writer.flush();
        }
    }

    @Override
    public Collection<IProgramState> getProgramStatesList() {
        return programs.getContent();
    }

    @Override
    public void setProgramStatesList(Collection<IProgramState> programStates) {
        this.programs.setContent(programStates);
    }
}
