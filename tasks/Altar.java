package Lavas.tasks;

import Lavas.Lavas;
import Lavas.Utility.Consts;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;

public class Altar extends Task{
    public Altar(Lavas main) {
        super(main);
    }

    @Override
    public boolean activate() {
        return (Consts.ALTAR_AREA.contains(Players.local()));
    }

    @Override
    public void execute() {
        if (Inventory.stream().name("Pure essence").isEmpty()) {
            if (Game.tab(Game.Tab.EQUIPMENT)) {
                Item ring = Equipment.itemAt(Equipment.Slot.RING);
                if (ring.interact("Castle wars")) {
                    Condition.wait(() -> !Consts.ALTAR_AREA.contains(Players.local()), 500, 12);
                }
            }
        }

        if (!Inventory.stream().name("Pure essence").isEmpty()) {
            GameObject altar = Objects.stream().name("Altar").first();
            if (Inventory.selectedItem().id() == -1) {
                if (altar.inViewport()) {
                    Item earthRune = Inventory.stream().name("Earth rune").first();
                    if (earthRune.interact("Use")) {
                        Condition.wait(() -> !(Inventory.selectedItem().id() == -1), 500, 5);
                    }
                } else {
                    if (Players.local().tile().distanceTo(altar) > 7) {
                        Tile tile = altar.tile().derive(Random.nextInt(-3, 3), Random.nextInt(-3, 3));
                        Movement.step(tile);
                        Condition.wait(() -> altar.inViewport(), 500, 10);
                    } else {
                        Camera.turnTo(altar, Random.nextInt(-10, 10));
                        Condition.wait(() -> altar.inViewport(), 500, 10);
                    }
                }
            } else {
                altar.interact("Use");
                Condition.wait(() -> Inventory.stream().name("Pure essence").isEmpty(), 500, 15);
            }
        }
    }

    public String getName() {
        return "handleAltar";
    }
}