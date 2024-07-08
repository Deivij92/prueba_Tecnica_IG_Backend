package com.ig.test.model.repository;

import com.ig.test.model.Cliente;
import com.ig.test.model.ReferenciasClientes;
import com.ig.test.model.persistence.AbstractDAO_JPA;
import org.springframework.stereotype.Repository;

@Repository
public class ReferenciasDAO extends AbstractDAO_JPA<ReferenciasClientes> {
    public ReferenciasDAO() {
        super(ReferenciasClientes.class);
    }
}

