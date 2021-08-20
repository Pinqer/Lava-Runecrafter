

package Lavas.Utility;

import java.awt.event.*;

import Lavas.Lavas;
import Lavas.tasks.Altar;
import Lavas.tasks.Banking;
import Lavas.tasks.MysteriousRuins;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;


public class GUI extends JFrame {

    ClientContext ctx;
    Lavas main;
    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Eric b
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel buttonBar;
    private JButton startButton;
    private JSlider energyValue;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JLabel label2;
    private JPanel panel1;
    private JLabel title;

    public GUI(ClientContext ctx, Lavas main) {
        this.ctx = ctx;
        this.main = main;
        initComponents();
    }

    private void startButtonActionPerformed(ActionEvent e) {
        // Add tasks depending on selected options
        main.taskList.addAll(Arrays.asList(new Altar(ctx, main), new Banking(ctx, main), new MysteriousRuins(ctx, main)));

        main.energyValue = (int) (energyValue.getValue());

        // We're done with gui, enable looping through tasks
        main.startScript = true;

        // Close gui window
        this.dispose();
    }

    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Eric b
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        buttonBar = new JPanel();
        startButton = new JButton();
        energyValue = new JSlider();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        label2 = new JLabel();
        panel1 = new JPanel();
        title = new JLabel();

        //======== this ========
        setTitle("Pinq's Lavas");
        setBackground(new Color(55, 58, 59));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBackground(new Color(32, 34, 97));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setBackground(new Color(32, 34, 97));

                //======== buttonBar ========
                {
                    buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                    buttonBar.setBackground(new Color(32, 34, 97));
                    buttonBar.setLayout(new GridBagLayout());
                    ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 80};
                    ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 0.0};

                    //---- startButton ----
                    startButton.setText("START");
                    startButton.setFont(new Font("Calibri", Font.BOLD, 21));
                    startButton.setVerticalAlignment(SwingConstants.BOTTOM);
                    startButton.setMargin(new Insets(0, 0, -3, 0));
                    startButton.setBackground(new Color(32, 34, 97));
                    startButton.addActionListener(e -> startButtonActionPerformed(e));
                    buttonBar.add(startButton, new GridBagConstraints(0, 0, 2, 2, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                }

                //---- energyValue ----
                energyValue.setMinorTickSpacing(2);
                energyValue.setMajorTickSpacing(10);
                energyValue.setPaintTicks(true);
                energyValue.setSnapToTicks(true);
                energyValue.setName("Energy Value:");
                energyValue.setBackground(new Color(32, 34, 97));

                //---- label1 ----
                label1.setText("Energy Value:");
                label1.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label1.setForeground(new Color(229, 229, 229));
                label1.setHorizontalAlignment(SwingConstants.CENTER);

                //======== scrollPane1 ========
                {

                    //---- textArea1 ----
                    textArea1.setText("- Works best zoomed out\n- Bank: Pure essence, Stamina potion, Earth talisman\n   Dueling ring, Binding necklace\n- Inventory: Earth rune, Lava rune (Placeholder)\n- Equip: Fire tiara\n   ");
                    textArea1.setForeground(new Color(229, 229, 229));
                    textArea1.setBackground(new Color(26, 27, 79));
                    scrollPane1.setViewportView(textArea1);
                }

                //---- label2 ----
                label2.setText("DOES NOT SUPPORT ANY POUCHES");
                label2.setForeground(new Color(139, 139, 139));
                label2.setHorizontalAlignment(SwingConstants.CENTER);

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                        contentPanelLayout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(buttonBar, GroupLayout.PREFERRED_SIZE, 374, GroupLayout.PREFERRED_SIZE))
                                .addComponent(energyValue, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                        .addGroup(contentPanelLayout.createParallelGroup()
                                                .addGroup(contentPanelLayout.createSequentialGroup()
                                                        .addGroup(contentPanelLayout.createParallelGroup()
                                                                .addGroup(contentPanelLayout.createSequentialGroup()
                                                                        .addContainerGap()
                                                                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(0, 0, Short.MAX_VALUE))
                                                .addGroup(contentPanelLayout.createSequentialGroup()
                                                        .addContainerGap()
                                                        .addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addContainerGap())
                );
                contentPanelLayout.setVerticalGroup(
                        contentPanelLayout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(energyValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                        .addComponent(buttonBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== panel1 ========
            {
                panel1.setBackground(new Color(32, 34, 97));
                panel1.setAlignmentX(9.0F);
                panel1.setPreferredSize(new Dimension(374, 50));
                panel1.setForeground(new Color(237, 237, 237));

                //---- title ----
                title.setText("Pinq's Lava Runecrafter");
                title.setFont(new Font("Segoe UI Black", Font.BOLD, 28));
                title.setForeground(new Color(232, 232, 232));
                title.setHorizontalAlignment(SwingConstants.CENTER);

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                        panel1Layout.createParallelGroup()
                                .addGroup(panel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(title, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                                        .addContainerGap())
                );
                panel1Layout.setVerticalGroup(
                        panel1Layout.createParallelGroup()
                                .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(title, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 12, Short.MAX_VALUE))
                );
            }
            dialogPane.add(panel1, BorderLayout.NORTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // End of component initialization  //GEN-END:initComponents
        setVisible(true);
    }
    // End of variables declaration  //GEN-END:variables
}
