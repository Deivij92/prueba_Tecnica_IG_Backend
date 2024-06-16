package com.ig.test.model.repository;

import com.ig.test.model.Cliente;
import com.ig.test.model.InfoLaboralCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryInfoLAboral extends JpaRepository<InfoLaboralCliente, Long> {
    Optional<InfoLaboralCliente> findByCliente(Cliente cliente);
    Optional<InfoLaboralCliente> findById(long idCliente);

}
