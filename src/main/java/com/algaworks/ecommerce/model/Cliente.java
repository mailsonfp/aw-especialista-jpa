package com.algaworks.ecommerce.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Transient;

import com.algaworks.ecommerce.model.enums.SexoCliente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SecondaryTable(name = "cliente_detalhe", pkJoinColumns = @PrimaryKeyJoinColumn(name="cliente_id"))
@Entity
public class Cliente extends EntidadeBaseInteger {

    private String nome;
    
    @Transient
    private String primeiroNome;
    
    @Enumerated(EnumType.STRING)
    private SexoCliente sexo;
    
    @Column(table = "cliente_detalhe")
    @Enumerated(EnumType.STRING)
    private SexoCliente sexoClienteDetalhe;

    @Column(name = "data_nascimento", table = "cliente_detalhe")
    private LocalDate dataNascimento;
    
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
    
    @ElementCollection
    @CollectionTable(name = "cliente_contato", joinColumns = @JoinColumn(name="cliente_id"))
    @MapKeyColumn(name = "tipo")
    @Column(name = "descricao")
    private Map<String, String> contatos;
    
    @PostLoad
    public void configurarPrimeiroNome() {
    	if(nome != null && !nome.isBlank()) {
    		int index = nome.indexOf(" ");
    		if(index > -1) {
    			primeiroNome = nome.substring(0,index);
    		}
    	}
    }
}
