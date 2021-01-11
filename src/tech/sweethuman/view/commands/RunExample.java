package tech.sweethuman.view.commands;

import tech.sweethuman.controller.Controller;
import tech.sweethuman.domain.GeneralException;

public class RunExample extends Command {
    private Controller ctr;

    public RunExample(String key, String desc, Controller ctr) {
        super(key, desc);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.executeAllSteps();
        } catch (GeneralException e) {
            e.printStackTrace();
            System.out.println("Example " + this.key + " encountered an issue");
            System.out.println(e.toString());
        }
    }
}
