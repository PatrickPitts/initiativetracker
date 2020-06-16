package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import model.Combatant;
import model.Party;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;

public class DataController {

    static String filePath = "src/main/resources/partys.json";

    public static void writePartyToJSON(Party party) {
        try (FileWriter f = new FileWriter(filePath)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeAllPartiesToJSON(Map<Integer, Party> parties) {

        JSONObject allPartiesObj = new JSONObject();

        for (Integer partyIndex : parties.keySet()) {
            allPartiesObj.put( partyIndex, parties.get(partyIndex).toJSONObject());
        }

        try (FileWriter f = new FileWriter(filePath)) {

            f.write(allPartiesObj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads
     *
     * @return a single org.json.simple.JSONObject that contains all parties
     */
    public static JSONObject readAllPartiesAsJSONObject(String partiesFilePath) {

        JSONParser jsonParser = new JSONParser();
        JSONObject allPartiesObject = new JSONObject();
        try (FileReader f = new FileReader(partiesFilePath)) {
            allPartiesObject = (JSONObject) jsonParser.parse(f);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return allPartiesObject;
    }

    public static Map<Integer, Party> getAllPartiesMap() {
        //Currently assumes only one filepath, for one file of parties. May expand to multiple files
        //if the file size becomes and issue
        JSONObject allPartiesObject = readAllPartiesAsJSONObject(filePath);

        Map<Integer, Party> partyMap = new HashMap<>(allPartiesObject.keySet().size());


        for (Object key : allPartiesObject.keySet()) {
            System.out.println(key);
            Integer partyKey = Integer.parseInt((String) key);
            partyMap.put(partyKey, new Party((JSONObject) allPartiesObject.get(key)));
        }


        return partyMap;
    }

    public static void writeAllPartiesToFile(Map<Integer, Party> allParties){
        try (FileWriter f = new FileWriter(filePath)) {
            JSONObject allPartiesObject = new JSONObject();
            for(Integer key : allParties.keySet()){
                allPartiesObject.put(key, allParties.get(key).toJSONObject());
            }
            f.write(allPartiesObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
