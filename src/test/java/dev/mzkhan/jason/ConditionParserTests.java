package dev.mzkhan.jason;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mzkhan.jason.utils.ConditionParser;

@SpringBootTest
public class ConditionParserTests {
    
    @Test
    void parserReturns() {
        assertEquals(ConditionParser.parse("Testing").length, 2);
    }
}
