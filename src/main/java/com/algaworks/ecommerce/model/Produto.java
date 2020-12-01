package com.algaworks.ecommerce.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto {
	
	@EqualsAndHashCode.Include
	@Id
	private Integer id;
	
	private String nome;
	
	private String descricao;
	
	private BigDecimal preco;
}
