package com.hateoas.apirestHateoas.config;

import com.hateoas.apirestHateoas.controller.CuentaController;
import com.hateoas.apirestHateoas.persistence.entitites.Cuenta;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class HateoasConfig implements RepresentationModelAssembler<Cuenta, EntityModel<Cuenta>> {

    @Override
    public EntityModel<Cuenta> toModel(Cuenta entity) {
        EntityModel<Cuenta> cuentaModel = EntityModel.of(entity);

        cuentaModel.add(linkTo(methodOn(CuentaController.class).getOne(entity.getId())).withSelfRel());
        cuentaModel.add(linkTo(methodOn(CuentaController.class).deposit(entity.getId(), null)).withRel("deposits"));
        cuentaModel.add(linkTo(methodOn(CuentaController.class).retirar(entity.getId(), null)).withRel("withdrawls"));
        cuentaModel.add(linkTo(methodOn(CuentaController.class).getAll()).withRel(IanaLinkRelations.COLLECTION));

        return cuentaModel;
    }
}
