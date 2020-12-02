package com.algaworks.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Categoria {

	/*
     @GeneratedValue(strategy = GenerationType.AUTO) o próprio hibernate deifine a estratégia pra criar o id
	 ****
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq") utiliza sequence nos bancos que suportam e nos outros cria um tabela específica
     @SequenceGenerator(name = "seq", sequenceName = "categoria_chave_primaria") a cada nome diferente um sequence é criado e o valor é gerado conforme nome do sequence
	 ****
     @GeneratedValue(strategy = GenerationType.TABLE, generator = "tabela")
     @TableGenerator(name = "tabela", table = "hibernate_sequences",
      							pkColumnName = "sequence_name",
      							pkColumnValue = "categoria",
      							valueColumnName = "next_val",	
      							initialValue = 0,
      							allocationSize = 50)
	 */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    
    @Column(name = "categoria_pai_id")
    private Integer categoriaPaiId;
}
