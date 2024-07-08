package com.ig.test.model.repository;

import com.ig.test.model.Cliente;
import com.ig.test.model.persistence.AbstractDAO_JPA;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteDAO extends AbstractDAO_JPA<Cliente> {
    public ClienteDAO() {
        super(Cliente.class);
    }
}
