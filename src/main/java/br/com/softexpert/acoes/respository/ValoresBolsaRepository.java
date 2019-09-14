package br.com.softexpert.acoes.respository;

import br.com.softexpert.acoes.objects.ValorBolsa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValoresBolsaRepository extends JpaRepository<ValorBolsa, Long> {
    ValorBolsa findByEmpresa(String empresa);

}
