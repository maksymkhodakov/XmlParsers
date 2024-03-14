package org.example.parsers;

import org.example.data.Candy;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.List;

public class DOMTestNGTest {
    private static DOM domParser;
    private static final String VALID_PATH = "src/test/resources/test.xml";
    private static final String INVALID_PATH = "invalid_path.xml";

    @BeforeClass
    public static void setupAll() {
        domParser = new DOM();
    }

    @BeforeMethod
    public void setup() {
        // Переконуємось, що файл існує перед кожним тестом
        Assert.assertTrue(new File(VALID_PATH).exists(), "Test file should exist.");
    }

    @Test(groups = {"functional"})
    public void parseDocumentShouldReturnCorrectNumberOfCandies() {
        List<Candy> candies = domParser.parseDocument(VALID_PATH);
        Assert.assertEquals(candies.size(), 3, "Number of candies parsed does not match expected.");
    }

    @Test(groups = {"functional", "property"})
    public void parseDocumentShouldContainSpecificCandyProperties() {
        List<Candy> candies = domParser.parseDocument(VALID_PATH);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(candies.get(0).getName(), "Candy Name", "Candy name does not match expected.");
        softAssert.assertEquals(candies.get(0).getIngredients().size(), 3, "Ingredients map size does not match expected.");
        softAssert.assertEquals(candies.get(0).getValue().size(), 3, "Value map size does not match expected.");
        softAssert.assertEquals(candies.get(0).getProduction(), "production", "Production does not match expected.");
        softAssert.assertAll();
    }

    @Test(groups = {"functional"})
    public void parseDocumentShouldCorrectlyParseProductionDate() {
        List<Candy> candies = domParser.parseDocument(VALID_PATH);
        for (Candy candy : candies) {
            Assert.assertNotNull(candy.getProductionDate(), "Production date should not be null.");
        }
    }

    @Test(groups = {"exception"}, expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = ".*Invalid file.*")
    public void parseDocumentThrowsExceptionForInvalidFile() {
        domParser.parseDocument(INVALID_PATH);
    }

    @DataProvider(name = "validFilePathProvider")
    public Object[][] validFilePathProvider() {
        return new Object[][]{{VALID_PATH}};
    }

    @Test(groups = {"functional"}, dataProvider = "validFilePathProvider")
    public void parseDocumentWithDifferentInputs(String filePath) {
        List<Candy> candies = domParser.parseDocument(filePath);
        Assert.assertFalse(candies.isEmpty(), "Candies list should not be empty.");
    }

    @Test(groups = {"property"})
    public void candyValuesAreCorrect() {
        List<Candy> candies = domParser.parseDocument(VALID_PATH);
        for (Candy candy : candies) {
            Assert.assertTrue(candy.getEnergy() > 0, "Energy should be greater than 0.");
        }
    }

    @Test(groups = {"property"})
    public void candyIdsShouldBeUnique() {
        List<Candy> candies = domParser.parseDocument(VALID_PATH);
        long uniqueIds = candies.stream().map(Candy::getId).distinct().count();
        Assert.assertEquals(uniqueIds, candies.size(), "Candy IDs should be unique.");
    }
}
