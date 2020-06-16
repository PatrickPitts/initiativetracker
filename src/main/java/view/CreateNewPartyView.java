package view;

import controller.MainViewController;
import model.PARTY_TYPE;
import model.Party;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreateNewPartyView extends JFrame {

    ArrayList<NewSingleCombatantPanel> singleCombatantPanels = new ArrayList<>();
    JFrame thisFrame = this;
    Party shellParty;
    GridBagConstraints g;
    JPanel mainPanel;
    String partyName = "";

    public CreateNewPartyView(Party party) {
        super("Create New Party");

        shellParty = party;
        g = new GridBagConstraints();
        setLayout(new GridBagLayout());

        for (int i = 0; i < 3; i++) {
            singleCombatantPanels.add(new NewSingleCombatantPanel());
        }
        mainPanel = allPanels();
        add(allPanels());
        pack();
        setVisible(true);
    }

    private void rebuild(){
        getContentPane().removeAll();
        add(allPanels());
        pack();
    }

    //TODO: Revamp many NewSingleCombatantPanel JPanel
    private JPanel allPanels() {

        JPanel mainPanel = new JPanel();
        JPanel partyTypeButtonPanel = new JPanel();
        JPanel newCombatantsPanels = new JPanel();
        JPanel buttonPanel = new JPanel();

        JTextField partyNameField = new JTextField(32);

        partyNameField.setText(partyName);

        newCombatantsPanels.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridBagLayout());
        mainPanel.setLayout(new GridBagLayout());

        g.gridwidth=PARTY_TYPE.values().length;
        partyTypeButtonPanel.add(partyNameField, g);

        g.gridwidth=1; g.gridy++;
        ButtonGroup partyTypeButtonGroup = new ButtonGroup();
        for (PARTY_TYPE pt : PARTY_TYPE.values()) {
            String ptName = pt.toString();

            ptName = ptName.substring(0, 1) + ptName.substring(1).toLowerCase();
            JRadioButton radioButton = new JRadioButton(ptName);
            radioButton.setActionCommand(pt.toString());
            if (shellParty.getPartyType().equals(pt)) {
                radioButton.setSelected(true);
            }
            partyTypeButtonGroup.add(radioButton);
            partyTypeButtonPanel.add(radioButton);
        }

        JButton addAnotherCombatantButton = new JButton("+ Combatant");
        JButton cancelButton = new JButton("Cancel");
        JButton savePartyButton = new JButton("Save This Party");

        addAnotherCombatantButton.addActionListener(e -> {
            singleCombatantPanels.add(new NewSingleCombatantPanel());
            partyName = partyNameField.getText();
            rebuild();
        });
        cancelButton.addActionListener(e -> thisFrame.dispose());
        savePartyButton.addActionListener(e -> {
            partyName = partyNameField.getText();
            String partyType = partyTypeButtonGroup.getSelection().getActionCommand();
            MainViewController.saveNewParty(singleCombatantPanels, partyName, partyType);
            thisFrame.dispose();
        });

        g.gridx=0; g.gridy=0;
        buttonPanel.add(addAnotherCombatantButton, g);
        g.gridx++;
        buttonPanel.add(cancelButton, g);
        g.gridx=0; g.gridy=1;
        buttonPanel.add(savePartyButton, g);

        for (NewSingleCombatantPanel n : singleCombatantPanels) {
            g.gridx = 0;
            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener(e -> {
                singleCombatantPanels.remove(n);
                rebuild();
            });
            newCombatantsPanels.add(n, g);
            g.gridx++;
            newCombatantsPanels.add(removeButton, g);
            g.gridy++;
        }

        g.gridx = 0;
        g.gridy = 0;
        mainPanel.add(partyTypeButtonPanel, g);
        g.gridy++;
        mainPanel.add(newCombatantsPanels, g);
        g.gridx++;
        mainPanel.add(buttonPanel, g);
        return mainPanel;
    }
}
