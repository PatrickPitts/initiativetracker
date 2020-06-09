package view;

import controller.MainViewController;
import model.Combatant;
import model.Party;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel combatantPanel;
    private JTable initiativeTable;
    private GridBagConstraints g = new GridBagConstraints();

    //calls the super constructor, called by Party-parameter constructor
    public MainView() {
        super("Initiative Tracker");
        mainViewInit();
    }

    /**
     * Party-parameter constructor, called by MainViewController. Calls No-parameter constructor then implements
     * Party specific content display methods.
     *
     * @param party
     */
    public MainView(Party party) {
        this();
        rebuild(party);
    }

    public void rebuild(Party party){
        buildButtonPanel(party);
        buildCombatantPanel(party);
        repaint();
        revalidate();
    }

    /**
     * Method to build the left-hand panel that holds buttons that implement other actions of this view of the application
     *
     * @param party
     */
    private void buildButtonPanel(final Party party) {
        for (Component c : buttonPanel.getComponents()) {
            buttonPanel.remove(c);
        }


        defaultGridBagConstraints();

        JButton choosePartyButton = new JButton("Change Party");
        JButton rollInitButton = new JButton("Roll for Initiative!");
        JButton sortParty = new JButton("Just sort 'em");
        JButton clearParty = new JButton("Clear out the extras");
        JButton addCombatantButton = new JButton("Add Combatant");



        buttonPanel.add(sortParty, g);
        g.gridy++;
        buttonPanel.add(rollInitButton, g);
        g.gridy++;
        buttonPanel.add(addCombatantButton, g);
        g.gridy++;
        JSeparator hbar = new JSeparator();
        hbar.setPreferredSize(new Dimension(buttonPanel.getPreferredSize().width, 2));

        buttonPanel.add(hbar, g);
        g.gridy++;
        buttonPanel.add(clearParty, g);
        g.gridy++;
        buttonPanel.add(choosePartyButton, g);

        clearParty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainViewController.clearParty();
            }
        });

        rollInitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainViewController.totalPartyReroll();
            }
        });

        sortParty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(initiativeTable.getCellEditor() != null){
                    initiativeTable.getCellEditor().stopCellEditing();
                }
                MainViewController.sortParty(party, initiativeTable);
            }
        });

        addCombatantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainViewController.buildAddCombatantView();
            }
        });

        choosePartyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainViewController.buildSelectPartyView();
            }
        });

    }


    /*TODO:
     * Add functionality to change player scores when rolled out of program
     * */

    /**
     * Method to build the right-hand panel that displays the sorted initiative information
     *
     * @param party The Selected party for the initiative tracker to track
     */
    public void buildCombatantPanel(final Party party) {
        for (Component c : combatantPanel.getComponents()) {
            combatantPanel.remove(c);
        }

        defaultGridBagConstraints();

        initiativeTable = new JTable(new InitTableModel(party)){
            public Component prepareRenderer(TableCellRenderer renderer,int row, int col){
                JComponent c = (JComponent) super.prepareRenderer(renderer, row, col);
                switch (party.getAllCombatants().get(row).getCombatantType()) {
                    case MONSTER:
                        c.setBackground(Color.RED);
                        break;
                    case PLAYER:
                        c.setBackground(Color.GREEN);
                        break;
                    case ALLY:
                        c.setBackground(Color.YELLOW);
                        break;
                    default:
                        c.setBackground(Color.LIGHT_GRAY);
                        break;
                }
                //Sets font for the table
                c.setFont(new Font("Papyrus", Font.BOLD, 30));
                c.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

                return c;
            }
        };
        initiativeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        initiativeTable.setRowHeight(38);
        JScrollPane tableScroller = new JScrollPane(initiativeTable);
        combatantPanel.add(tableScroller);
        pack();
    }

    /**
     * Initializes key foundational Swing components for the view that don't display or require specific party information
     */
    private void mainViewInit() {

        defaultGridBagConstraints();

        setSize(400, 300);
        setLayout(new GridBagLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        mainPanel.add(buttonPanel, g);

        g.gridx++;

        combatantPanel = new JPanel();
        combatantPanel.setLayout(new GridBagLayout());
        mainPanel.add(combatantPanel, g);

        add(mainPanel);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //Resets the GridBagConstraints to standardized values
    private void defaultGridBagConstraints() {
        g.insets = new Insets(5, 5, 5, 5);
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.gridheight = 1;

    }

}


//Organizes and stores display data
class InitTableModel extends DefaultTableModel {

    static Object[] headers = new Object[]{"Combatant", "Initiative"};

    InitTableModel(Party party) {
        super(getTableArray(party), headers);
    }

    private static Object[][] getTableArray(Party party) {
        Object[][] partyData = new Object[party.getAllCombatants().size()][headers.length];
        for (int i = 0; i < party.getAllCombatants().size(); i++) {
            Combatant c = party.getAllCombatants().get(i);

            partyData[i][0] = c.getName();
            partyData[i][1] = c.getInitiative();
        }
        return partyData;
    }

}
