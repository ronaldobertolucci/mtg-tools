package br.com.bertolucci.mtgtools.deckbuilder.application.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ExtractSymbolsServiceTest {
    @ParameterizedTest
    @NullAndEmptySource
    void testNullOrEmptyManaCost(String manaCost) {
        Map<Integer, List<String>> map = ExtractSymbolsService.extract(manaCost);
        assertTrue(map.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("providingSingleManaCosts")
    void testCorrectSingleManaCosts(String in, String out) {
        Map<Integer, List<String>> map = ExtractSymbolsService.extract(in);

        assertEquals(1, map.size());
        assertEquals(1, map.get(0).size());
        assertEquals(out, map.get(0).get(0));
    }

    private static Stream<Arguments> providingSingleManaCosts() {
        return Stream.of(
                Arguments.of("{U}", "{U}"),
                Arguments.of("{ U }", "{U}"),
                Arguments.of(" {U} ", "{U}"),
                Arguments.of(" {1} ", "{1}")
        );
    }

    @ParameterizedTest
    @MethodSource("providingCompoundManaCosts")
    void testCorrectCompoundManaCosts(String in, List<String> out) {
        Map<Integer, List<String>> map = ExtractSymbolsService.extract(in);

        assertEquals(1, map.size());
        assertEquals(2, map.get(0).size());
        for (int i = 0; i < map.get(0).size(); i++) {
            assertEquals(out.get(i), map.get(0).get(i));
        }
    }

    private static Stream<Arguments> providingCompoundManaCosts() {
        List<String> out = new ArrayList<>();
        out.add("{2U}");
        out.add("{U}");

        return Stream.of(
                Arguments.of("{2 / U}{U}", out),
                Arguments.of("{ 2 / U }{U}", out),
                Arguments.of(" {2/U} {U}", out)
        );
    }

    @ParameterizedTest
    @MethodSource("providingDoubleManaCosts")
    void testDoubleManaCosts(String in, Map<Integer, List<String>> out) {
        Map<Integer, List<String>> map = ExtractSymbolsService.extract(in);

        assertEquals(map.size(), 2);
        assertEquals(map.get(0).size(), 1);
        assertEquals(map.get(1).size(), 1);
        assertEquals(map.get(0).get(0), out.get(0).get(0));
        assertEquals(map.get(1).get(0), out.get(1).get(0));
    }

    private static Stream<Arguments> providingDoubleManaCosts() {
        List<String> one = new ArrayList<>();
        one.add("{2U}");
        List<String> two = new ArrayList<>();
        two.add("{U}");
        Map<Integer, List<String>> out = new HashMap<>();
        out.put(0, one);
        out.put(1, two);

        return Stream.of(
                Arguments.of("{2 / U} // {U}", out),
                Arguments.of("{ 2 / U } // {U}", out),
                Arguments.of(" {2/U} // {U}", out)
        );
    }
}