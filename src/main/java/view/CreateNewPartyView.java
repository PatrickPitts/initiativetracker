package view;

import com.sun.org.apache.xml.internal.security.utils.JDKXPathAPI;
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

    public CreateNewPartyView(Party party) {
        super("Create New Party");

        shellParty = party;
        g = new GridBagConstraints();
        setLayout(new GridBagLayout());

        for (int i = 0; i < 3; i++) {
            singleCombatantPanels.add(new NewSingleCombatantPanel());
        }
        add(rebuild());
        pack();
        setVisible(true);
    }

    //TODO: Revamp many NewSignleCombatantPanel JPanel
    private JPanel rebuild() {

        JPanel mainPanel = new JPanel();
        JPanel partyTypeButtonPanel = new JPanel();
        JPanel newCombatantsPanels = new JPanel();
        JPanel buttonPanel = new JPanel();

        newCombatantsPanels.setLayout(new GridBagLayout());

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

        buttonPanel.add(addAnotherCombatantButton);
        buttonPanel.add(cancelButton);

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
        g.gridy++;
        mainPanel.add(buttonPanel, g);
        return mainPanel;
    }
}
