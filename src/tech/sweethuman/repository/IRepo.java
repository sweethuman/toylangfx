package tech.sweethuman.repository;

import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;

import java.util.Collection;

public interface IRepo {

    void logProgramStateExecution(IProgramState programState);

    Collection<IProgramState> getProgramStatesList();

    void setProgramStatesList(Collection<IProgramState> programStates);
}
