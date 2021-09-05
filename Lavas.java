package Lavas;

import com.google.common.eventbus.Subscribe;
import org.powbot.api.event.RenderEvent;
import org.powbot.api.rt4.Constants;
import org.powbot.api.rt4.Skills;
import org.powbot.api.script.AbstractScript;
import org.powbot.api.script.OptionType;
import org.powbot.api.script.ScriptConfiguration;
import org.powbot.api.script.ScriptManifest;
import Lavas.tasks.*;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

import java.util.ArrayList;
import java.util.List;


@ScriptManifest(
        name = "Pinq's Lavas",
        description = "Crafts lava runes at castle wars with ring of dueling, earth runes, binding necklace, stamina potions, earth talismans and pure essence",
        version = "0.0.1"
)

@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name = "Energy value",
                        description = "Drink stamina potion at energy level",
                        optionType = OptionType.INTEGER
                )
        }
)

public class Lavas extends AbstractScript {

    public String currentTask = "Starting...";
    public int energyValue;
    public long sRunecraftXp;
    public boolean shouldGrabRing;
    public boolean shouldGrabNeck;
    public boolean startScript = true;
    public List<Task> taskList = new ArrayList<Task>();


    @Override
    public void onStart() {
        energyValue = getOption("Energy value");

        taskList.add(new CastleWars(this));
        taskList.add(new TravelRuins(this));
        taskList.add(new Banking(this));
        taskList.add(new Altar(this));
        sRunecraftXp = Skills.experience(Constants.SKILLS_RUNECRAFTING);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void poll() {
            for (Task t : taskList) {
                if (t.activate()) {
                    currentTask = t.getName();
                    t.execute();
                    break;
                }
            }
    }


    @Subscribe
    public void onRender(RenderEvent e) {
        org.powbot.mobile.drawing.Graphics g = e.getGraphics();
        long cRunecraftXp = Skills.experience(Constants.SKILLS_RUNECRAFTING);

        g.setColor(org.powbot.api.Color.getWHITE());
        g.drawRect(40, 152, 480, 175);

        g.setColor(org.powbot.api.Color.getORANGE());
        g.setTextSize(16);
        g.drawString("Pinq's Lavas", 180, 200);

        g.setTextSize(14);
        g.setColor(org.powbot.api.Color.getWHITE());
        g.drawString("Runecraft exp. gained: " + (cRunecraftXp-sRunecraftXp) + " (" + getPerHour(cRunecraftXp-sRunecraftXp, ScriptManager.INSTANCE.getRuntime(true)) + "/pH)", 50, 240);
        g.drawString("Time running: " + formatTime(ScriptManager.INSTANCE.getRuntime(true)  / 1000), 50, 270);
        g.drawString("Current task: " + currentTask, 50, 300);
    }

    private long getPerHour(long in, long time) {
        return (int) ((in) * 3600000D / time);
    }

    private String formatTime(long time) {
        return String.format("%d:%02d:%02d", time / 3600, (time % 3600) / 60, (time % 60));
    }

    public static void main(String[] args) {

        new ScriptUploader().uploadAndStart("Pinq's Lavas", "bob", "127.0.0.1:5563", false, false);
    }
}
