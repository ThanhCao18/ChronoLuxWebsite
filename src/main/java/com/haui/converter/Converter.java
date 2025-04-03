package com.haui.converter;

public interface Converter<D, E> {
    D convertToDTO(E entity);
    E convertToEntity(D dto);
}
