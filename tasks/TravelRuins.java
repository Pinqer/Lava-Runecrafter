package Lavas.tasks;

import Lavas.Lavas;
import Lavas.Utility.Consts;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;

public class TravelRuins extends Task {
    public TravelRuins(Lavas main) {
        super(main);
    }

    @Override
    public boolean activate() {
        return (Consts.RUINS_AREA.contains(Players.local()));
    }

    @Override
    public void execute() {

        if (!Game.tab(Game.Tab.INVENTORY)) {
            return;
        }

        GameObject ruins = Objects.stream().name("Mysterious ruins").first();
        if (ruins.valid()) {
            if (ruins.inViewport()) {
                if (ruins.interact("Enter")) {
                    Condition.wait(() -> !Consts.RUINS_AREA.contains(Players.local()), 500, 17);
                }
            } else {
                if (Players.local().tile().distanceTo(ruins) > 7) {
                    Tile tile = ruins.tile().derive(Random.nextInt(-6, -5), Random.nextInt(-2, 1));
                    Movement.step(tile);
                    Condition.wait(() -> ruins.inViewport(), 500, 10);
                }
                else {
                    Camera.turnTo(ruins, Random.nextInt(-10, 10));
                    Condition.wait(() -> ruins.inViewport(), 500, 10);
                }
            }
        } else {
            Movement.step(Consts.RUINS_TILE);
            Condition.sleep(Random.nextInt(4000, 6000));
        }
    }

    public String getName() {
        return "WalkRuins";
    }
}