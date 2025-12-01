package com.remediar.back_remediar.model.mapper;

public interface GenericMapper<D, E> {
    E toEntity(D dto);
    D toDto(E entity);
}
