package com.platzi.platzimarker.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable // Esto permite que sea embebida en otra clase, en este caso ComprasProducto
@Getter
@Setter
public class ComprasProductoPK implements Serializable {

    @Column(name = "id_compra")
    private Long idCompra;

    @Column(name = "id_producto")
    private Long idProducto;
}
