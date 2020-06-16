package view;

import controller.MainViewController;
import model.Combatant;
import model.LIST_CLASS_OPTION;
import model.PARTY_TYPE;
import model.Party;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class ChoosePartyView extends JFrame {

    JFrame passMe = this;

    GridBagConstraints g = new GridBagConstraints();

    public ChoosePartyView(Map<Integer, Party> allPartiesMap, LIST_CLASS_OPTION option) {
        super(getTitle(option));
        setLayout(new GridBagLayout());
        DefaultListModel<Party> partyListModel = new DefaultListModel<>();
        for(Party p : allPartiesMap.values()){
            if(option.toString().contains("MONSTER") && p.getPartyType().equals(PARTY_TYPE.MONSTER)){
                partyListModel.addElement(p);
            } else if (option.toString().contains("PLAYERS") && p.getPartyType().equals(PARTY_TYPE.PLAYERS)){
                partyListModel.addElement(p);
            }
        }

        JList partyNames = new JList(partyListModel);

        JPanel listPanel = new JPanel();
        JPanel partyDisplayPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        listPanel.setLayout(new GridBagLayout());
        partyDisplayPanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridBagLayout());


    }

    private static String getTitle(LIST_CLASS_OPTION option) {
        switch (option) {
            case ADD_MONSTERS:
                return "Select a group of Monsters";
            case CHOOSE_PLAYERS:
                return "Select a new Party";
            case EDIT_PLAYERS:
                return "Choose Party to edit";
            case EDIT_MONSTERS:
                return "Choose a group of Monsters to edit";
            default:
                return "Choose a Party";
        }
    }

    public ChoosePartyView(ArrayList<Party> allPartiesList) {

        super("Select a Party");

        setLayout(new GridBagLayout());


        DefaultListModel<Party> partyListModel = new DefaultListModel<>();
        for (Party p : allPartiesList) {
            partyListModel.addElement(p);
        }
        JList partyNames = new JList(partyListModel);

        JPanel listPanel = new JPanel();
        JPanel partyDisplayPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        listPanel.setLayout(new GridBagLayout());
        partyDisplayPanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridBagLayout());

        JButton selectPartyButton = new JButton("Use Chosen Party");

        selectPartyButton.addActionListener(e -> MainViewController.newPartyView((Party) partyNames.getSelectedValue(), passMe));

        partyNames.addListSelectionListener(e -> buildPartyDisplayPanel(partyDisplayPanel, (Party) partyNames.getSelectedValue()));

        buttonPanel.add(selectPartyButton);

        listPanel.add(partyNames);

        add(listPanel);
        g.gridx++;
        add(partyDisplayPanel);
        g.gridx++;
        add(buttonPanel);

        pack();
        setVisible(true);
    }

    private void buildPartyDisplayPanel(JPanel panel, Party party) {
        for (Component c : panel.getComponents()) {
            panel.remove(c);
        }
        g.gridx = 0;
        g.gridy = 0;
        for (Combatant c : party.getCharacters()) {
            panel.add(new JLabel(c.getName()), g);
            g.gridy++;
        }

        panel.repaint();
        panel.revalidate();
        pack();
    }
}
