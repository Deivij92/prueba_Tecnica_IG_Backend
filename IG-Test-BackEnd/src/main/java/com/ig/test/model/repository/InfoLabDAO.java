package com.ig.test.model.repository;

import com.ig.test.model.InfoLaboralCliente;
import com.ig.test.model.persistence.AbstractDAO_JPA;
import org.springframework.stereotype.Repository;

@Repository
public class InfoLabDAO extends AbstractDAO_JPA<InfoLaboralCliente> {
    public InfoLabDAO() {
        super(InfoLaboralCliente.class);
    }
}
