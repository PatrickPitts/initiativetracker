package view;

import controller.MainViewController;
import javafx.scene.control.TableCell;
import model.Combatant;
import model.Party;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
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
    private Party currentParty;

    Object[] headers = new Object[]{"Combatant", "Initiative", "HP", "Max HP", "Hit/Heal", "AC"};
    Object[][] partyData;

    private JFrame thisMainView = this;

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
        this.currentParty = party;
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
        JButton rollMonsterInitiativeButton = new JButton("Roll Monsters Only");
        JButton createNewPartyButton = new JButton("Create New Party");


        buttonPanel.add(rollMonsterInitiativeButton, g);
        g.gridy++;
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
        g.gridy++;
        buttonPanel.add(createNewPartyButton, g);

        clearParty.addActionListener(e -> MainViewController.clearParty());

        rollInitButton.addActionListener(e -> MainViewController.totalPartyReroll());

        sortParty.addActionListener(e -> {
            if(initiativeTable.getCellEditor() != null){
                initiativeTable.getCellEditor().stopCellEditing();
            }
            MainViewController.sortParty(party, initiativeTable);
        });

        addCombatantButton.addActionListener(e -> MainViewController.buildAddCombatantView());

        choosePartyButton.addActionListener(e -> MainViewController.buildSelectPartyView());

        rollMonsterInitiativeButton.addActionListener(e -> MainViewController.rollMonstersInParty());

        createNewPartyButton.addActionListener(e-> MainViewController.buildCreateNewPartyView());

    }

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
        initiativeTable = new JTable(new InitiativeTableModel(party));
        initiativeTable.setDefaultRenderer(Object.class, new InitiativeTableCellRenderer());
        initiativeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        initiativeTable.setRowHeight(75);
        initiativeTable.getColumnModel().getColumn(4).setPreferredWidth(300);
        initiativeTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
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

    private Object[][] getTableArray(Party party) {
        partyData = new Object[party.getAllCombatants().size()][headers.length];
        for (int i = 0; i < party.getAllCombatants().size(); i++) {
            Combatant c = party.getAllCombatants().get(i);
            partyData[i][0] = c;
            partyData[i][1] = c.getInitiative();
            partyData[i][2] = c.getCurrentHP();
            partyData[i][3] = c.getMaxHP();
            partyData[i][4] = c;
            partyData[i][5] = c.getArmorClass();
        }
        return partyData;
    }

    class HitHealPanel extends JPanel{

        HitHealPanel(Combatant c){
            setLayout(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();

            JButton plusOneHealthButton = new JButton("+");
            JButton minusOneHealthButton = new JButton("-");
            JButton hitButton = new JButton("Hit");
            JButton healButton = new JButton("Heal");

            plusOneHealthButton.setPreferredSize(new Dimension(45,25));
            minusOneHealthButton.setPreferredSize(new Dimension(45,25));

            hitButton.setPreferredSize(healButton.getPreferredSize());

            JTextField damageField = new JTextField("0");
            damageField.setPreferredSize(new Dimension(50, damageField.getPreferredSize().height));

            plusOneHealthButton.addActionListener(e -> {c.changeHP(1); ((MainView)SwingUtilities.getWindowAncestor(this)).buildCombatantPanel(currentParty); });
            minusOneHealthButton.addActionListener(e -> {c.changeHP(-1); ((MainView)SwingUtilities.getWindowAncestor(this)).buildCombatantPanel(currentParty);});
            hitButton.addActionListener(e -> {c.changeHP(-1*Integer.parseInt(damageField.getText())); ((MainView)SwingUtilities.getWindowAncestor(this)).buildCombatantPanel(currentParty);});
            healButton.addActionListener(e -> {c.changeHP(Integer.parseInt(damageField.getText())); ((MainView)SwingUtilities.getWindowAncestor(this)).buildCombatantPanel(currentParty);});

            damageField.addKeyListener(new NumbersOnlyKeyListener(damageField));



            add(plusOneHealthButton, g);
            g.gridy = 1;
            add(minusOneHealthButton, g);
            g.gridx = 1; g.gridy = 0; g.gridheight=2;
            add(damageField, g);
            g.gridx = 2; g.gridheight=1;
            add(healButton, g);
            g.gridy= 1;
            add(hitButton, g);

            revalidate();
        }
    }

    class InitiativeTableCellRenderer implements TableCellRenderer{

        private TableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            if(value instanceof Component){
                return (Component) value;
            }

            if(value instanceof Combatant){
                Combatant c = (Combatant) value;
                JTextField cell = new JTextField();
                cell.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
                switch (c.getCombatantType()){
                    case MONSTER:
                        cell.setBackground(Color.RED);
                        break;
                    case PLAYER:
                        cell.setBackground(Color.GREEN);
                        break;
                    case ALLY:
                        cell.setBackground(Color.YELLOW);
                        break;
                    default:
                        cell.setBackground(Color.LIGHT_GRAY);
                        break;
                }
                switch (column){
                    case 0:
                        cell.setText(c.getName());
                        return cell;
                    case 1:
                        cell.setText(Integer.toString(c.getInitiative()));
                        cell.addKeyListener(new NumbersOnlyKeyListener(cell));
                        return cell;
                    case 2:
                        cell.setText(Integer.toString(c.getCurrentHP()));
                        cell.addKeyListener(new NumbersOnlyKeyListener(cell));
                        return cell;
                    case 3:
                        cell.setText(Integer.toString(c.getMaxHP()));
                        cell.setEditable(false);
                        return cell;
                    case 4:

                        return new MainView.HitHealPanel(c);
                    case 5:
                        cell.setText(Integer.toString(c.getArmorClass()));
                        return cell;
                    default:
                        return null;
                }
            }
            return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    class ButtonEditor extends DefaultCellEditor{

        public ButtonEditor(JTextField textField) {
            super(textField);
        }

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public ButtonEditor(JComboBox comboBox) {
            super(comboBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
            return new HitHealPanel((Combatant) table.getValueAt(row,column));
        }
    }

    class InitiativeTableModel extends DefaultTableModel{

        public InitiativeTableModel(Party party){
            super(getTableArray(party), headers);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return true;
        }
        @Override
        public int getRowCount() {
            return partyData.length;
        }

        @Override
        public int getColumnCount() {
            return headers.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return partyData[rowIndex][columnIndex];
        }
    }

}
