package org.example;

import jakarta.xml.bind.JAXBException;
import org.example.data.Candies;
import org.example.data.Candy;
import org.example.data.CandyType;
import org.example.parsers.DOM;
import org.example.parsers.SAX;
import org.example.parsers.StAX;
import org.example.service.CandyMarshallingService;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

    public static final String CANDIES_OUTPUT_FILENAME = "candies";
    public static final String CANDIES_XML = "./candies.xml";

    public static void main(String[] args) throws JAXBException, IOException {
        // marshalling and unmarshalling of xml part
        System.out.println("\t\t\t\t\t\tMARSHALLING AND UNMARSHALLING");
        final Candies candies = new Candies();
        candies.setCandies(getCandies());
        CandyMarshallingService candyMarshallingService = new CandyMarshallingService();
        candyMarshallingService.marshal(candies);
        final Candies returnedCandies = candyMarshallingService.unmarshal(CANDIES_OUTPUT_FILENAME);
        System.out.println(returnedCandies.getCandies());

        // SAX parser work
        System.out.println("\n\t\t\t\t\t\tSAX PARSER");
        SAX parser = new SAX();
        parser.parseDocument(CANDIES_XML);
        System.out.println(getSorted(parser.getCandies()));

        // DOM parser work
        System.out.println("\n\t\t\t\t\t\tDOM PARSER");
        DOM dom = new DOM();
        System.out.println(getSorted(dom.parseDocument(CANDIES_XML)));

        // StAX parser work
        System.out.println("\n\t\t\t\t\t\tStAX PARSER");
        StAX stax = new StAX();
        System.out.println(getSorted(stax.parseDocument(CANDIES_XML)));
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

    private static List<Candy> getSorted(List<Candy> candies) {
        return candies
                .stream()
                .sorted(Comparator.comparingInt(Candy::getEnergy))
                .collect(Collectors.toList());
    }
}
