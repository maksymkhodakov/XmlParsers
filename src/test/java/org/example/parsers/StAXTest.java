package org.example.parsers;

import org.example.data.Candy;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Assertions;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.File;
import java.util.List;

class StAXTest {
    private static StAX staxParser;
    private static final String VALID_PATH = "src/test/resources/test.xml";
    private static final String INVALID_PATH = "invalid_path.xml";

    @BeforeAll
    static void setupAll() {
        staxParser = new StAX();
    }

    @BeforeEach
    void setup() {
        Assertions.assertTrue(new File(VALID_PATH).exists(), "Test XML file must exist");
    }

    @Test
    void parseDocumentShouldReturnCorrectNumberOfCandies() {
        List<Candy> candies = staxParser.parseDocument(VALID_PATH);
        assertThat(candies, hasSize(3));
    }

    @Test
    void parseDocumentShouldContainExpectedCandyData() {
        List<Candy> candies = staxParser.parseDocument(VALID_PATH);
        Candy firstCandy = candies.get(0);
        assertAll("Checking first Candy data",
                () -> Assertions.assertEquals(1, firstCandy.getId()),
                () -> Assertions.assertEquals("Candy Name", firstCandy.getName()),
                () -> Assertions.assertEquals("Chewy", firstCandy.getType().toString()),
                () -> assertThat(firstCandy.getIngredients(), is(aMapWithSize(3)))
        );
    }

    @Test
    void parseDocumentShouldHandleException() {
        Assertions.assertThrows(Exception.class, () -> staxParser.parseDocument(INVALID_PATH));
    }

    @ParameterizedTest
    @ValueSource(strings = {VALID_PATH})
    void parseDocumentWithParameterizedPath(String path) {
        List<Candy> candies = staxParser.parseDocument(path);
        assertThat("Candies list should not be empty", candies, is(not(empty())));
    }

    @Test
    void candyIdsShouldBeUnique() {
        List<Candy> candies = staxParser.parseDocument(VALID_PATH);
        long uniqueIdsCount = candies.stream().map(Candy::getId).distinct().count();
        Assertions.assertEquals(candies.size(), uniqueIdsCount, "Candy IDs should be unique");
    }
}

