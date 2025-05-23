package org.gpavl.datastructuresvisualizationbackend.model;

import org.gpavl.datastructuresvisualizationbackend.model.vector.LinkedListVector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class LinkedListVectorTest {

    @Test
    public void testNoArgConstructor() {
        LinkedListVector vector = new LinkedListVector();
        Assertions.assertNull(vector.getStart());
        Assertions.assertNull(vector.getEnd());
        Assertions.assertEquals(0, vector.getCount());
        Assertions.assertEquals(0, vector.size());
        Assertions.assertTrue(vector.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "6, hello",
            "7, world",
            "11, test",
            "122, big data"
    })
    public void testInitialDataConstructor(int amount, String element) {
        LinkedListVector vector = new LinkedListVector(amount, element);
        Assertions.assertNotNull(vector.getStart());
        Assertions.assertNotNull(vector.getEnd());
        Assertions.assertEquals(vector.getEnd().getValue(), element);
        Assertions.assertNull(vector.getEnd().getNextNode());

        Assertions.assertEquals(amount, vector.getCount());
        Assertions.assertEquals(amount, vector.size());
        Assertions.assertFalse(vector.isEmpty());

        Assertions.assertEquals(element, vector.get(5));
    }

    @ParameterizedTest
    @MethodSource("elementsStream")
    public void testAdd(List<String> elementsToAdd) {
        LinkedListVector vector = new LinkedListVector();
        for (String element : elementsToAdd) {
            vector.add(element);
        }

        Assertions.assertNotNull(vector.getStart());
        Assertions.assertNotNull(vector.getEnd());
        Assertions.assertEquals(vector.getEnd().getValue(), elementsToAdd.getLast());
        Assertions.assertNull(vector.getEnd().getNextNode());

        Assertions.assertEquals(elementsToAdd.size(), vector.getCount());
        Assertions.assertEquals(elementsToAdd.size(), vector.size());
        Assertions.assertFalse(vector.isEmpty());

        Assertions.assertEquals(elementsToAdd.get(1), vector.get(1));
    }

    @ParameterizedTest
    @MethodSource("elementsStream")
    public void testGet(List<String> elementsToAdd) {
        LinkedListVector vector = new LinkedListVector();
        for (String element : elementsToAdd) {
            vector.add(element);
        }

        for (int i = 0; i < vector.size(); i++) {
            Assertions.assertEquals(elementsToAdd.get(i), vector.get(i));
        }
    }

    @ParameterizedTest
    @MethodSource("elementsStream")
    public void testSet(List<String> elementsToAdd) {
        LinkedListVector vector = new LinkedListVector();
        for (String element : elementsToAdd) {
            vector.add(element);
        }

        vector.set(0, "0");
        vector.set(1, "one");

        Assertions.assertEquals("0", vector.get(0));
        Assertions.assertEquals("one", vector.get(1));

        for (int i = 2; i < vector.size(); i++) {
            Assertions.assertEquals(elementsToAdd.get(i), vector.get(i));
        }
    }

    @ParameterizedTest
    @MethodSource("elementsStream")
    public void testInsertAt(List<String> elementsToAdd) {
        LinkedListVector vector = new LinkedListVector();
        for (String element : elementsToAdd) {
            vector.add(element);
        }

        vector.insertAt(1, "one");
        vector.insertAt(1, "two");
        vector.insertAt(1, "three");

        Assertions.assertEquals(elementsToAdd.getFirst(), vector.get(0));

        Assertions.assertEquals("three", vector.get(1));
        Assertions.assertEquals("two", vector.get(2));
        Assertions.assertEquals("one", vector.get(3));

        for (int i = 4; i < vector.size(); i++) {
            Assertions.assertEquals(elementsToAdd.get(i - 3), vector.get(i));
        }
    }

    @Test
    public void testRemoveAt() {
        LinkedListVector vector = new LinkedListVector();
        vector.add("1");
        vector.add("2");
        vector.add("3");
        vector.add("4");
        vector.add("5");
        vector.add("6");

        vector.removeAt(1);
        vector.removeAt(4);

        Assertions.assertEquals("1", vector.get(0));
        Assertions.assertEquals("3", vector.get(1));
        Assertions.assertEquals("4", vector.get(2));
        Assertions.assertEquals("5", vector.get(3));
    }

    private static Stream<List<String>> elementsStream() {
        return Stream.of(
                List.of("1", "2", "3", "4", "5", "6"),
                List.of("4", "7", "21", "33", "14", "543"),
                List.of("11", "55", "2", "0", "1234", "8"),
                List.of("23", "455", "1", "65", "4565", "74")
        );
    }
}
