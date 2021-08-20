package Lavas;

import Lavas.Utility.GUI;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import Lavas.tasks.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@Script.Manifest(
        name = "Lavas Runecrafter",
        description = "Crafts lava runes without pouches",
        version = "0.0.1"
)
public class Lavas extends PollingScript<ClientContext> implements PaintListener {

    private final Font helveticaFont = new Font("Helvetica", 0, 12);
    private final Font helveticaFontBig = new Font("Helvetica", 0, 13);
    public String currentTask = "Starting...";
    public int chargesLeft = 8;
    public int energyValue;
    public long sRunecraftXp;
    public boolean shouldGrabRing;
    public boolean startScript = true;
    public List<Task> taskList = new ArrayList<Task>();

    @Override
    public void start() {
        // Start the GUI
        SwingUtilities.invokeLater((() -> {
            GUI gui = new GUI(ctx, this);
        }));

        // Initialize starting xp
        sRunecraftXp = ctx.skills.experience(Constants.SKILLS_RUNECRAFTING);
    }

    @Override
    public void stop() {

    }

    @Override
    public void poll() {
        if (startScript) {
            for (Task t : taskList) {
                if (t.activate()) {
                    currentTask = t.getName();
                    t.execute();
                    break;
                }
            }
        }
    }

    @Override
    public void repaint(Graphics g) {
        long cRunecraftXp = ctx.skills.experience(Constants.SKILLS_RUNECRAFTING);

        // Paint window
        g.setColor(new Color(0, 0, 0, 255));
        g.drawRect(240, 352, 250, 100);
        g.setColor(new Color(33, 33, 33, 230));
        g.fillRect(240, 352, 250, 100);

        // Info text
        g.setColor(Color.ORANGE);
        g.setFont(helveticaFontBig);
        g.drawString("Pinq's Lavas", 302, 372);
        g.setColor(Color.WHITE);
        g.setFont(helveticaFont);
        g.drawString("Runecraft exp. gained: " + (cRunecraftXp - sRunecraftXp) + " (" + getPerHour(cRunecraftXp, this.getRuntime()) + "/pH)", 250, 397);
        g.drawString("Time running: " + formatTime((int) this.getRuntime() / 1000), 250, 417);
        g.drawString("Current task: " + currentTask, 250, 437);
    }

    private long getPerHour(long in, long time) {
        return (int) ((in) * 3600000D / time);
    }

    private String formatTime(long time) {
        return String.format("%d:%02d:%02d", time / 3600, (time % 3600) / 60, (time % 60));
    }
}
