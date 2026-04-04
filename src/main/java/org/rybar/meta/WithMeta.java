package org.rybar.meta;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface WithMeta {
    @NotNull MetaContainer meta();

    default <T> Optional<T> meta(MetaKey<T> key) {
        return meta().meta(key);
    }

    default <T> void meta(MetaKey<T> key, T value) {
        meta().meta(key, value);
    }

    default <T> T metaOr(MetaKey<T> key, T fallback) {
        return meta().metaOr(key, fallback);
    }

    default boolean hasMeta(MetaKey<?> key) {
        return meta().has(key);
    }

    default void removeMeta(MetaKey<?> key) {
        meta().remove(key);
    }
}
