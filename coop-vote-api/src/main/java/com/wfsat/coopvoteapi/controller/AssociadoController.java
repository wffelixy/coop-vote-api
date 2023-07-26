package com.wfsat.coopvoteapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wfsat.coopvoteapi.model.Associado;
import com.wfsat.coopvoteapi.service.AssociadoService;
import com.wfsat.coopvoteapi.service.CPFValidator;

@RestController
@RequestMapping("/api/associados")
public class AssociadoController {

    private final AssociadoService associadoService;

    @Autowired
    public AssociadoController(AssociadoService associadoService) {
        this.associadoService = associadoService;
    }

    @PostMapping
    public ResponseEntity<Associado> cadastrarAssociado(@RequestBody Associado associado) {
    	
    	CPFValidator validaCpf = new CPFValidator();
    	if(!validaCpf.isValidCPF(associado.getCpf())) {
    	   return ResponseEntity.notFound().build();
    	}
    	
    	Associado novoAssociado = associadoService.cadastrarAssociado(associado);        
        return new ResponseEntity<>(novoAssociado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Associado>> listarAssociados() {
        List<Associado> associados = associadoService.listarAssociados();
        return new ResponseEntity<>(associados, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Associado> obterAssociadoPorId(@PathVariable Long id) {
        Associado associado = associadoService.obterAssociadoPorId(id);
        if (associado != null) {
            return new ResponseEntity<>(associado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Associado> obterAssociadoPorCpf(@PathVariable String cpf) {
        Associado associado = associadoService.obterAssociadoPorCpf(cpf);
        if (associado != null) {
            return new ResponseEntity<>(associado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Associado> obterAssociadoPorEmail(@PathVariable String email) {
        Associado associado = associadoService.obterAssociadoPorEmail(email);
        if (associado != null) {
            return new ResponseEntity<>(associado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
