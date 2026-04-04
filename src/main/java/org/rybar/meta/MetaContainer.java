package org.rybar.meta;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MetaContainer {
    private final @NotNull Map<MetaKey<?>, Object> data;

    public static MetaContainer concurrent() {
        return new MetaContainer(new ConcurrentHashMap<>());
    }

    public static MetaContainer identity() {
        return new MetaContainer(new IdentityHashMap<>());
    }

    public static MetaContainer custom(final @NotNull Map<MetaKey<?>, Object> map) {
        return new MetaContainer(map);
    }

    public <T> @NotNull MetaContainer meta(final @NotNull MetaKey<T> key, @Nullable T value) {
        if (value == null) {
            data.remove(key);
        } else {
            data.put(key, value);
        }

        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> @NotNull Optional<T> meta(final @NotNull MetaKey<T> key) {
        return Optional.ofNullable((T) data.get(key));
    }

    @SuppressWarnings("unchecked")
    public <T> T metaOr(final @NotNull MetaKey<T> key, final @NotNull T fallback) {
        Object value = data.get(key);
        return value != null ? (T) value : fallback;
    }

    @SuppressWarnings("unchecked")
    public <T> T computeIfAbsent(final @NotNull MetaKey<T> key, final @NotNull Supplier<T> provider) {
        return (T) data.computeIfAbsent(key, k -> provider.get());
    }

    public boolean has(final @NotNull MetaKey<?> key) {
        return data.containsKey(key);
    }

    public MetaContainer remove(final @NotNull MetaKey<?> key) {
        data.remove(key);
        return this;
    }

    public MetaContainer clear() {
        data.clear();
        return this;
    }
}
