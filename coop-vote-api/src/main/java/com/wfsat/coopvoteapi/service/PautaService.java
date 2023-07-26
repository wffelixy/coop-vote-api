package com.wfsat.coopvoteapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wfsat.coopvoteapi.model.Pauta;
import com.wfsat.coopvoteapi.model.Voto;
import com.wfsat.coopvoteapi.repository.PautaRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
@EnableScheduling
public class PautaService {

	private List<Pauta> pautasEmMemoria = new ArrayList<>();

	private List<Pauta> pautasEmEspera = new ArrayList<>();

	private Boolean pautasEmExecucao = false;

	private final PautaRepository pautaRepository;

	@Autowired
	public PautaService(PautaRepository pautaRepository) {
		this.pautaRepository = pautaRepository;
		abrirSessaoVotacao();
	}

	public Pauta cadastrarPauta(Pauta pautaRequest) {
		Pauta pauta = new Pauta();
		pautaRequest.setTempoRestante(pautaRequest.getDuracaoEmSegundos());
		pauta = pautaRepository.save(pautaRequest);

		adicionaPautaListaEspera(pauta);

		return pauta;
	}

	@PostConstruct
	public void carregarPautasEmMemoria() {
		pautasEmMemoria = pautaRepository.findAll();
	}

	public List<Pauta> listarPautas() {
		return pautaRepository.findAll();
	}

	public Pauta obterPautaPorId(Long id) {
		Optional<Pauta> optionalPauta = pautaRepository.findById(id);
		return optionalPauta.orElse(null);
	}

	// Método para abrir sessão de votação por um tempo determinado (em segundos)
	public void abrirSessaoVotacao() {
		List<Pauta> pautas = pautaRepository.findAll();

		for (Pauta pauta : pautas) {
			if (pauta.getDuracaoEmSegundos() > 0) {
				pauta.setSessaoAberta(true);
				pauta.setTempoRestante(pauta.getDuracaoEmSegundos());
				pautaRepository.save(pauta);
			}
		}
	}

	// Método agendado para fechar automaticamente as sessões de votação após o
	// tempo determinado
	@Scheduled(fixedRate = 1000) // Executa a cada segundo
	public void fecharSessoesVotacao() {

		if (pautasEmMemoria == null || pautasEmMemoria.isEmpty()) {
			pautasEmMemoria = pautaRepository.findAll();
		}
		pautasEmExecucao = true;
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

			pautasEmExecucao = false;

		}
	}

	public void adicionaPautaListaEspera(Pauta pauta) {
		pautasEmEspera.add(pauta);

		for (Pauta pautaAux : pautasEmEspera) {
			System.out.println("novas pautas em espera: " + " = " + pautaAux.getTitulo());
		}
	}

	@Scheduled(fixedRate = 1000)
	public void abrirSessaoVotacaoNovaPauta() {
		if (!pautasEmExecucao) {
			for (Pauta pauta : pautasEmEspera) {
				pautasEmMemoria.add(pauta);
			}
			pautasEmEspera.clear();

		}
	}

	@Transactional
	public void contabilizarVotos(Pauta pauta) {

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
			String resultadoVotacao = "Votos [SIM] = " + votosSim + " - Votos [NÃO] = " + votosNao
					+ " --- Total de voto = " + totalVotos + ". Resultado: ";
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
