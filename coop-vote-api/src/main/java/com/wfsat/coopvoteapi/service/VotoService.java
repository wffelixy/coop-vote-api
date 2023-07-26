package com.wfsat.coopvoteapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wfsat.coopvoteapi.model.Associado;
import com.wfsat.coopvoteapi.model.Pauta;
import com.wfsat.coopvoteapi.model.Voto;
import com.wfsat.coopvoteapi.repository.VotoRepository;

@Service
public class VotoService {

    private final VotoRepository votoRepository;
    
    @Autowired
    private PautaService servicePautaRepository;
    
    @Autowired
    private AssociadoService serviceAssociadoRepository;

    @Autowired
    public VotoService(VotoRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    public Voto registrarVoto(String voto, Long pautaId, String cpf) {
    	Pauta pauta = servicePautaRepository.obterPautaPorId(pautaId);
    	
    	if(!pauta.isSessaoAberta()) {
    		return null;
    	}
    	
    	Associado associado = serviceAssociadoRepository.obterAssociadoPorCpf(cpf);
    	
        Voto novoVoto = new Voto();
        novoVoto.setVoto(voto);
        novoVoto.setPauta(pauta);
        novoVoto.setAssociado(associado);
        
        return votoRepository.save(novoVoto);
    }
    
}

