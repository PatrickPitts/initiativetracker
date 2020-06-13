package view;

import controller.MainViewController;
import model.COMBATANT_TYPE;
import model.Combatant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class NewSingleCombatantPanel extends JPanel {

    public Map<String, Component> panelComponents = new HashMap<>();

    public Map<String, Component> getPanelComponents() {
        return panelComponents;
    }

    public NewSingleCombatantPanel(Combatant... combatants) {

        GridBagConstraints g = new GridBagConstraints();

        setLayout(new GridBagLayout());

        final JPanel combatantTypeRadioPanel = new JPanel();


        final JTextField nameField = new JTextField(24);
        final JTextField dexField = new JTextField(3);
        final JTextField extraBonusField = new JTextField(3);
        final JTextField maxHPField = new JTextField(5);
        final JTextField acField = new JTextField(3);


            dexField.setText("10");
            extraBonusField.setText("0");
            acField.setText("10");
            maxHPField.setText("0");




        final ButtonGroup combatantTypeButtonGroup = new ButtonGroup();

        for (COMBATANT_TYPE ct : COMBATANT_TYPE.values()) {

            String ctName = ct.toString();

            ctName = ctName.substring(0, 1) + ctName.substring(1).toLowerCase();

            JRadioButton radioButton = new JRadioButton(ctName);
            radioButton.setActionCommand(ct.toString());
            combatantTypeButtonGroup.add(radioButton);
            combatantTypeRadioPanel.add(radioButton);
        }

        ((JRadioButton) combatantTypeRadioPanel.getComponents()[0]).setSelected(true);

        dexField.addKeyListener(new NumbersOnlyKeyListener(dexField));
        extraBonusField.addKeyListener(new NumbersOnlyKeyListener(extraBonusField));
        maxHPField.addKeyListener(new NumbersOnlyKeyListener(maxHPField));
        acField.addKeyListener(new NumbersOnlyKeyListener(acField));

        g.gridy = 0;
        g.gridx = 0;
        add(new JLabel("Name: "), g);
        g.gridx++;
        g.gridwidth = 3;
        add(nameField, g);
        panelComponents.put("nameField", nameField);
        g.gridy++;
        g.gridwidth = 1;
        g.gridx = 0;
        add(new JLabel("Dexterity: "), g);
        g.gridx++;
        add(dexField, g);
        panelComponents.put("dexField", dexField);
        g.gridx++;
        add(new JLabel("Bonus: "), g);
        g.gridx++;
        add(extraBonusField, g);
        panelComponents.put("extraBonusField", extraBonusField);
        g.gridy++;
        g.gridx = 0;
        add(new JLabel("Armor Class: "), g);
        g.gridx++;
        add(acField, g);
        panelComponents.put("acField", acField);
        g.gridx++;
        add(new JLabel("Hit Points: "), g);
        g.gridx++;
        add(maxHPField, g);
        panelComponents.put("maxHPField", maxHPField);
        g.gridy++;
        g.gridx = 0;
        g.gridwidth = 2;

        add(combatantTypeRadioPanel, g);
        panelComponents.put("combatantTypeRadioPanel", combatantTypeRadioPanel);

        setVisible(true);


    }
}
