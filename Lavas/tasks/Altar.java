package Lavas.tasks;

import Lavas.Lavas;
import Lavas.Utility.Consts;
import org.powerbot.script.ClientAccessor;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

public class Altar extends Task<ClientContext> {
    public Altar(ClientContext ctx, Lavas main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {
        return (Consts.ALTAR_AREA.contains(ctx.players.local()));
    }

    @Override
    public void execute() {
        if (ctx.inventory.toStream().name("Pure essence").isEmpty()) {
            if (ctx.game.tab(Game.Tab.EQUIPMENT)) {
                Item ring = ctx.equipment.itemAt(Equipment.Slot.RING);
                if (ring.interact("Castle wars")) {
                    if (Condition.wait(() -> !Consts.ALTAR_AREA.contains(ctx.players.local()), 500, 12)) {
                        main.chargesLeft -= 1;
                    }
                }
            }
        }

        if (!ctx.inventory.toStream().name("Pure essence").isEmpty()) {
            if (ctx.inventory.selectedItem().id() == -1) {
                GameObject altar = ctx.objects.toStream().name("Altar").first();
                if (altar.inViewport()) {
                    Item earthRune = ctx.inventory.toStream().name("Earth rune").first();
                    if (earthRune.interact("Use")) {
                        Condition.wait(() -> !(ctx.inventory.selectedItem().id() == -1), 500, 5);
                    }
                } else {
                    if (ctx.players.local().tile().distanceTo(altar) > 7) {
                        Tile tile = altar.tile().derive(Random.nextInt(-3, 3), Random.nextInt(-3, 3));
                        ctx.movement.step(altar);
                        Condition.wait(() -> altar.inViewport(), 500, 10);
                    } else {
                        ctx.camera.turnTo(altar, Random.nextInt(-10, 10));
                        Condition.wait(() -> altar.inViewport(), 500, 10);
                    }
                }
            } else {
                if (altar.interact("Use")) {
                    Condition.wait(() -> ctx.inventory.toStream().name("Pure essence").isEmpty(), 500, 15);
                }
            }
        }
    }

    public String getName() {
        return "handleAltar";
    }
}