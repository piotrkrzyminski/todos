package com.todos.core.converters;

import com.todos.data.AbstractData;
import com.todos.model.AbstractModel;

/**
 * This is interface for converter that converts model data to dto.
 *
 * @param <E> model item.
 * @param <T> dto item.
 */
public interface Converter<E extends AbstractModel,T extends AbstractData> {

    T convert(E model);
}
