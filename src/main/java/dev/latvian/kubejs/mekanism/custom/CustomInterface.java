package dev.latvian.kubejs.mekanism.custom;

public class CustomInterface {
    @FunctionalInterface
    public interface KQuadConsumer<T, U, V, W, R> {
        R apply(T t, U u, V v, W w);
    }

    // 自定义五参数的回调接口
    @FunctionalInterface
    public interface KQuintConsumer<T, U, V, W, X, R> {
        R apply(T t, U u, V v, W w, X x);

    }

    // 自定义三参数的回调接口
    @FunctionalInterface
    public interface KTriConsumer<T, U, V, R> {
        R apply(T t, U u, V v);
    }
}
