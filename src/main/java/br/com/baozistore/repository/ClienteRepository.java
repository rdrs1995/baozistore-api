package br.com.baozistore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.baozistore.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
