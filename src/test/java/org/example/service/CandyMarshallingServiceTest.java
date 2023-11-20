package org.example.service;

import jakarta.xml.bind.JAXBException;
import org.example.data.Candies;
import org.example.data.Candy;
import org.example.data.CandyType;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class CandyMarshallingServiceTest {

    public static final String PATH = "/Users/maksymkhodakov/IdeaProjects/xml-parsers/XmlParsers/src/test/resources/test.xml";

    @Test
    public void testMarshal() throws JAXBException, IOException {
        final Candies candies = new Candies();
        candies.setCandies(getCandies());
        CandyMarshallingService candyMarshallingService = new CandyMarshallingService();
        candyMarshallingService.marshal(candies, PATH);
        String xml = new String(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("test.xml")).readAllBytes());
        assertNotNull(xml);
    }

    @Test
    public void testUnmarshal() throws JAXBException, IOException {
        final String path = PATH;
        final Candies candies = new Candies();
        candies.setCandies(getCandies());
        CandyMarshallingService candyMarshallingService = new CandyMarshallingService();
        candyMarshallingService.marshal(candies, path);
        Candies result = candyMarshallingService.unmarshal(path);
        assertNotNull(result);
        assertEquals(3, result.getCandies().size());
    }

    // get candy data
    private static List<Candy> getCandies() {
        Candy candy1 = new Candy();
        candy1.setId(1);
        candy1.setName("Candy Name");
        candy1.setType(CandyType.Chewy);
        candy1.setEnergy(2);
        candy1.setProduction("production");
        candy1.setIngredients(Map.of("a", 1, "b", 2, "c", 3));
        candy1.setValue(Map.of("val1", 3, "val2", 2, "val3", 3));
        candy1.setProductionDate(new Date());

        Candy candy2 = new Candy();
        candy2.setId(2);
        candy2.setName("Candy Name2");
        candy2.setType(CandyType.Hard);
        candy2.setEnergy(221312);
        candy2.setProduction("pre production");
        candy2.setIngredients(Map.of("x", 1, "y", 2, "z", 3));
        candy2.setValue(Map.of("val1", 3, "val2", 2, "val3", 3));
        candy2.setProductionDate(new Date());

        Candy candy3 = new Candy();
        candy3.setId(3);
        candy3.setName("Candy Name3");
        candy3.setType(CandyType.Soft);
        candy3.setEnergy(12312431);
        candy3.setProduction("pre pre production");
        candy3.setIngredients(Map.of("t", 1, "u", 2, "p", 3));
        candy3.setValue(Map.of("val1", 3, "val2", 2, "val3", 3));
        candy3.setProductionDate(new Date());

        return List.of(candy1, candy2, candy3);
    }
}