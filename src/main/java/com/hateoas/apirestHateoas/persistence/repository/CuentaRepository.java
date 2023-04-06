package com.hateoas.apirestHateoas.persistence.repository;

import com.hateoas.apirestHateoas.persistence.entitites.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query("UPDATE Cuenta c SET c.balance = c.balance + ?1 WHERE c.id = ?2")
    @Modifying
    @Transactional
    void deposito(double amount, Long id);

    @Query("UPDATE Cuenta c SET c.balance = c.balance - ?1 WHERE c.id = ?2")
    @Modifying
    @Transactional
    void retirar(double amount, Long id);
}
