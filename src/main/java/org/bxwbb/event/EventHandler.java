package org.bxwbb.event;

@FunctionalInterface
public interface EventHandler<T> {
    void invoke(T event);
}
