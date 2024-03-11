package org.example.parsers;

import org.example.data.Candy;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.List;

class SAXTest {
    private static SAX saxParser;
    private static final String path = "src/test/resources/test.xml"; // Оновлений відносний шлях

    @BeforeAll
    static void setupAll() {
        saxParser = new SAX();
    }

    @BeforeEach
    void setup() {
        // Перед кожним тестом переконуємось, що файл існує
        Assumptions.assumeTrue(new File(path).exists());
    }

    @Test
    void parseDocumentShouldReturnCorrectNumberOfCandies() {
        saxParser.parseDocument(path);
        List<Candy> candies = saxParser.getCandies();
        assertThat(candies.size(), equalTo(3));
    }

    @Test
    void parseDocumentShouldContainSpecificCandyProperties() {
        saxParser.parseDocument(path);
        List<Candy> candies = saxParser.getCandies();
        assertThat(candies.get(0).getName(), equalTo("Candy Name"));
        assertThat(candies.get(0).getIngredients(), is(aMapWithSize(3)));
        assertThat(candies.get(0).getValue(), is(aMapWithSize(3)));
        assertThat(candies.get(0).getProduction(), equalTo("production"));
    }

    @Test
    void parseDocumentShouldCorrectlyParseProductionDate() {
        saxParser.parseDocument(path);
        List<Candy> candies = saxParser.getCandies();
        candies.forEach(candy -> assertThat(candy.getProductionDate(), notNullValue()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/test.xml"})
    void parseDocumentWithDifferentInputs(String filePath) {
        saxParser.parseDocument(filePath);
        List<Candy> candies = saxParser.getCandies();
        assertThat(candies, is(not(empty())));
    }

    @Test
    void candyValuesAreCorrect() {
        saxParser.parseDocument(path);
        List<Candy> candies = saxParser.getCandies();
        assertThat(candies, everyItem(hasProperty("energy", greaterThan(0))));
    }

    @Test
    void candyIdsShouldBeUnique() {
        saxParser.parseDocument(path);
        List<Candy> candies = saxParser.getCandies();
        assertThat(9L, equalTo((long)candies.size()));
    }
}

