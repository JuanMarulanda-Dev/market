package com.platzi.platzimarker.persistence.mapper;

import com.platzi.platzimarker.domain.Category;
import com.platzi.platzimarker.persistence.entity.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring") // indicarle a mapstructor que esto va a ser un componenete de spring
public interface CategoryMapper {
    @Mappings({
            @Mapping(source = "id", target = "categoryId"),
            @Mapping(source = "descripcion", target = "category"),
            @Mapping(source = "estado", target = "active"),
    }) // Mapea los datos de categoria al modelo de category
    Category toCategory(Categoria categoria);

    @InheritInverseConfiguration // hace la operacion contraria a los mappings definidos anteriormente
    @Mapping(target = "productos", ignore = true) // le indicamos al mapper que no va a incluir esta propiedad de categoria porque la estamos ignorando
    Categoria toCategoria(Category category);

}
