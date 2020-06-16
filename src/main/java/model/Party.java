package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.*;

public class Party {

    //Static value to track the total number of Party objects created in the application
    static int maxCount = 0;
    //Instance counter for this Party, acts as identifier
    int localCount = maxCount;
    String partyName;

    ArrayList<Combatant> characters = new ArrayList<>();
    ArrayList<Combatant> allCombatants = new ArrayList<>();
    PARTY_TYPE partyType = PARTY_TYPE.PLAYERS;

    /**
     * This no-parameter constructor increments the static maxCount value, ensuring that a counting mechanism
     * is in place to track the total number of parties within the program. All constructors need to call this()!
     */
    public Party() {
        maxCount++;
    }

    public Party(List<Combatant> party) {
        this();
        this.characters = new ArrayList<>(party);
        this.allCombatants = new ArrayList<>(this.characters);
    }


    public Party(JSONObject partyObject) {
        this();
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject playerObject = (JSONObject) partyObject.get("players");
        JSONObject otherObject = (JSONObject) partyObject.get("nonplayers");
        partyType = PARTY_TYPE.valueOf((String) partyObject.get("partyType"));
        try {
            for (Object o : playerObject.values()) {
                JSONObject j = (JSONObject) o;
                characters.add(objectMapper.readValue(j.toJSONString(), Combatant.class));
            }

            allCombatants = new ArrayList<>(characters);
            for (Object o : otherObject.values()) {
                JSONObject j = (JSONObject) o;
                allCombatants.add(objectMapper.readValue(j.toJSONString(), Combatant.class));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public PARTY_TYPE getPartyType() {
        return partyType;
    }

    public void setPartyType(PARTY_TYPE partyType) {
        this.partyType = partyType;
    }

    public static int getMaxCount() {
        return maxCount;
    }

    public static void setMaxCount(int maxCount) {
        Party.maxCount = maxCount;
    }

    public int getLocalCount() {
        return localCount;
    }

    public void setLocalCount(int localCount) {
        this.localCount = localCount;
    }

    public ArrayList<Combatant> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Combatant> characters) {
        this.characters = characters;
    }

    public ArrayList<Combatant> getAllCombatants() {
        return allCombatants;
    }

    public void setAllCombatants(ArrayList<Combatant> allCombatants) {
        this.allCombatants = allCombatants;
    }

    public void sort() {
        Collections.sort(allCombatants);
    }


    public Party rollForInitiative() {
        for (Combatant c : this.allCombatants) {
            c.rollForInitiative();
        }
        Collections.sort(this.allCombatants);
        return this;
    }

    public Party rollMonstersForInitiative() {
        for (Combatant c : this.allCombatants) {
            if (c.getCombatantType() == COMBATANT_TYPE.MONSTER) {
                c.rollForInitiative();
            }
        }
        return this;
    }

    public static List<Combatant> removeCombatant(List<Combatant> combatants, Combatant c) {
        combatants.remove(c);
        return combatants;
    }

    public static List<Combatant> removeCombatant(List<Combatant> combatants, String name) {
        for (Combatant c : combatants) {
            if (c.getName().equals(name)) {
                combatants.remove(c);
                return combatants;
            }
        }
        return combatants;
    }

    public List<Combatant> removeCombatant(Combatant c) {
        ArrayList<Combatant> shortenedCharacters = new ArrayList<>(this.characters);
        shortenedCharacters.remove(c);
        return shortenedCharacters;
    }

    public List<Combatant> removeCombatant(String name) {
        ArrayList<Combatant> shortenedCharacters = new ArrayList<>(this.characters);
        for (Combatant c : shortenedCharacters) {
            if (c.getName().equals(name)) {
                shortenedCharacters.remove(c);
                return shortenedCharacters;
            }
        }
        return shortenedCharacters;

    }

    public List<Combatant> clearParty() {
        this.allCombatants = new ArrayList<>(this.characters);
        return this.allCombatants;
    }

    public List<Combatant> addCombatant(Combatant c) {
        this.allCombatants.add(c);
        Collections.sort(allCombatants);
        return this.allCombatants;
    }

    public JSONObject toJSONObject() {
        JSONObject pObject = new JSONObject(), playerObject = new JSONObject(), otherObject = new JSONObject();

        for (int i = 0; i < allCombatants.size(); i++) {
            Combatant c = allCombatants.get(i);
            if (c.getCombatantType() == COMBATANT_TYPE.PLAYER) {
                playerObject.put("player" + i, c.toJSONObject());
                continue;
            }
            otherObject.put("nonplayer" + i, c.toJSONObject());
        }

        pObject.put("players", playerObject);
        pObject.put("nonplayers", otherObject);
        pObject.put("partyType", partyType.toString());
        pObject.put("partyName", partyName);
        return pObject;
    }

    private String[] getCharacterNames() {

        String[] namesArray = new String[characters.size()];
        for (int i = 0; i < namesArray.length; i++) {
            namesArray[i] = characters.get(i).getName();
        }
        return namesArray;

    }

    @Override
    public String toString() {
        if (partyName == null || partyName.length() == 0) {
            return String.join(", ", getCharacterNames());
        }
        return partyName;
    }


}
