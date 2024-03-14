package org.example.parsers;

import org.example.data.Candy;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.List;

public class StAXTestNGTest {
    private static StAX staxParser;
    private static final String VALID_PATH = "src/test/resources/test.xml";
    private static final String INVALID_PATH = "invalid_path.xml";

    @BeforeClass
    public static void setupAll() {
        staxParser = new StAX();
    }

    @BeforeMethod
    public void setup() {
        Assert.assertTrue(new File(VALID_PATH).exists(), "Test XML file must exist");
    }

    @Test(groups = {"functional"})
    public void parseDocumentShouldReturnCorrectNumberOfCandies() {
        List<Candy> candies = staxParser.parseDocument(VALID_PATH);
        Assert.assertEquals(candies.size(), 3, "Expected number of candies does not match.");
    }

    @Test(groups = {"functional", "property"})
    public void parseDocumentShouldContainExpectedCandyData() {
        List<Candy> candies = staxParser.parseDocument(VALID_PATH);
        Candy firstCandy = candies.get(0);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(firstCandy.getName(), "Candy Name", "First candy name does not match expected.");
        softAssert.assertEquals(firstCandy.getType().toString(), "Chewy", "First candy type does not match expected.");
        softAssert.assertEquals(firstCandy.getIngredients().size(), 3, "Ingredients size does not match expected.");
        softAssert.assertAll();
    }

    @Test(groups = {"exception"}, expectedExceptions = Exception.class)
    public void parseDocumentShouldHandleException() {
        staxParser.parseDocument(INVALID_PATH);
    }

    @DataProvider(name = "validFilePathProvider")
    public Object[][] validFilePathProvider() {
        return new Object[][]{{VALID_PATH}};
    }

    @Test(groups = {"functional"}, dataProvider = "validFilePathProvider")
    public void parseDocumentWithParameterizedPath(String path) {
        List<Candy> candies = staxParser.parseDocument(path);
        Assert.assertFalse(candies.isEmpty(), "Candies list should not be empty.");
    }

    @Test(groups = {"property"})
    public void candyIdsShouldBeUnique() {
        List<Candy> candies = staxParser.parseDocument(VALID_PATH);
        long uniqueIdsCount = candies.stream().map(Candy::getId).distinct().count();
        Assert.assertEquals(candies.size(), uniqueIdsCount, "Candy IDs should be unique.");
    }
}

