package br.com.softexpert.acoes.objects;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="TB_DadosNegociacao")
public class DadosNegociacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String data;
    private String empresa;
    private BigDecimal valor;
    private BigDecimal quantidade;
    private String tipo;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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
