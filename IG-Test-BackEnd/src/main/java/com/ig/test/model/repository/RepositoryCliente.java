package com.ig.test.model.repository;

import com.ig.test.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositoryCliente extends JpaRepository<Cliente, Long> {
    public Cliente findByNumeroDocumento(String numerodocumento);
    public Cliente findById(long id);
}
