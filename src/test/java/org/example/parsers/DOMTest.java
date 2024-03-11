package org.example.parsers;

import org.example.data.Candy;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.util.List;

class DOMTest {
    private static DOM domParser;
    private static final String path = "src/test/resources/test.xml";

    @BeforeAll
    static void setupAll() {
        domParser = new DOM();
    }

    @BeforeEach
    void setup() {
        // Перед кожним тестом переконуємось, що файл існує
        Assumptions.assumeTrue(new File(path).exists());
    }

    @Test
    void parseDocumentShouldReturnCorrectNumberOfCandies() {
        List<Candy> candies = domParser.parseDocument(path);
        assertThat(candies.size(), equalTo(3));
    }

    @Test
    void parseDocumentShouldContainSpecificCandyProperties() {
        List<Candy> candies = domParser.parseDocument(path);
        assertThat(candies.get(0).getName(), equalTo("Candy Name"));
        assertThat(candies.get(0).getIngredients(), is(aMapWithSize(3)));
        assertThat(candies.get(0).getValue(), is(aMapWithSize(3)));
        assertThat(candies.get(0).getProduction(), equalTo("production"));
    }

    @Test
    void parseDocumentShouldCorrectlyParseProductionDate() {
        List<Candy> candies = domParser.parseDocument(path);
        candies.forEach(candy -> {
            assertThat(candy.getProductionDate(), notNullValue());
        });
    }

    @Test
    void parseDocumentThrowsExceptionForInvalidFile() {
        Exception exception = assertThrows(RuntimeException.class, () -> domParser.parseDocument("invalid_path.xml"));
        assertThat(exception.getMessage(), containsString("Invalid file"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/test.xml"})
    void parseDocumentWithDifferentInputs(String filePath) {
        List<Candy> candies = domParser.parseDocument(filePath);
        assertThat(candies, is(not(empty())));
    }

    @Test
    void candyValuesAreCorrect() {
        List<Candy> candies = domParser.parseDocument(path);
        assertThat(candies, everyItem(hasProperty("energy", greaterThan(0))));
    }

    @Test
    void candyIdsShouldBeUnique() {
        List<Candy> candies = domParser.parseDocument(path);
        long uniqueIds = candies.stream().map(Candy::getId).distinct().count();
        assertThat(uniqueIds, equalTo((long)candies.size()));
    }
}
