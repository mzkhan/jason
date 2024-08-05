package dev.mzkhan.jason.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.mzkhan.jason.utils.ConditionParser;

@RestController
public class ExpressionParseController {

    @PostMapping("/expression")
    public String ConvertExpression(@RequestBody String uiJsonString) {
        try {
            return ConditionParser.parse(uiJsonString);
        } catch (Exception e) {
           return "Error in Parsing String: " + e.getMessage();
        }
    }
}
