package com.algaworks.ecommerce.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import com.algaworks.ecommerce.model.id.PedidoItemId;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "item_pedido-produto.ItemPedido-Produto",
            entities = {@EntityResult(entityClass = PedidoItem.class),
                    	@EntityResult(entityClass = Produto.class) })
})
@Entity
@Table(name = "pedido_item")
public class PedidoItem {
	
	@EmbeddedId
	private PedidoItemId id;
	
	@MapsId("pedidoId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "pedido_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_pedido_item_pedido"), nullable = false)
	private Pedido pedido;
	
    @MapsId("produtoId") // entre parenteses � o nome do atribuido que representa o id, no caso, � o nome do atributo na PedidoItemId
	@ManyToOne(optional = false)
	@JoinColumn(name = "produto_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_pedido_item_produto"), nullable = false)
	private Produto produto;
	
	@Column(name = "preco_produto")
    private BigDecimal precoProduto;

    private Integer quantidade;
}
