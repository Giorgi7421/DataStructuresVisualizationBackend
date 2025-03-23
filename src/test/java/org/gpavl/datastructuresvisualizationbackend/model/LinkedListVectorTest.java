package org.gpavl.datastructuresvisualizationbackend.model;

import org.gpavl.datastructuresvisualizationbackend.model.vector.linkedlistvector.LinkedListVector;
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
        for(String element : elementsToAdd) {
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

    private static Stream<List<String>> elementsStream() {
        return Stream.of(
                List.of("1", "2", "3"),
                List.of("4", "7"),
                List.of("11", "55"),
                List.of("23", "455", "1", "65", "4565", "74")
        );
    }
}
