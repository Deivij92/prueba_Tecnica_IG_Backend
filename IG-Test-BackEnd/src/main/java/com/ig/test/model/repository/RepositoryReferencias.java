package com.ig.test.model.repository;

import com.ig.test.model.Cliente;
import com.ig.test.model.ReferenciasClientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryReferencias extends JpaRepository<ReferenciasClientes, Long> {
    List<ReferenciasClientes> findByCliente(Cliente cliente);
    List<ReferenciasClientes> findByClienteId(long cliente);
}
