package com.vooders.romanNumerals;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@EnableAutoConfiguration
public class MainController implements RomanNumeralGenerator {

    @RequestMapping(value = "/")
    public String index() {
        return "/index.html";
    }

    @GetMapping("/generate")
    public @ResponseBody String generate(
            @RequestParam(value="number", required=false, defaultValue="1") int number) {
        // Check for out of bounds
        if (number < 1 || number > 3999)
            return "Out of bounds";

        // We ned to store all the numerals with their decimal values
        // We can treat the 'one unders' as their own numeral
        LinkedHashMap<String, Integer> numerals = new LinkedHashMap<String, Integer>();
        numerals.put("M", 1000);
        numerals.put("CM", 900);
        numerals.put("D", 500);
        numerals.put("CD", 400);
        numerals.put("C", 100);
        numerals.put("XC", 90);
        numerals.put("L", 50);
        numerals.put("XL", 40);
        numerals.put("X", 10);
        numerals.put("IX", 9);
        numerals.put("V", 5);
        numerals.put("IV", 4);
        numerals.put("I", 1);

        StringBuilder numeralString = new StringBuilder();
        // We need to loop through the numerals map and figure out how many of each we need
        for(Map.Entry<String, Integer> entry : numerals.entrySet()){
            // Divide by the current value, this will tell us how many we need
            int amount = number/entry.getValue();

            if(amount != 0) { // If we need more than 0 of a numeral
                // Get the amount of the numeral we need from buildChunk and append it to the result string
                numeralString.append(buildChunk(entry.getKey(), amount));
                // Subtract the equivalent of the numeral we've added from the number
                number = number % entry.getValue();
            }
        }
        // Return the completed string
        return numeralString.toString();
    }

    /**
     * Builds chunks of numerals by repeating a numeral n times
     * @param numeral: The numeral to repeat eg. X
     * @param amount: The amount of times to repeat eg. 3
     * @return numeral chunk eg. XXX
     */
    private static String buildChunk(String numeral, int amount) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < amount; i++) {
            sb.append(numeral);
        }
        return sb.toString();
    }
}
