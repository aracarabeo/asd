package com.example.demo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Random;


@RestController
@Validated
public class DiceController {
    @GetMapping("/Roll")
    public HashMap<HashMap, HashMap> roll(@RequestParam(value = "numOfRolls", defaultValue = "100") @Min(1) int numOfRolls, @RequestParam(value = "numOfSides", defaultValue = "6") @Min(4) int numOfSides, @RequestParam(value = "numOfDice", defaultValue = "3") @Min(1) int numOfDice){
        HashMap<HashMap<String,Integer>, HashMap<String, Integer>> json = new HashMap<HashMap<String,Integer>, HashMap<String, Integer>>();
        HashMap<String, Integer> subJSON,keyJSON;
        HashMap<Integer,Integer> totalHash;
        int rolledNum;
        int rollNum;
        int rollDice;
        int total;
        int maxpossibleTotal = numOfDice*numOfSides;
        Random rand = new Random();
        totalHash = new HashMap<>();
        for(int k=numOfDice;k<=maxpossibleTotal;k++){ //initialize all possible totals in a hashmap
            totalHash.put(k,0);
        }
        for(int j=0;j<numOfRolls;j++) {
            total = 0;
            rollNum = j+1;
            keyJSON = new HashMap<>();
            subJSON = new HashMap<>();
            for (int i = 0; i < numOfDice; i++) {
                rollDice = i+1;
                rolledNum = rand.nextInt(numOfSides) + 1;
                subJSON.put("Roll Number "+rollDice,rolledNum);
                total = total + rolledNum;
            }
            totalHash.put(total,totalHash.get(total)+1); //update totalHash depending on total
            keyJSON.put("Total of Roll " + rollNum,total);
            json.put(keyJSON, subJSON);
        }
        HashMap<HashMap,HashMap> finalJSON = new HashMap();
        finalJSON.put(json,totalHash);
        return finalJSON;
    }
}

