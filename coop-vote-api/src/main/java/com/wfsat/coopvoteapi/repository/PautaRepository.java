package com.wfsat.coopvoteapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wfsat.coopvoteapi.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long>{

	@Query("SELECT p FROM Pauta p LEFT JOIN FETCH p.votos WHERE p.id = :id")
	Pauta findByIdWithVotos(@Param("id") Long id);
}
