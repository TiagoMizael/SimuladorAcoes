package br.com.softexpert.acoes.respository;

import br.com.softexpert.acoes.objects.DadosNegociacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosNegociacaoRespository extends JpaRepository<DadosNegociacao,Long> {
    DadosNegociacao findByTipo(String tipo);
}
