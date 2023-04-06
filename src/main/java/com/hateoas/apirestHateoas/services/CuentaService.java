package com.hateoas.apirestHateoas.services;

import com.hateoas.apirestHateoas.persistence.entitites.Cuenta;
import com.hateoas.apirestHateoas.persistence.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public List<Cuenta> listAll() {
        return cuentaRepository.findAll();
    }

    public Cuenta save(Cuenta cuenta){
        return cuentaRepository.save(cuenta);
    }

    public Cuenta get(Long id) {
        return cuentaRepository.findById(id).get();
    }

    public Cuenta retirar(double amount, Long id) {
        cuentaRepository.retirar(amount, id);
        return cuentaRepository.findById(id).get();
    }

    public Cuenta depositar(double amount, Long id) {
        cuentaRepository.deposito(amount, id);
        return cuentaRepository.findById(id).get();
    }

    public void delete(Long id ){
        cuentaRepository.deleteById(id);
    }

}
