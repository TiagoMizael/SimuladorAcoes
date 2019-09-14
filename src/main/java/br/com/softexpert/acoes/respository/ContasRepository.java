package br.com.softexpert.acoes.respository;

import br.com.softexpert.acoes.objects.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContasRepository extends JpaRepository <Conta, Long>{
    Conta findById(long id);

}
