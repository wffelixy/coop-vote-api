package com.wfsat.validacpf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wfsat.validacpf.model.VotoElegivel;
import com.wfsat.validacpf.repository.CPFValidationService;

@RestController
@RequestMapping("/api/valida-cpf")
public class CPFController {

    private final CPFValidationService cpfValidationService;

    @Autowired
    public CPFController(CPFValidationService cpfValidationService) {
        this.cpfValidationService = cpfValidationService;
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<VotoElegivel> isValidCPF(@PathVariable String cpf) {
        boolean isValid = cpfValidationService.isValidCPF(cpf);
        if (!isValid) {
            return ResponseEntity.notFound().build();
        }
        
        String votingStatus = "ABLE_TO_VOTE"; // O usuário pode votar
        VotoElegivel result = new VotoElegivel(true, votingStatus);
        
        return ResponseEntity.ok(result);
    }
}
