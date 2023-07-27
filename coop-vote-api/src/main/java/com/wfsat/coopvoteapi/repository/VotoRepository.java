package com.wfsat.coopvoteapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wfsat.coopvoteapi.model.Associado;
import com.wfsat.coopvoteapi.model.Pauta;
import com.wfsat.coopvoteapi.model.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long>{

	List<Voto> findByAssociadoAndPauta(Associado associado, Pauta pauta);

}
