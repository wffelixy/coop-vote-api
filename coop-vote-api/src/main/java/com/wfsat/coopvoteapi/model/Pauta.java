package com.wfsat.coopvoteapi.model;


import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    
    private boolean sessaoAberta;
    
    private Integer duracaoEmSegundos;
    
    private String resultadoVotacao;
    
    @JsonIgnore
    private Integer tempoRestante;
    
    
    
    @OneToMany(mappedBy = "pauta", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Voto> votos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Voto> getVotos() {
		return votos;
	}

	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}
		
	public boolean isSessaoAberta() {
		return sessaoAberta;
	}

	public void setSessaoAberta(boolean sessaoAberta) {
		this.sessaoAberta = sessaoAberta;
	}

	public Integer getDuracaoEmSegundos() {
		return duracaoEmSegundos;
	}

	public void setDuracaoEmSegundos(Integer duracaoEmSegundos) {
		this.duracaoEmSegundos = duracaoEmSegundos;
	}

	public String getResultadoVotacao() {
		return resultadoVotacao;
	}

	public void setResultadoVotacao(String resultadoVotacao) {
		this.resultadoVotacao = resultadoVotacao;
	}
		
	public Integer getTempoRestante() {
		return tempoRestante;
	}

	public void setTempoRestante(Integer tempoRestante) {
		this.tempoRestante = tempoRestante;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pauta other = (Pauta) obj;
		return Objects.equals(id, other.id);
	}	    
}
