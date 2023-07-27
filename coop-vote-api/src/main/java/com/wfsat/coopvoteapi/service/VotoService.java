package com.wfsat.coopvoteapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfsat.coopvoteapi.exception.SessaoVotacaoException;
import com.wfsat.coopvoteapi.model.Associado;
import com.wfsat.coopvoteapi.model.Pauta;
import com.wfsat.coopvoteapi.model.Voto;
import com.wfsat.coopvoteapi.repository.VotoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VotoService {
	
	private static final Logger log = LoggerFactory.getLogger(VotoService.class);

    private final VotoRepository votoRepository;
    
    @Autowired
    private PautaService servicePautaRepository;
    
    @Autowired
    private AssociadoService serviceAssociadoRepository;

    @Autowired
    public VotoService(VotoRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    //Método que registra o voto
    public Voto registrarVoto(String voto, Long pautaId, String cpf) {    	
    	log.info("Registrando voto");
    	Pauta pauta = servicePautaRepository.obterPautaPorId(pautaId);
    	
    	log.info("Verificando se a sessão está aberta para votação...");
    	if(!pauta.isSessaoAberta()) {
    		log.info("Sessão encerrada!");
    		throw new SessaoVotacaoException("Sessão encerrada!");
    	}
    	
    	Associado associado = serviceAssociadoRepository.obterAssociadoPorCpf(cpf);
    	
    	if(associado == null) {
    		throw new SessaoVotacaoException("Associado não cadastrador!");
    	}
    	
    	if (associadoJaVotouNaPauta(associado, pauta)) {
            throw new SessaoVotacaoException("Associado já votou nesta pauta!");
        }
    	
    	log.info("Verificando se o associado está apto votar!");
    	if(associado.getElegivel() == null || !associado.getElegivel().equals("ABLE_TO_VOTE")) {
    		 throw new SessaoVotacaoException("Associado não elegível para votação!");
    	}
    	
        Voto novoVoto = new Voto();
        novoVoto.setVoto(voto);
        novoVoto.setPauta(pauta);
        novoVoto.setAssociado(associado);
        
        log.info("Gravando voto...");
        return votoRepository.save(novoVoto);
    }
    
    //Método paar verificar se o associado já votou na pauta.
    private boolean associadoJaVotouNaPauta(Associado associado, Pauta pauta) {
    	log.info("Verificando se o associado ja votou nessa pauta!");
        List<Voto> votosDoAssociado = votoRepository.findByAssociadoAndPauta(associado, pauta);
        return !votosDoAssociado.isEmpty();
    }
    
}

