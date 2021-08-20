package Lavas.tasks;

import Lavas.Lavas;
import Lavas.Utility.Consts;
import org.powerbot.script.ClientAccessor;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.GameObject;

public class MysteriousRuins extends Task<ClientContext> {
    public MysteriousRuins(ClientContext ctx, Lavas main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {
        return (Consts.RUINS_AREA.contains(ctx.players.local()));
    }

    @Override
    public void execute() {

        if (!ctx.game.tab(Game.Tab.INVENTORY)) {
            return;
        }

        GameObject ruins = ctx.objects.toStream().name("Mysterious ruins").first();
        if (ruins.valid()) {
            if (ruins.inViewport()) {
                if (ruins.interact("Enter")) {
                    Condition.wait(() -> !Consts.RUINS_AREA.contains(ctx.players.local()), 500, 17);
                }
            } else {
                if (ctx.players.local().tile().distanceTo(ruins) > 7) {
                    Tile tile = ruins.tile().derive(Random.nextInt(-6, -5), Random.nextInt(-2, 1));
                    ctx.movement.step(tile);
                    Condition.wait(() -> ruins.inViewport(), 500, 10);
                }
                else {
                    ctx.camera.turnTo(ruins, Random.nextInt(-10, 10));
                    Condition.wait(() -> ruins.inViewport(), 500, 10);
                }
            }
        } else {
            ctx.movement.walkTo(Consts.RUINS_TILE);
            Condition.sleep(Random.nextInt(4000, 6000));
        }
    }

    public String getName() {
        return "WalkRuins";
    }
}