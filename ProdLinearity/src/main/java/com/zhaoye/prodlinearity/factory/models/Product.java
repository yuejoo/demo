package com.zhaoye.prodlinearity.factory.models;

import org.immutables.value.Value;

@Value.Immutable
public interface Product
{
    static Product of(final String value) {
        return ImmutableProduct.builder().value(value).build();
    }

    String value();
}
