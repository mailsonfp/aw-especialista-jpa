package com.algaworks.ecommerce.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    
    @ManyToOne
    @JoinColumn(name = "categoria_pai_id")
    private Categoria categoriaPai;
    
    @OneToMany(mappedBy = "categoriaPai")
    private List<Categoria> categoriasFilhas;
    
    @ManyToMany(mappedBy = "categorias")
    private List<Produto> produtos;
    
}
