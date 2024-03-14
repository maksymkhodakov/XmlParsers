package org.example.parsers;


import org.example.data.Candy;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.List;

public class SAXTestNGTest {
    private static SAX saxParser;
    private static final String VALID_PATH = "src/test/resources/test.xml";

    @BeforeClass
    public static void setupAll() {
        saxParser = new SAX();
    }

    @BeforeMethod
    public void setup() {
        Assert.assertTrue(new File(VALID_PATH).exists(), "Test file should exist.");
    }

    @Test(groups = {"functional"})
    public void parseDocumentShouldReturnCorrectNumberOfCandies() {
        saxParser.parseDocument(VALID_PATH);
        List<Candy> candies = saxParser.getCandies();
        Assert.assertEquals(candies.size(), 15, "Number of candies parsed does not match expected.");
    }

    @Test(groups = {"functional", "property"})
    public void parseDocumentShouldContainSpecificCandyProperties() {
        saxParser.parseDocument(VALID_PATH);
        List<Candy> candies = saxParser.getCandies();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(candies.get(0).getName(), "Candy Name", "Candy name does not match expected.");
        softAssert.assertEquals(candies.get(0).getIngredients().size(), 3, "Ingredients map size does not match expected.");
        softAssert.assertEquals(candies.get(0).getValue().size(), 3, "Value map size does not match expected.");
        softAssert.assertEquals(candies.get(0).getProduction(), "production", "Production does not match expected.");
        softAssert.assertAll();
    }

    @Test(groups = {"functional"})
    public void parseDocumentShouldCorrectlyParseProductionDate() {
        saxParser.parseDocument(VALID_PATH);
        List<Candy> candies = saxParser.getCandies();
        candies.forEach(candy -> Assert.assertNotNull(candy.getProductionDate(), "Production date should not be null."));
    }

    @DataProvider(name = "validFilePathProvider")
    public Object[][] validFilePathProvider() {
        return new Object[][]{{VALID_PATH}};
    }

    @Test(groups = {"functional"}, dataProvider = "validFilePathProvider")
    public void parseDocumentWithDifferentInputs(String filePath) {
        saxParser.parseDocument(filePath);
        List<Candy> candies = saxParser.getCandies();
        Assert.assertFalse(candies.isEmpty(), "Candies list should not be empty.");
    }

    @Test(groups = {"property"})
    public void candyValuesAreCorrect() {
        saxParser.parseDocument(VALID_PATH);
        List<Candy> candies = saxParser.getCandies();
        candies.forEach(candy -> Assert.assertTrue(candy.getEnergy() > 0, "Energy should be greater than 0."));
    }

    @Test(groups = {"property"})
    public void candyIdsShouldBeUnique() {
        saxParser.parseDocument(VALID_PATH);
        List<Candy> candies = saxParser.getCandies();
        long uniqueIds = candies.stream().map(Candy::getId).distinct().count();
        Assert.assertEquals(uniqueIds, candies.size(), "Candy IDs should be unique.");
    }
}

