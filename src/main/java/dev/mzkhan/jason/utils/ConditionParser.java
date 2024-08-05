package dev.mzkhan.jason.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class ConditionParser {
    public static final String FIELDS = "fields";

    public static String parse(String condition) throws ParseException{
        
        JSONParser jsonParser = new JSONParser();
        // Read JSON file
        Object obj = jsonParser.parse(condition);
        JSONObject inputs = (JSONObject) obj;

        JSONObject root_node = (JSONObject) inputs.get("0");
        if (Integer.parseInt(root_node.get("parentId").toString()) != -1) {
            throw new ParseException(0);
        }

        return nodeTraversal(root_node ,inputs);
    }

    private static String nodeTraversal(JSONObject currentObject, JSONObject completeJson) throws ParseException {
        StringBuilder result = new StringBuilder();
        JSONArray fields = (JSONArray) (currentObject.get(FIELDS));

        // If there are no fields in the Object,it is a leaf node
        //  we return the expression
        if (fields == null || fields.size() == 0) {
            String operand_1 = currentObject.get("value").toString();
            String operand_2 = currentObject.get("textVal").toString();
            String operator = currentObject.get("comparisonOperator").toString();
            result = result.append(operand_1).append(" ").append(operator).append(" ").append(operand_2);
            //return result.toString();
        }
        else {
            JSONObject ruleObject = (JSONObject) currentObject.get("ruleSelected");
            if (ruleObject == null) {
                throw new ParseException(0);
            }
            String operator = ruleObject.get("value").toString();

            // If there is only 1 arguments in the field, 
            //  it means that its a unary logical operator
            if (fields.size() == 1) {
                String operandObjKey = fields.get(0).toString();
                JSONObject operand = (JSONObject) completeJson.get(operandObjKey);

                String operandExpression = nodeTraversal(operand, completeJson);
                result =  result.append("(").append(operator).append(" ").append(operandExpression).append(")");
            }
            // If there are multiple fields
            else {
                result = result.append("(");
                for (int i=0; i<fields.size(); i++) {
                    JSONObject operand = (JSONObject) completeJson.get( fields.get(i).toString());
                    String operandExpression = nodeTraversal(operand, completeJson);
                    if (i != 0 ) {
                        result = result.append(" ").append(operator).append(" "); 
                    }
                    result = result.append(operandExpression);
                }
                result = result.append(")");
            }
        }

        return result.toString();
    }
}
