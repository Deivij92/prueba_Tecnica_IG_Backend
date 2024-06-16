package com.ig.test.model.repository;

import ch.qos.logback.core.net.server.Client;
import com.ig.test.model.Cliente;
import com.ig.test.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryPrestamo extends JpaRepository<Prestamo, Long> {


    List<Prestamo> findByClienteId(long cliente);
}
