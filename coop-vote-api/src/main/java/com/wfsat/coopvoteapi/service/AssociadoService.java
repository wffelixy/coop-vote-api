package com.wfsat.coopvoteapi.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfsat.coopvoteapi.model.Associado;
import com.wfsat.coopvoteapi.repository.AssociadoRepository;

@Service
public class AssociadoService {
	
	private static final Logger log = LoggerFactory.getLogger(AssociadoService.class);

    private final AssociadoRepository associadoRepository;

    @Autowired
    public AssociadoService(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    public Associado cadastrarAssociado(Associado associado) { 
        return associadoRepository.save(associado);
    }

    public List<Associado> listarAssociados() {
        return associadoRepository.findAll();
    }

    public Associado obterAssociadoPorId(Long id) {
        Optional<Associado> optionalAssociado = associadoRepository.findById(id);
        return optionalAssociado.orElse(null);
    }

    public Associado obterAssociadoPorCpf(String cpf) {
        return associadoRepository.findByCpf(cpf);
    }

    public Associado obterAssociadoPorEmail(String email) {
        return associadoRepository.findByEmail(email);
    }
}
