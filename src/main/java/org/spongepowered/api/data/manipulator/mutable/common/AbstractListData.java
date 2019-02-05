/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.api.data.manipulator.mutable.common;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.manipulator.immutable.ImmutableListData;
import org.spongepowered.api.data.manipulator.mutable.ListData;
import org.spongepowered.api.util.CollectionUtils;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.data.value.Value;

import java.util.List;
import java.util.Optional;

/**
 * A common implementation for {@link ListData}s provided by the API.
 *
 * @param <E> The type of element within the list
 * @param <M> The type of {@link DataManipulator}
 * @param <I> The type of {@link ImmutableDataManipulator}
 */
@SuppressWarnings("unchecked")
public abstract class AbstractListData<E, M extends ListData<E, M, I>, I extends ImmutableListData<E, I, M>>
        extends AbstractSingleData<List<E>, M, I> implements ListData<E, M, I> {

    protected AbstractListData(Key<ListValue<E>> usedKey, List<E> value) {
        super(usedKey, CollectionUtils.copyList(value));
    }

    @Override
    protected ListValue.Mutable<E> getValueGetter() {
        return Sponge.getRegistry().getValueFactory().createListValue(this.usedKey, getValue());
    }

    @Override
    public <V> Optional<V> get(Key<? extends Value<V>> key) {
        // we can delegate this since we have a direct value check as this is
        // a Single value.
        return key == this.usedKey ? Optional.of((V) getValue()) : super.get(key);
    }

    @Override
    public boolean supports(Key<?> key) {
        return checkNotNull(key) == this.usedKey;
    }

    // We have to have this abstract to properly override for generics.
    @Override
    public abstract I asImmutable();

    @Override
    protected List<E> getValue() {
        return CollectionUtils.copyList(super.getValue());
    }

    @Override
    protected M setValue(List<E> value) {
        return super.setValue(CollectionUtils.copyList(value));
    }

    @Override
    public ListValue.Mutable<E> getListValue() {
        return getValueGetter();
    }

    @Override
    public List<E> asList() {
        return getValue();
    }
}
