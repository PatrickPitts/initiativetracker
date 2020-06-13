package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Combatant implements Creature, Comparable<Combatant>{

    private int initiative = 0;
    private int dexterity;
    private String name;
    private COMBATANT_TYPE combatantType = COMBATANT_TYPE.MONSTER;
    private Map<String, Integer> initiativeBonuses = new HashMap<>();
    private int maxHP;
    private int currentHP;
    private int armorClass = 10;

    public Combatant(){}

    public Combatant(String name, int dexterity){
        this.name = name;
        this.dexterity = dexterity;
    }

    public int getInitiativeModifier(){
        return 0;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getInitiative(){
        return initiative;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCombatantType(COMBATANT_TYPE combatantType) {
        this.combatantType = combatantType;
    }

    public Map<String, Integer> getInitiativeBonuses() {
        return initiativeBonuses;
    }

    public void setInitiativeBonuses(Map<String, Integer> initiativeBonuses) {
        this.initiativeBonuses = initiativeBonuses;
    }

    public void addInitiativeBonus(String bonusName, Integer value){
        initiativeBonuses.put(bonusName, value);
    }

    public void setCombatantTypeToPlayer(){
        this.combatantType = COMBATANT_TYPE.PLAYER;
    }

    public void setCombatantTypeToMonster(){
        this.combatantType = COMBATANT_TYPE.MONSTER;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
        setCurrentHP(maxHP);
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public COMBATANT_TYPE getCombatantType(){
        return this.combatantType;
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public int compareTo(Combatant o) {

        return (o.getInitiative() == this.getInitiative()) ? Integer.compare(o.getDexterity(), this.getDexterity()) : Integer.compare(o.getInitiative(), this.getInitiative());
    }

    public JSONObject toJSONObject(){
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("dexterity", dexterity);
        obj.put("combatantType", combatantType.toString());
        obj.put("initiative", initiative);
        obj.put("initiativeBonuses", new JSONObject(initiativeBonuses));
        obj.put("currentHP", currentHP);
        obj.put("maxHP", maxHP);
        obj.put("armorClass", armorClass);
        return obj;
    }

    public void resetInitiative(){
        initiative = 10;
    }

    public void rollForInitiative(){
        this.initiative = Roller.d20() + (this.getDexterity() - 10)/2;
        for(Integer bValue : initiativeBonuses.values()){
            this.initiative += bValue;
        }
    }

    public void fullHeal(){
        this.currentHP = this.maxHP;
    }

    public void changeHP(int change){
        int newHP = currentHP + change;
//        this.currentHP = (newHP > 0) ? currentHP = newHP : 0;
        if(newHP < 0){
            this.currentHP = 0;
        } else if(newHP > maxHP){
            this.currentHP = maxHP;
        }
        this.currentHP = newHP;
    }

    /**
     * This method adds an additional initiative bonus to the class, with a name optionally given.
     *
     * @param i the initiative bonus to be applied
     * @param name the name of the initiative bonus, optionally
     */
    public void addInitiativeBonus(int i, String... name){
        String bonusName;
        if(name == null){
            bonusName = "Unnamed Bonus "+ initiativeBonuses.size();
        } else {
            bonusName = name[0];
        }
        initiativeBonuses.put(bonusName, i);
    }



}
