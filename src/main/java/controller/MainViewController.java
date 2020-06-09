package controller;

import model.COMBATANT_TYPE;
import model.Combatant;
import model.Party;
import view.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainViewController {

    public static MainView mainView;

    public static Party currentParty;

    public static Map<Integer, Party> allParties = new HashMap<>();

    public static void initView(){

        allParties = JSONDataController.getAllPartiesMap();

        currentParty = allParties.get(0);
        currentParty.rollForInitiative();
        mainView = new MainView(currentParty);
    }

    //TODO: static method call to roll party and update party display panel

    public static void totalPartyReroll(){
        currentParty.rollForInitiative();
        mainView.rebuild(currentParty);
    }

    public static void totalPartyReroll(Party party){
        mainView.rebuild(party.rollForInitiative());
    }

    public static void sortParty(Party party, JTable dataTable){
        for(int i = 0; i < party.getAllCombatants().size(); i++){
            Combatant c = party.getAllCombatants().get(i);
            Object tableValue = dataTable.getModel().getValueAt(i, 1);
            if(tableValue instanceof String){
                c.setInitiative(Integer.parseInt((String)tableValue));
            } else if (tableValue instanceof Integer){
                c.setInitiative((Integer)tableValue);
            }
        }
        party.sort();
        mainView.rebuild(party);
    }


    public static void buildAddCombatantView(){
        JFrame addFrame = new AddCombatantView();
    }

    public static void destroyFrame(JFrame jFrame){
        jFrame.dispose();
    }

    public static void addCombatant(String name, COMBATANT_TYPE combatantType, int dexterity, int bonus){
        Combatant c = new Combatant(name, dexterity);

        if(bonus != 0){
            c.addInitiativeBonus("SingleNewCombatantBonus", bonus);
        }
        c.setCombatantType(combatantType);
        c.rollForInitiative();
        currentParty.addCombatant(c);
        mainView.rebuild(currentParty);
    }

    public static void clearParty(){
        currentParty.clearParty();
        mainView.rebuild(currentParty);
    }

    public static void buildSelectPartyView(){
        JFrame selectPartyFrame = new ChoosePartyView(new ArrayList<>(allParties.values()));
    }
}
