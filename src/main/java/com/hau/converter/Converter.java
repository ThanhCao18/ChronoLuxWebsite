package com.hau.converter;

public interface Converter<D, E> {
    D convertToDTO(E entity);
    E convertToEntity(D dto);
}
