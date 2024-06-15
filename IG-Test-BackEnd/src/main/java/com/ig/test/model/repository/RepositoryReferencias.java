package com.ig.test.model.repository;

import com.ig.test.model.ReferenciasClientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryReferencias extends JpaRepository<ReferenciasClientes, Long> {
}
