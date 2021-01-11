package tech.sweethuman.controller;

import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.RunTimeException;
import tech.sweethuman.domain.value.RefValue;
import tech.sweethuman.domain.vartype.RefType;
import tech.sweethuman.repository.IRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Controller implements IController {
    IRepo repo;
    ExecutorService executor;
    public ArrayList<IProgramState> completedPrograms;

    public Controller(IRepo repo) {
        this.repo = repo;
        this.completedPrograms = new ArrayList<>();
    }

    void oneStepForAllPrograms(Collection<IProgramState> programStatesList) throws RunTimeException {
        programStatesList.forEach(program -> repo.logProgramStateExecution(program));
        Collection<Callable<IProgramState>> callList = programStatesList.stream()
                .map((IProgramState program) -> (Callable<IProgramState>) (program::executeOneStep))
                .collect(Collectors.toList());

        Collection<IProgramState> newProgramStatesList;
        try {
            newProgramStatesList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception exception) {
                            System.out.println("Couldn't get one of the program states!");
                            System.out.println(exception.getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException exception) {
            throw new RunTimeException(exception.getMessage());
        }

        programStatesList.addAll(newProgramStatesList);
        programStatesList.forEach(program -> repo.logProgramStateExecution(program));
        repo.setProgramStatesList(programStatesList);
    }

    public void executeAllSteps() throws GeneralException {
        executor = Executors.newFixedThreadPool(2);

        Collection<IProgramState> programStatesList = removeCompletedPrograms(repo.getProgramStatesList());

        while (programStatesList.size() > 0) {

            garbageCollector();
            oneStepForAllPrograms(programStatesList);
            programStatesList = removeCompletedPrograms(repo.getProgramStatesList());
        }
        executor.shutdown();
        repo.setProgramStatesList(programStatesList);
    }

    @Override
    public void executeOneStep() throws GeneralException {
        executor = Executors.newFixedThreadPool(2);

        Collection<IProgramState> programStatesList = removeCompletedPrograms(repo.getProgramStatesList());

        if (programStatesList.size() > 0) {

            garbageCollector();
            oneStepForAllPrograms(programStatesList);
            programStatesList = removeCompletedPrograms(repo.getProgramStatesList());
        }
        executor.shutdown();
        completedPrograms.addAll(getCompletedPrograms(repo.getProgramStatesList()));
        repo.setProgramStatesList(programStatesList);
    }

    private void unsafeGarbageCollector() {
        for (var state : repo.getProgramStatesList()) {
            var heap = state.getMemoryHeap().toMap();
            var symbolTable = state.getSymbolTable().toMap().values().stream()
                    .filter(value -> value.getType() instanceof RefType)
                    .map(value -> ((RefValue) value).getAddress())
                    .collect(Collectors.toList());
            var values = heap.keySet().stream()
                    .filter(iValue -> !(symbolTable.contains(iValue))).collect(Collectors.toList());
            for (var x : values) {
                state.getMemoryHeap().remove(x);
            }
        }

    }

    private void garbageCollector() {
        for (var state : repo.getProgramStatesList()) {
            var heap = state.getMemoryHeap().toMap();
            var symbolTable = state.getSymbolTable().toMap().values().stream()
                    .filter(value -> value.getType() instanceof RefType)
                    .map(value -> ((RefValue) value).getAddress())
                    .collect(Collectors.toList());
            var memHeap = state.getMemoryHeap().toMap().values().stream()
                    .filter(value -> value instanceof RefValue)
                    .map(value -> ((RefValue) value).getAddress())
                    .collect(Collectors.toList());
            var values = heap.keySet().stream()
                    .filter(iValue -> !(symbolTable.contains(iValue) || memHeap.contains(iValue))).collect(Collectors.toList());
            for (var x : values) {
                state.getMemoryHeap().remove(x);
            }
        }
    }

    public Collection<IProgramState> getCompletedPrograms(Collection<IProgramState> inProgramList) {
        return inProgramList.stream()
                .filter(Predicate.not(IProgramState::isNotCompleted))
                .collect(Collectors.toList());
    }

    public Collection<IProgramState> removeCompletedPrograms(Collection<IProgramState> inProgramList) {
        inProgramList.stream()
                .filter(Predicate.not(IProgramState::isNotCompleted)).forEach(program -> {
            var list = program.getOutput().toArrayList();
            for (var x : list) {
                System.out.print(x);
            }
            System.out.println();
        });
        return inProgramList.stream()
                .filter(IProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<IProgramState> getFinishedPrograms() {
        return completedPrograms;
    }
}
