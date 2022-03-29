package com.platzi.platzimarker.persistence.crud;

import com.platzi.platzimarker.persistence.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto, Long> {
    // Query methods
    List<Producto> findByIdCategoriaOrderByNombreAsc(Long idCategoria);

    // Se tienen que llamar igual que los atributos de los datos y los tipos de datos pueden ser sus equivalentes a datos primitivos
    Optional<List<Producto>> findBycantidadStockLessThanAndEstado(int cantidadStock, boolean estado);

    // This is another way to use query methods with JPQL
    @Query(value = "SELECT * FROM productos WHERE id_categoria = ?", nativeQuery = true)
    List<Producto> getProductByCategoria(Long idCategoria);
}
