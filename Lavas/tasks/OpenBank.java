package Lavas.tasks;

import Lavas.Lavas;
import Lavas.Utility.Consts;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

public class OpenBank extends Task<ClientContext> {
    public OpenBank(ClientContext ctx, Lavas main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {

        return (Consts.BANK_AREA.contains(ctx.players.local()) && !ctx.bank.opened());
    }

    @Override
    public void execute() {
        if (!ctx.inventory.toStream().name("Pure essence").isEmpty() && !ctx.inventory.toStream().name("Earth talisman").isEmpty() && ctx.equipment.itemAt(Equipment.Slot.NECK).valid() && main.chargesLeft != 0) {
            if (ctx.game.tab(Game.Tab.EQUIPMENT)) {
                Item ring = ctx.equipment.itemAt(Equipment.Slot.RING);
                if (ring.interact("Duel Arena")) {
                    if (Condition.wait(() -> !Consts.BANK_AREA.contains(ctx.players.local()), 500, 12)) {
                        main.chargesLeft -= 1;
                    }
                }
            }
        } else {
            if (ctx.game.tab(Game.Tab.EQUIPMENT)) {
                main.shouldGrabRing = !ctx.equipment.itemAt(Equipment.Slot.RING).valid();
            }
            if (ctx.bank.inViewport()) {
                if (ctx.bank.open()) {
                    Condition.wait(() -> ctx.bank.opened(), 500, 15);
                }
            } else {
                if (ctx.players.local().tile().distanceTo(ctx.bank.nearest()) > 7) {
                    Tile tile = ctx.bank.nearest().tile().derive(Random.nextInt(-3, -1), Random.nextInt(1, 3));
                    ctx.movement.step(tile);
                    Condition.wait(() -> altar.inViewport(), 500, 10);
                } else {
                    ctx.camera.turnTo(ctx.bank.nearest(), Random.nextInt(-10, 10));
                    Condition.wait(() -> ctx.bank.inViewport(), 500, 10);
                }
            }
        }
    }

    public String getName() {
        return "Banking";
    }
}