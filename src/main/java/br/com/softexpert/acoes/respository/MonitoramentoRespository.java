package br.com.softexpert.acoes.respository;

import br.com.softexpert.acoes.objects.Monitoramento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoramentoRespository extends JpaRepository<Monitoramento,Long> {
    Monitoramento findByEmpresa(String empresa);
    Monitoramento findById(long id);

}
