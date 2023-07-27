package com.wfsat.coopvoteapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wfsat.coopvoteapi.exception.PautaNotFoundException;
import com.wfsat.coopvoteapi.model.Pauta;
import com.wfsat.coopvoteapi.model.Voto;
import com.wfsat.coopvoteapi.repository.AssociadoRepository;
import com.wfsat.coopvoteapi.repository.PautaRepository;

@Service
@EnableScheduling
public class PautaService {

	private List<Pauta> pautasEmMemoria = new ArrayList<>();

	private final PautaRepository pautaRepository;
	
	@Autowired
	private  AssociadoRepository associadoRepository;

	@Autowired
	public PautaService(PautaRepository pautaRepository) {
		this.pautaRepository = pautaRepository;
	}

	public Pauta cadastrarPauta(Pauta pautaRequest) {
		if (pautaRequest.getDuracaoEmSegundos() == null) {
			pautaRequest.setDuracaoEmSegundos(60);
		}

		return pautaRepository.save(pautaRequest);
	}

	public List<Pauta> listarPautas() {
		return pautaRepository.findAll();
	}

	public Pauta obterPautaPorId(Long id) {
		Optional<Pauta> optionalPauta = pautaRepository.findById(id);
		return optionalPauta.orElse(null);
	}

	// Método para abrir sessão de votação por um tempo determinado (em segundos)
	public void abrirSessaoVotacao(Long pautaId, Integer duracaoSessao, Long AssociadoId) throws Exception {
		Optional<Pauta> pauta = pautaRepository.findById(pautaId);

		if (!pauta.isPresent()) {
			throw new Exception("Pauta não encontrada!");
		}
		
		// Verificar se o associado está cadastrado
	    if (!associadoRepository.existsById(AssociadoId)) {
	        throw new Exception("Associado não cadastrado!");
	    }

		if (pauta.get().getDuracaoEmSegundos() > 0) {
			pauta.get().setDuracaoEmSegundos(duracaoSessao);
			pauta.get().setSessaoAberta(true);
			pauta.get().setTempoRestante(pauta.get().getDuracaoEmSegundos());
			pautaRepository.save(pauta.get());

			pautasEmMemoria.add(pauta.get());
		}
	}

	// Método agendado para fechar automaticamente as sessões de votação após o
	// tempo determinado
	@Scheduled(fixedRate = 1000) // Executa a cada segundo
	public void fecharSessoesVotacao() {

		for (Pauta pauta : pautasEmMemoria) {
			if (pauta.isSessaoAberta()) {
				int duracaoEmSegundos = pauta.getTempoRestante();
				if (duracaoEmSegundos > 0) {
					duracaoEmSegundos--;
					if (duracaoEmSegundos == 0) {

						String resultado = pauta.getResultadoVotacao();
						pauta.setResultadoVotacao(resultado);
						pauta.setSessaoAberta(false);
						pautaRepository.save(pauta);
					}

					pauta.setTempoRestante(duracaoEmSegundos);
					System.out.println("Tempo restante da" + pauta.getTitulo() + " = " + pauta.getTempoRestante());
				}
			}

		}
	}

	// Contabiliza os votos de uma Pauta
	public void contabilizarVotos(Pauta pauta) throws Exception {

		if (pauta.isSessaoAberta()) {
			throw new PautaNotFoundException(
					"Não foi possível contabilizar, pois a sessão para votaão ainda está aberta!");
		}

		if (pauta != null) {
			int votosSim = 0;
			int votosNao = 0;

			List<Voto> votos = pauta.getVotos();
			for (Voto voto : votos) {
				if ("Sim".equalsIgnoreCase(voto.getVoto())) {
					votosSim++;
				} else if ("Não".equalsIgnoreCase(voto.getVoto())) {
					votosNao++;
				}
			}
			int totalVotos = votosSim + votosNao;
			String resultadoVotacao = "[" + votosSim + "]" + "votos = [SIM]. " 
                    +"[" + votosNao + "]" + "votos = [Não]. "
                    +"[" + totalVotos + "]" + "totais. " + "  Resultado: ";
			if (votosSim > votosNao) {
				resultadoVotacao += "Aprovado";
			} else if (votosSim < votosNao) {
				resultadoVotacao += "Reprovado";
			} else {
				resultadoVotacao += "Empate";
			}

			pauta.setResultadoVotacao(resultadoVotacao);
			pautaRepository.save(pauta);
		}

	}

}
