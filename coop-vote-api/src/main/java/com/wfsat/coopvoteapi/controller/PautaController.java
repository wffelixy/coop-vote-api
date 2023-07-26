package com.wfsat.coopvoteapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wfsat.coopvoteapi.model.Pauta;
import com.wfsat.coopvoteapi.service.PautaService;

import java.util.List;

@RestController
@RequestMapping("/api/pautas")
public class PautaController {

    private final PautaService pautaService;

    @Autowired
    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<Pauta> cadastrarPauta(@RequestBody Pauta pautaRequest) {
        Pauta pauta = pautaService.cadastrarPauta(pautaRequest);
        return new ResponseEntity<>(pauta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pauta>> listarPautas() {
        List<Pauta> pautas = pautaService.listarPautas();
        return new ResponseEntity<>(pautas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> obterPautaPorId(@PathVariable Long id) {
        Pauta pauta = pautaService.obterPautaPorId(id);
        if (pauta != null) {
            return new ResponseEntity<>(pauta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("contabiliza/{id}")
    public ResponseEntity<Pauta> obterVotosContabilizadosPautaId(@PathVariable Long id) {
        Pauta pauta = pautaService.obterPautaPorId(id);
        if (pauta != null) {
        	pautaService.contabilizarVotos(pauta);
            return new ResponseEntity<>(pauta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

}

