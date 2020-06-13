package view;

import controller.MainViewController;
import model.COMBATANT_TYPE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCombatantView extends JFrame {

    JFrame passMe = this;

    public AddCombatantView() {
        super("Add Combatant to Tracker");
        GridBagConstraints g = new GridBagConstraints();

        setLayout(new GridBagLayout());
        NewSingleCombatantPanel singleCombatantPanel = new NewSingleCombatantPanel();

        final JButton addCombatant = new JButton("Add 'em!");
        final JButton cancelButton = new JButton("Cancel");

        addCombatant.addActionListener(e -> {
            MainViewController.addCombatant(singleCombatantPanel);
            passMe.dispose();
        });

        cancelButton.addActionListener(e -> passMe.dispose());

        g.gridx = 0; g.gridy = 0; g.gridwidth=2;
        add(singleCombatantPanel, g);
        g.gridy++;g.gridwidth=1;
        add(addCombatant, g);
        g.gridx++;
        add(cancelButton, g);
        pack();
        setVisible(true);


    }
}



class DestroyFrameAction implements ActionListener {

    private JFrame destroyMe;

    public DestroyFrameAction(JFrame frame) {
        destroyMe = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainViewController.destroyFrame(destroyMe);
    }
}