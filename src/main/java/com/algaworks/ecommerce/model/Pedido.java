package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.algaworks.ecommerce.listener.GenericoListener;
import com.algaworks.ecommerce.listener.GerarNotaFiscalListener;
import com.algaworks.ecommerce.model.enums.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@NamedEntityGraphs({
    @NamedEntityGraph(
            name = "Pedido.dadosEssencias",
            attributeNodes = {
                    @NamedAttributeNode("dataCriacao"),
                    @NamedAttributeNode("status"),
                    @NamedAttributeNode("total"),
                    @NamedAttributeNode(
                            value = "cliente",
                            subgraph = "cli"
                    )
            },
            subgraphs = {
                    @NamedSubgraph(
                            name = "cli",
                            attributeNodes = {
                                    @NamedAttributeNode("nome"),
                                    @NamedAttributeNode("cpf")
                            }
                    )
            }
    )
})
@Getter
@Setter
@Entity
@EntityListeners({ GerarNotaFiscalListener.class, GenericoListener.class })
public class Pedido extends EntidadeBaseInteger {
    
    @ManyToOne(optional = false /*optional define se será realizado um inner join ou left outer join*/, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
    private Cliente cliente;
    
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;
    
    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;
    
    @OneToOne(mappedBy = "pedido")
    private NotaFiscal notaFiscal;

    private BigDecimal total;
    
    //CascadeType.REMOVAL -> quando remove os pedidos, remove o item
  	//cascade = CascadeType.PERSIST, orphanRemoval = true também excluir os itens quando um pedido é excluido
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PedidoItem> itens;
    
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    
    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;
    
    @Embedded
    private EnderecoEntregaPedido enderecoEntrega;
    
    public boolean isPago() {
    	return StatusPedido.PAGO.equals(status);
    }
    //  @PrePersist o mesmo método pode ter mais de uma anotação, porém, não pode repetir anotações em mais de um método
    //  @PreUpdate
    public void calcularTotal() {
        if (itens != null) {
            total = itens.stream().map(PedidoItem::getPrecoProduto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    @PrePersist
    public void aoPersistir() {
        dataCriacao = LocalDateTime.now();
        calcularTotal();
    }

    @PreUpdate
    public void aoAtualizar() {
        dataUltimaAtualizacao = LocalDateTime.now();
        calcularTotal();
    }

    @PostPersist
    public void aposPersistir() {
        System.out.println("Após persistir Pedido.");
    }

    @PostUpdate
    public void aposAtualizar() {
        System.out.println("Após atualizar Pedido.");
    }

    @PreRemove
    public void aoRemover() {
        System.out.println("Antes de remover Pedido.");
    }

    @PostRemove
    public void aposRemover() {
        System.out.println("Após remover Pedido.");
    }

    @PostLoad
    public void aoCarregar() {
        System.out.println("Após carregar o Pedido.");
    }
}