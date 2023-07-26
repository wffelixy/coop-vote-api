package com.wfsat.coopvoteapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wfsat.coopvoteapi.model.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long>{

}
