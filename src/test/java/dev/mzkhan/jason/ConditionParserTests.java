package dev.mzkhan.jason;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dev.mzkhan.jason.utils.ConditionParser;

@SpringBootTest
public class ConditionParserTests {
    
    @Test
    void testParserReturnsExpressionSingle() throws ParseException {
        String resourceInputFile = "/input_2.json";
        String uiString = getUIString(resourceInputFile);
        assertEquals("ABA_LEAKAGE > 4 AND test < 4", ConditionParser.parse(uiString));
    }

    @Test
    void testParserReturnsExpressionMultiple() throws ParseException {
        String resourceInputFile = "/input.json";
        String uiString = getUIString(resourceInputFile);
        String expression = ConditionParser.parse(uiString);
        System.out.println(expression);
        // ABA_LEAKAGE < 4 AND (AADHAR_LEAKAGE > 12 OR ADULT_CONTENT == 0 OR (NOT CREDIT_CARD == 5))
        // assertEquals("ABA_LEAKAGE > 4 AND test < 4", expression);
    }

    @SuppressWarnings("deprecation")
    private String getUIString(String inputFile) {
        JSONParser jsonParser = new JSONParser();
        String result = "";
        try {
            String json = IOUtils.toString(this.getClass().getResourceAsStream(inputFile));        
            // Read JSON file
            Object obj = jsonParser.parse(json);
            JSONObject inputs = (JSONObject) obj;
            result = inputs.toJSONString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}
