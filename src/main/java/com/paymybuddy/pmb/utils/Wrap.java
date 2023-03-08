package com.paymybuddy.pmb.utils;

public class Wrap<T, V> {
    private final T object;
    private final V value;

    public T unWrap()   {
        return object;
    }

    public V getTag()    {
        return value;
    }

    private Wrap(Wrapper<T, V> wrapper) {
        this.object = wrapper.object;
        this.value = wrapper.value;
    }

    public static class Wrapper<T, V> {
        private T object = null;
        private V value;

        public Wrapper<T, V> put(T object) {
            this.object = object;
            return this;
        }

        public Wrapper<T, V> setTag(V value) {
            this.value = value;
            return this;
        }

        public Wrap<T, V> wrap() {
            return new Wrap<>(this);
        }
    }

}