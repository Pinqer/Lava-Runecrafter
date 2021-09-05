package Lavas.tasks;

import Lavas.Lavas;
import Lavas.Utility.Consts;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;

public class CastleWars extends Task {

    public CastleWars(Lavas main) {
        super(main);
    }

    @Override
    public boolean activate() {
        return (Consts.BANK_AREA.contains(Players.local()) && !Bank.opened());
    }

    @Override
    public void execute() {
        if (!Inventory.stream().name("Pure essence").isEmpty() && !Inventory.stream().name("Earth talisman").isEmpty() && Equipment.itemAt(Equipment.Slot.NECK).valid()) {
            if (Game.tab(Game.Tab.EQUIPMENT)) {
                Item ring = Equipment.itemAt(Equipment.Slot.RING);
                if (ring.interact("Duel Arena")) {
                    Condition.wait(() -> !Consts.BANK_AREA.contains(Players.local()), 500, 12);
                }
            }
        } else {
            if (Bank.inViewport()) {
                if (!Bank.opened() && Game.tab(Game.Tab.EQUIPMENT)) {
                    main.shouldGrabRing = Equipment.stream().filter(n->n.name().contains("dueling")).isEmpty();
                    Condition.sleep(Random.nextInt(800,1200));
                }
                if (Bank.open()) {
                    Condition.wait(() -> Bank.opened(), 500, 15);
                }
            } else {
                if (Players.local().tile().distanceTo(Bank.nearest()) > 7) {
                    Tile tile = Bank.nearest().tile().derive(Random.nextInt(-3, -1), Random.nextInt(1, 3));
                    Movement.step(tile);
                    Condition.wait(() -> Bank.inViewport(), 500, 10);
                } else {
                    Camera.turnTo(Bank.nearest(), Random.nextInt(-10, 10));
                    Condition.wait(() -> Bank.inViewport(), 500, 10);
                }
            }
        }
    }

    public String getName() {
        return "Castle wars";
    }
}