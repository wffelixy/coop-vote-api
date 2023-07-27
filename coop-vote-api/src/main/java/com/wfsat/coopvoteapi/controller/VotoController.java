package com.wfsat.coopvoteapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wfsat.coopvoteapi.model.Voto;
import com.wfsat.coopvoteapi.service.VotoService;

@RestController
@RequestMapping("/api/votos")
public class VotoController {

    private final VotoService votoService;

    @Autowired
    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping
    public ResponseEntity<Voto> registrarVoto(@RequestBody Voto votoRequest) {    	
        Voto voto = votoService.registrarVoto(votoRequest.getVoto(), votoRequest.getPauta().getId(), votoRequest.getAssociado().getCpf());
       
        if(voto == null) {
        	return ResponseEntity.notFound().build();
        }
        
        //Oculta para o associado não ter essa informação no retorno da confirmação do seu voto.
        voto.getPauta().setResultadoVotacao(null);
        
        return new ResponseEntity<>(voto, HttpStatus.CREATED);
    }

}

