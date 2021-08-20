package Lavas.tasks;

import Lavas.Lavas;
import Lavas.Utility.Consts;
import org.powerbot.script.ClientAccessor;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

public class Banking extends Task<ClientContext> {
    public Banking(ClientContext ctx, Lavas main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {
        return (Consts.BANK_AREA.contains(ctx.players.local()) && ctx.bank.opened());
    }

    @Override
    public void execute() {
        if (ctx.movement.energyLevel() < main.energyValue && ctx.inventory.toStream().filter(n -> n.name().contains("Stamina pot")).isEmpty()) {
            if (ctx.bank.withdraw(ctx.bank.toStream().filter(n -> n.name().contains("Stamina pot")).first().name(), 1)) {
                Condition.wait(() -> !ctx.inventory.toStream().name("Stamina pot").isEmpty(), 500, 5);
            }
        } else if (ctx.movement.energyLevel() < main.energyValue && !ctx.inventory.toStream().filter(n -> n.name().contains("Stamina pot")).isEmpty()) {
            Item stamPot = ctx.inventory.toStream().filter(n -> n.name().contains("Stamina pot")).first();
            if (stamPot.interact("Drink")) {
                Condition.wait(() -> ctx.movement.energyLevel() > main.energyValue, 500, 6);
            }
        } else if (!ctx.inventory.toStream().name("Vial").isEmpty() || !ctx.inventory.toStream().filter(n -> n.name().contains("Stamina pot")).isEmpty()) {
            if (ctx.bank.deposit("Vial", Bank.Amount.ALL) || ctx.bank.deposit(ctx.inventory.toStream().filter(n -> n.name().contains("Stamina pot")).first().name(), Bank.Amount.ALL)) {
                Condition.wait(() -> ctx.inventory.toStream().filter(n -> n.name().contains("Stamina pot") || n.name().contains("Vial")).isEmpty(), 500, 6);
            }
        } else if (!ctx.equipment.itemAt(Equipment.Slot.NECK).valid() && ctx.inventory.toStream().name("Binding necklace").isEmpty()) {
            if (ctx.bank.withdraw("Binding necklace", 1)) {
                Condition.wait(() -> ctx.inventory.toStream().filter(n -> n.name().contains("Stamina pot")).isEmpty(), 500, 5);
            }
        } else if (main.shouldGrabRing && ctx.inventory.toStream().filter(n -> n.name().contains("dueling")).isEmpty()) {
            if (ctx.bank.withdraw("Ring of dueling(8)", 1)) {
                if (Condition.wait(() -> !ctx.inventory.toStream().name("Ring of dueling(8)").isEmpty(), 500, 10)) {
                    main.shouldGrabRing = false;
                }
            }
        } else if (!ctx.inventory.toStream().name("Binding necklace").isEmpty()) {
            Item bindingNecklace = ctx.inventory.toStream().name("Binding necklace").first();
            if (bindingNecklace.interact("Wear")) {
                Condition.wait(() -> ctx.equipment.itemAt(Equipment.Slot.NECK).valid(), 500, 5);
            }
        } else if (!ctx.inventory.toStream().name("Ring of dueling(8)").isEmpty()) {
            Item ring = ctx.inventory.toStream().name("Ring of dueling(8)").first();
            if (ring.interact("Wear")) {
                Condition.wait(() -> ctx.inventory.toStream().name("Ring of dueling(8)").isEmpty(), 500, 5);
                main.chargesLeft = 8;
            }
        } else if (ctx.inventory.toStream().name("Earth talisman").isEmpty()) {
            if (ctx.bank.withdraw("Earth talisman", 1)) {
                Condition.wait(() -> !ctx.inventory.toStream().name("Earth talisman").isEmpty(), 500, 5);
            }
        } else if (ctx.inventory.toStream().name("Pure essence").isEmpty()) {
            if (ctx.bank.withdraw("Pure essence", Bank.Amount.ALL)) {
                Condition.wait(() -> !ctx.inventory.toStream().name("Pure essence").isEmpty(), 500, 5);
            }
        } else {
            if (ctx.bank.close()) {
                Condition.wait(() -> !ctx.bank.opened(), 500, 5);
            }
        }
    }

    public String getName() {
        return "Banking";
    }
}