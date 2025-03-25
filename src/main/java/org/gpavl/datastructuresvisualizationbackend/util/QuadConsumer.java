package org.gpavl.datastructuresvisualizationbackend.util;

@FunctionalInterface
public interface QuadConsumer<T, U, V, R> {
    void accept(T var1, U var2, V var3, R var4);
}
