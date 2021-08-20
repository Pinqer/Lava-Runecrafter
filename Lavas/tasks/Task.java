package Lavas.tasks;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import Lavas.Lavas;

public abstract class Task<C extends ClientContext> extends ClientAccessor<C> {

    protected Lavas main;

    public Task(C ctx, Lavas main) {
        super(ctx);
        this.main = main;
    }

    public abstract boolean activate();

    public abstract void execute();

    public abstract String getName();

}