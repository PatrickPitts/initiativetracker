package view;

import com.sun.org.apache.xml.internal.security.utils.JDKXPathAPI;
import controller.MainViewController;
import model.COMBATANT_TYPE;
import model.Combatant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AddCombatantView extends JFrame {

    JFrame passMe = this;

    public AddCombatantView() {
        super("Add Combatant to Tracker");
        GridBagConstraints g = new GridBagConstraints();

        setLayout(new GridBagLayout());

        final JPanel combatantTypeRadioPanel = new JPanel();


        final JTextField nameField = new JTextField(24);
        final JTextField dexField = new JTextField(3);
        final JTextField extraBonusField = new JTextField(3);

        dexField.setText("0");
        extraBonusField.setText("0");

        final JButton cancel = new JButton("Cancel");
        final JButton addCombatant = new JButton("Add 'em!");

        final JRadioButton monsterRadio = new JRadioButton("Monster");
        final JRadioButton playerRadio = new JRadioButton("Player");
        final JRadioButton allyRadio = new JRadioButton("Ally/NPC");
        final JRadioButton otherRadio = new JRadioButton("Other");

        monsterRadio.setSelected(true);

        monsterRadio.setActionCommand("MONSTER");
        playerRadio.setActionCommand("PLAYER");
        allyRadio.setActionCommand("ALLY");
        otherRadio.setActionCommand("OTHER");

        final ButtonGroup combatantTypeButtonGroup = new ButtonGroup();
        combatantTypeButtonGroup.add(monsterRadio);
        combatantTypeButtonGroup.add(playerRadio);
        combatantTypeButtonGroup.add(allyRadio);
        combatantTypeButtonGroup.add(otherRadio);

        dexField.addKeyListener(new NumbersOnly(dexField));
        extraBonusField.addKeyListener(new NumbersOnly(extraBonusField));

        cancel.addActionListener(new DestroyFrameAction(this));

        addCombatant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                COMBATANT_TYPE selectedCombatantType;
                if (monsterRadio.isSelected()) {
                    selectedCombatantType = COMBATANT_TYPE.MONSTER;
                } else if (playerRadio.isSelected()) {
                    selectedCombatantType = COMBATANT_TYPE.PLAYER;
                } else if (allyRadio.isSelected()) {
                    selectedCombatantType = COMBATANT_TYPE.ALLY;
                } else {
                    selectedCombatantType = COMBATANT_TYPE.OTHER;
                }

                MainViewController.addCombatant(nameField.getText(),
                        selectedCombatantType,
                        Integer.parseInt(dexField.getText()),
                        Integer.parseInt(extraBonusField.getText()));


                cancel.doClick();
            }
        });

        g.gridy = 0;
        g.gridx = 0;
        add(new JLabel("Name: "), g);
        g.gridx++;
        add(nameField, g);
        g.gridy++;
        g.gridx = 0;
        add(new JLabel("Dexterity: "), g);
        g.gridx++;
        add(dexField, g);
        g.gridx++;
        add(new JLabel("Bonus: "), g);
        g.gridx++;
        add(extraBonusField, g);

        g.gridy++;
        g.gridx = 0;
        g.gridwidth = 2;

        combatantTypeRadioPanel.add(monsterRadio);
        combatantTypeRadioPanel.add(playerRadio);
        combatantTypeRadioPanel.add(allyRadio);
        combatantTypeRadioPanel.add(otherRadio);

        add(combatantTypeRadioPanel, g);

        g.gridy++;
        g.gridx = 0;
        g.gridwidth = 1;
        add(cancel, g);
        g.gridx++;
        add(addCombatant, g);

        pack();

        setVisible(true);


    }
}

class NumbersOnly implements KeyListener {

    JTextField field;

    public NumbersOnly(JTextField textField) {
        field = textField;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        field.setEditable(true);
        if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
            field.setEditable(true);
        } else if ((e.getKeyChar() >= ' ' && e.getKeyChar() <= '/') || (e.getKeyChar() >= ':' && e.getKeyChar() <= '~')) {
            field.setEditable(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

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