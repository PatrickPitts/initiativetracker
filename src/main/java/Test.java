import controller.DataController;
import model.Combatant;
import model.Party;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Test {

    static String filePath = "src/main/resources/partys.json";

    public static void main(String[] args) {
        testBuild();
        testRead();
    }

    public static ArrayList<Combatant> testBuild() {

        Party party = new Party();
        party.addCombatant(new Combatant("Player1", 10));
        party.addCombatant(new Combatant("Player2", 14));
        party.addCombatant(new Combatant("Player3", 19));

        party.getAllCombatants().get(0).setCombatantTypeToPlayer();
        party.getAllCombatants().get(1).setCombatantTypeToPlayer();
        party.getAllCombatants().get(2).setCombatantTypeToPlayer();

        Party secondParty = new Party();

        secondParty.addCombatant(new Combatant("Alpha", 15));
        secondParty.addCombatant(new Combatant("Beta", 13));
        secondParty.addCombatant(new Combatant("Gamma", 9));

        secondParty.getAllCombatants().get(0).setCombatantTypeToPlayer();
        secondParty.getAllCombatants().get(1).setCombatantTypeToPlayer();
        secondParty.getAllCombatants().get(2).setCombatantTypeToPlayer();

        final Map<Integer, Party> allParties = new HashMap<>();
        {
            allParties.put(0, party);
            allParties.put(1, secondParty);
        }

        //Updated this method, takes Map now
        DataController.writeAllPartiesToJSON(allParties);
        return null;
    }

    public static void testRead() {
        JSONObject partiesObject = DataController.readAllPartiesAsJSONObject(filePath);
        for(Object key : partiesObject.keySet()){
            System.out.println(key);
            System.out.println(partiesObject.get(key));
        }
    }

    public static void testParty() {
        Scanner sc = new Scanner(System.in);

        Party party = new Party(testBuild());
        Combatant newCombatant;
        boolean flag = true;
        int counter = 1;
        while (flag) {

            for (Combatant c : party.getAllCombatants()) {
                System.out.println((c.getName() + " " + c.getInitiative()));
            }


            party.rollForInitiative();

            newCombatant = new Combatant("Monster" + counter++, 10);
            newCombatant.rollForInitiative();

            party.addCombatant(newCombatant);

            System.out.println("[y] again");
            String res = sc.nextLine();
            flag = res.equals("y");

        }
    }
}
