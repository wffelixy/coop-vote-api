package com.wfsat.coopvoteapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wfsat.coopvoteapi.model.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    Associado findByCpf(String cpf);

    Associado findByEmail(String email);

}
