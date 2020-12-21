package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Version;

import com.algaworks.ecommerce.listener.GenericoListener;
import com.algaworks.ecommerce.model.converter.BooleanToSimNaoConverter;
import com.algaworks.ecommerce.model.dto.ProdutoDTO;
import com.sun.istack.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@NamedNativeQueries({
    @NamedNativeQuery(name = "produto_loja.listar",
            query = "select id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto " +
                    " from produto_loja", resultClass = Produto.class),
    @NamedNativeQuery(name = "ecm_produto.listar",
            query = "select * from ecm_produto", resultSetMapping = "ecm_produto.Produto")
})
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "produto_loja.Produto",
            entities = { @EntityResult(entityClass = Produto.class) }),
    @SqlResultSetMapping(name = "ecm_produto.Produto",
            entities = { @EntityResult(entityClass = Produto.class,
                    fields = {
                            @FieldResult(name = "id", column = "prd_id"),
                            @FieldResult(name = "nome", column = "prd_nome"),
                            @FieldResult(name = "descricao", column = "prd_descricao"),
                            @FieldResult(name = "preco", column = "prd_preco"),
                            @FieldResult(name = "foto", column = "prd_foto"),
                            @FieldResult(name = "dataCriacao", column = "prd_data_criacao"),
                            @FieldResult(name = "dataUltimaAtualizacao",column = "prd_data_ultima_atualizacao")
                    }) }),
    @SqlResultSetMapping(name = "ecm_produto.ProdutoDTO",
    classes = {
            @ConstructorResult(targetClass = ProdutoDTO.class,
                    columns = {
                            @ColumnResult(name = "prd_id", type = Integer.class),
                            @ColumnResult(name = "prd_nome", type = String.class)
                    })
    })
})
@NamedQueries({
    @NamedQuery(name = "Produto.listar", query = "select p from Produto p"),
    @NamedQuery(name = "Produto.listarPorCategoria", query = "select p from Produto p where exists (select 1 from Categoria c2 join c2.produtos p2 where p2 = p and c2.id = :categoria)")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@EntityListeners({ GenericoListener.class })
public class Produto {
	
	@Version
	private Integer versao;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@Column(columnDefinition = "varchar(275) not null default 'descricao'")
	private String descricao;
	
	@Column(precision = 19, scale = 2)
	private BigDecimal preco;
	
	@Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;
    
    @Convert(converter = BooleanToSimNaoConverter.class)
    @NotNull
    @Column(length = 3)
    private Boolean ativo = Boolean.FALSE;
	
	@ManyToMany
	@JoinTable(name = "produto_categoria",
				joinColumns = @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_produto_categoria_produto")),
				inverseJoinColumns = @JoinColumn(name = "categoria_id", nullable = false, foreignKey = @ForeignKey(name = "fk_produto_categoria_catergoria")))
	private List<Categoria> categorias;
	
	@OneToOne(mappedBy = "produto")
	private Estoque estoque;
	
	@ElementCollection
	@CollectionTable(name = "produto_tag", joinColumns = @JoinColumn(name = "produto_id"))
	@Column(name = "tag")
	private List<String> tags;
	
	@ElementCollection
    @CollectionTable(name = "produto_atributo",
            joinColumns = @JoinColumn(name = "produto_id"))
    private List<Atributo> atributos;
	
	@Lob
	private byte[] foto;
}
