package com.platzi.platzimarker.persistence.crud;

import com.platzi.platzimarker.persistence.entity.Compra;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.List;

public interface CompraCrudRepository extends CrudRepository<Compra, Long> {
    Optional<List<Compra>> findByIdCliente(String idCliente);
}
