package dev.latvian.kubejs.mekanism.util;

public class KJSInterfaceAddon {
    @FunctionalInterface
    public interface KQuadConsumer<T, U, V, W, R> {
        R apply(T t, U u, V v, W w);
    }

    // Custom callback interface for five parameters
    @FunctionalInterface
    public interface KQuintConsumer<T, U, V, W, X, R> {
        R apply(T t, U u, V v, W w, X x);

    }

    // Custom callback interface for three parameters
    @FunctionalInterface
    public interface KTriConsumer<T, U, V, R> {
        R apply(T t, U u, V v);
    }
}
