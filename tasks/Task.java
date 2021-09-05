package Lavas.tasks;

import Lavas.Lavas;

public abstract class Task {

    protected Lavas main;

    public Task(Lavas main) {
        this.main = main;
    }

    public abstract boolean activate();

    public abstract void execute();

    public abstract String getName();

}