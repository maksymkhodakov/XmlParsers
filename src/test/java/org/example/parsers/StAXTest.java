package org.example.parsers;

import org.example.data.Candy;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StAXTest {

    @Test
    public void testParseDocument() {
        final String path = "/Users/maksymkhodakov/IdeaProjects/xml-parsers/XmlParsers/src/test/resources/test.xml";
        StAX stax = new StAX();

        List<Candy> candies = stax.parseDocument(path);

        assertNotNull(candies);

        assertEquals(3, candies.size());

        assertEquals("Candy Name", candies.get(0).getName());
        assertEquals(2, candies.get(0).getEnergy());
        assertEquals("{val3=3, val2=2, val1=3}", candies.get(0).getValue().toString());

        assertEquals("Candy Name2", candies.get(1).getName());
        assertEquals(221312, candies.get(1).getEnergy());
        assertEquals("{val3=3, val2=2, val1=3}", candies.get(1).getValue().toString());

        assertEquals("Candy Name3", candies.get(2).getName());
        assertEquals(12312431, candies.get(2).getEnergy());
        assertEquals("{val3=3, val2=2, val1=3}", candies.get(2).getValue().toString());
    }
}
