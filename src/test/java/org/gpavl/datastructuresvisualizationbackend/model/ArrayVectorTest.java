package org.gpavl.datastructuresvisualizationbackend.model;

import org.gpavl.datastructuresvisualizationbackend.model.vector.ArrayVector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ArrayVectorTest {

    @Test
    public void testNoArgConstructor() {
        ArrayVector arrayVector = new ArrayVector();
        int capacity = arrayVector.getCapacity();
        int count = arrayVector.getCount();
        String[] array = arrayVector.getArray();
        List<MemorySnapshotDto> steps = arrayVector.getMemoryHistory().getMemorySnapshots();

        Assertions.assertTrue(arrayVector.isEmpty());
        Assertions.assertEquals(0, count);
        Assertions.assertTrue(capacity > 0);
        Assertions.assertTrue(array.length > 0);
        Assertions.assertEquals(capacity, array.length);
        Assertions.assertEquals(0, steps.size());
    }

    @Test
    public void testInitialDataConstructor() {
        int amount = 4;
        String value = "test";

        ArrayVector arrayVector = new ArrayVector(amount, value);
        int capacity = arrayVector.getCapacity();
        int count = arrayVector.getCount();
        String[] array = arrayVector.getArray();
        List<MemorySnapshotDto> steps = arrayVector.getMemoryHistory().getMemorySnapshots();

        Assertions.assertFalse(arrayVector.isEmpty());
        Assertions.assertEquals(amount, count);
        Assertions.assertTrue(capacity > 0);
        Assertions.assertTrue(array.length > 0);
        Assertions.assertEquals(capacity, array.length);

        int initialStepCount = 3;
        Assertions.assertEquals(amount + initialStepCount, steps.size());
    }
}
