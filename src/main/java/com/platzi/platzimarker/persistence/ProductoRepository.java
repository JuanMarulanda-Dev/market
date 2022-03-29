package com.platzi.platzimarker.persistence;

import com.platzi.platzimarker.domain.Product;
import com.platzi.platzimarker.domain.repository.ProductRepository;
import com.platzi.platzimarker.persistence.crud.ProductoCrudRepository;
import com.platzi.platzimarker.persistence.entity.Producto;
import com.platzi.platzimarker.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Indica a spring que esta clase se encarga de interactuar con la base de datos
public class ProductoRepository implements ProductRepository {
    // solo tenemos que tener una cosa en cuenta cuando vamos a utilizar la inyeccion de dependencias
    // y es que esl componenete que vamos a inyectar debe ser un componenete de spring
    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public List<Product> getAll(){
        return mapper.toProducts((List<Producto>) productoCrudRepository.findAll());
    }

    @Override
    public Optional<List<Product>> getByCategory(Long categoryId) {
        return Optional.of(
                mapper.toProducts(
                        productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId)
                )
        );
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findBycantidadStockLessThanAndEstado(quantity, true);
        return productos.map(prods-> mapper.toProducts(prods));
    }

    @Override
    public Optional<Product> getProduct(Long productId) {
        return productoCrudRepository.findById(productId).map(producto -> mapper.toProduct(producto));
    }

    @Override
    public Product save(Product product) {
        return mapper.toProduct(
                productoCrudRepository.save(
                        mapper.toProducto(product)
                )
        );
    }

    @Override
    public void delete(Long productId) {
        productoCrudRepository.deleteById(productId);
    }

    public List<Producto> getByCategoria(Long idCategoria){
        return productoCrudRepository.findByIdCategoriaOrderByNombreAsc(idCategoria);
    }

    public Optional<List<Producto>> getEscasos(int cantidad){
        return productoCrudRepository.findBycantidadStockLessThanAndEstado(cantidad, true);
    };

    public Optional<Producto> getProducto(Long id){
        return productoCrudRepository.findById(id);
    }

    public Producto save(Producto producto){
        return productoCrudRepository.save(producto);
    }

    /*public void delete(Long idProducto){
        productoCrudRepository.deleteById(idProducto);
    }*/
}
