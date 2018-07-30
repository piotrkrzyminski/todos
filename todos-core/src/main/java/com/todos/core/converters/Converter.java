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

    /**
     * Convert model object to data.
     *
     * @param model model to convert.
     * @return converted data object.
     */
    T convert(E model);

    /**
     * Convert data object to model.
     *
     * @param data data to convert.
     * @return converted model object.
     */
    E convert(T data);
}
