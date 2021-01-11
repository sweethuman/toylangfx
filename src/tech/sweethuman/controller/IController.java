package tech.sweethuman.controller;

import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;

import java.util.Collection;

public interface IController {

    void executeAllSteps() throws GeneralException;

    void executeOneStep() throws GeneralException;

    Collection<IProgramState> getFinishedPrograms();
}
