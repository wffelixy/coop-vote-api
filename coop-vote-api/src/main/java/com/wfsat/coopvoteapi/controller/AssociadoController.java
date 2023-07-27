package com.wfsat.coopvoteapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wfsat.coopvoteapi.exception.AssociadoException;
import com.wfsat.coopvoteapi.model.Associado;
import com.wfsat.coopvoteapi.model.VotoElegivel;
import com.wfsat.coopvoteapi.service.AssociadoService;
@RestController
@RequestMapping("/api/associados")
public class AssociadoController {

    private final AssociadoService associadoService;

    @Autowired
    public AssociadoController(AssociadoService associadoService) {
        this.associadoService = associadoService;
    }

    @Value("${cpf.validation.api.url}")
    private String cpfValidationApiUrl; // Defina a URL da API externa no arquivo application.properties
   
    @PostMapping
    @ResponseBody
    public ResponseEntity<Associado> cadastrarAssociado(@RequestBody Associado associado) {
    	
    	//Seta vazio pois devera setar com o retorno da consulta externa.
    	associado.setElegivel("");
    	
        // Faz a chamada à API externa para validar o CPF
        String cpfValidationUrl = cpfValidationApiUrl + "/" + associado.getCpf();
        RestTemplate restTemplate = new RestTemplate();
       
        VotoElegivel votoElegivel;
        
        try {
        	 ResponseEntity<VotoElegivel> response = restTemplate.getForEntity(cpfValidationUrl, VotoElegivel.class);
             votoElegivel = response.getBody();
		} catch (Exception e) {
			throw new AssociadoException("CPF Invalido");
		}       

        // Verifica se o CPF é válido
        if (votoElegivel != null && "ABLE_TO_VOTE".equals(votoElegivel.getVotoStatus())) {
           
        	// CPF é válido, cadastra o associado        	
        	associado.setElegivel(votoElegivel.getVotoStatus());
            Associado novoAssociado = associadoService.cadastrarAssociado(associado);
            return new ResponseEntity<>(novoAssociado, HttpStatus.CREATED);
        } else {
        	associado.setElegivel("UNABLE_TO_VOTE");
            Associado novoAssociado = associadoService.cadastrarAssociado(associado);
            return new ResponseEntity<>(novoAssociado, HttpStatus.CREATED);
        }
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
