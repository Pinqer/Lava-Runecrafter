package Lavas.tasks;

import Lavas.Lavas;
import Lavas.Utility.Consts;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class Banking extends Task {
    public Banking(Lavas main) {
        super(main);
    }

    @Override
    public boolean activate() {
        return (Consts.BANK_AREA.contains(Players.local()) && Bank.opened());
    }

    @Override
    public void execute() {
        if (Movement.energyLevel() < main.energyValue && Inventory.stream().filter(n -> n.name().contains("Stamina pot")).isEmpty()) {
            if (Bank.withdraw(Bank.stream().filter(n -> n.name().contains("Stamina pot")).first().name(), 1)) {
                Condition.wait(() -> !Inventory.stream().filter(n->n.name().contains("Stamina pot")).isEmpty(), 500, 5);
            }
        } else if (Movement.energyLevel() < main.energyValue && !Inventory.stream().filter(n -> n.name().contains("Stamina pot")).isEmpty()) {
            Item stamPot = Inventory.stream().filter(n -> n.name().contains("Stamina pot")).first();
            if (stamPot.interact("Drink")) {
                Condition.wait(() -> Movement.energyLevel() > main.energyValue, 500, 6);
            }
        } else if (!Inventory.stream().name("Vial").isEmpty() || !Inventory.stream().filter(n -> n.name().contains("Stamina pot")).isEmpty()) {
            if (Bank.deposit("Vial", Bank.Amount.ALL) || Bank.deposit(Inventory.stream().filter(n -> n.name().contains("Stamina pot")).first().name(), Bank.Amount.ALL)) {
                Condition.wait(() -> Inventory.stream().filter(n -> n.name().contains("Stamina pot") || n.name().contains("Vial")).isEmpty(), 500, 6);
            }
        } else if (Equipment.stream().name("Binding necklace").isEmpty() && Inventory.stream().name("Binding necklace").isEmpty()) {
            if (Bank.withdraw("Binding necklace", 1)) {
                if (Condition.wait(() -> !Inventory.stream().filter(n -> n.name().contains("Binding necklace")).isEmpty(), 500, 10)) {
                    main.shouldGrabNeck = false;
                }
            }
        } else if (main.shouldGrabRing && Inventory.stream().filter(n -> n.name().contains("dueling")).isEmpty()) {
            if (Bank.withdraw("Ring of dueling(8)", 1)) {
                if (Condition.wait(() -> !Inventory.stream().name("Ring of dueling(8)").isEmpty(), 500, 10)) {
                    main.shouldGrabRing = false;
                }
            }
        } else if (!Inventory.stream().name("Binding necklace").isEmpty()) {
            Item bindingNecklace = Inventory.stream().name("Binding necklace").first();
            if (bindingNecklace.interact("Wear")) {
                Condition.wait(() -> Inventory.stream().name("Binding necklace").isEmpty(), 500, 5);
            }
        } else if (!Inventory.stream().filter(n->n.name().contains("dueling")).isEmpty()) {
            Item ring = Inventory.stream().name("Ring of dueling(8)").first();
            if (ring.interact("Wear")) {
                Condition.wait(() -> Inventory.stream().name("Ring of dueling(8)").isEmpty(), 500, 5);
            }
        } else if (Inventory.stream().name("Earth talisman").isEmpty()) {
            if (Bank.withdraw("Earth talisman", 1)) {
                Condition.wait(() -> !Inventory.stream().name("Earth talisman").isEmpty(), 500, 5);
            }
        } else if (Inventory.stream().name("Pure essence").isEmpty()) {
            if (Bank.withdraw("Pure essence", Bank.Amount.ALL)) {
                Condition.wait(() -> !Inventory.stream().name("Pure essence").isEmpty(), 500, 5);
            }
        } else {
            if (Bank.close()) {
                Condition.wait(() -> !Bank.opened(), 500, 5);
            }
        }
    }

    public String getName() {
        return "Banking";
    }
}