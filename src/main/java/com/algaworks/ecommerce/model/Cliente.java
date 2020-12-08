package com.algaworks.ecommerce.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.algaworks.ecommerce.model.enums.SexoCliente;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cliente {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    
    @Transient
    private String primeiroNome;
    
    @Enumerated(EnumType.STRING)
    private SexoCliente sexo;
    
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
    
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
