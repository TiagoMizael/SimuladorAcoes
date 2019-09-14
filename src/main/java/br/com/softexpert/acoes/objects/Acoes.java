package br.com.softexpert.acoes.objects;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_Acoes")
public class Acoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String empresa;
    private BigDecimal quantidade;

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
