package org.rybar.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(staticName = "of")
@Getter
@ToString
public final class MetaKey<T> {
    private final @NotNull String name;
}
