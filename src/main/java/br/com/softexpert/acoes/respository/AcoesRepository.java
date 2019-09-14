package br.com.softexpert.acoes.respository;

import br.com.softexpert.acoes.objects.Acoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcoesRepository extends JpaRepository<Acoes,Long> {
    Acoes findByEmpresa(String empresa);
}
