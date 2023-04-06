package com.hateoas.apirestHateoas.controller;

import com.hateoas.apirestHateoas.config.HateoasConfig;
import com.hateoas.apirestHateoas.dtos.Cantidad;
import com.hateoas.apirestHateoas.persistence.entitites.Cuenta;
import com.hateoas.apirestHateoas.services.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CuentaController {
    private final CuentaService cuentaService;
    private final HateoasConfig hateoasConfig;


    @PostMapping
    public HttpEntity<EntityModel<Cuenta>> add(@RequestBody Cuenta cuenta) {
        Cuenta savedAccount = cuentaService.save(cuenta);
        EntityModel<Cuenta> model = hateoasConfig.toModel(savedAccount);

        return ResponseEntity.created(linkTo(methodOn(CuentaController.class).getOne(savedAccount.getId())).toUri()).body(model);
    }

    @PutMapping
    public HttpEntity<EntityModel<Cuenta>> replace(@RequestBody Cuenta cuenta) {
        Cuenta savedAccount = cuentaService.save(cuenta);
        return new ResponseEntity<>(hateoasConfig.toModel(savedAccount), HttpStatus.OK);
    }

    @PatchMapping("/{id}/depositar")
    public HttpEntity<EntityModel<Cuenta>> deposit(@PathVariable("id") Long id, @RequestBody Cantidad cantidad) {
        Cuenta updatedAccount = cuentaService.depositar(cantidad.getCantidad(), id);
        return new ResponseEntity<>(hateoasConfig.toModel(updatedAccount), HttpStatus.OK);
    }

    @PatchMapping("/{id}/retirar")
    public HttpEntity<EntityModel<Cuenta>> retirar(@PathVariable("id") Long id, @RequestBody Cantidad cantidad) {
        Cuenta updatedAccount = cuentaService.retirar(cantidad.getCantidad(), id);
        return new ResponseEntity<>(hateoasConfig.toModel(updatedAccount), HttpStatus.OK);
    }

    @GetMapping
    public CollectionModel<EntityModel<Cuenta>> getAll() {
        List<EntityModel<Cuenta>> cuentas = cuentaService.listAll().stream()
                .map(hateoasConfig::toModel).collect(Collectors.toList());
        return CollectionModel.of(cuentas,
                linkTo(methodOn(CuentaController.class).getAll()).withRel("cuentas")
        );
    }

    @GetMapping("/{id}")
    public EntityModel<Cuenta> getOne(@PathVariable("id") Long id) {
        Cuenta account = cuentaService.get(id);
        return hateoasConfig.toModel(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        cuentaService.delete(id);
        return new ResponseEntity<>("Cuenta Eliminada !! ", HttpStatus.OK);
    }


}
