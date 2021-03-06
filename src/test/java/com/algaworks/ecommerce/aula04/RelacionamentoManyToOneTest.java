package com.algaworks.ecommerce.aula04;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.PedidoItem;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.enums.StatusPedido;
import com.algaworks.ecommerce.model.id.PedidoItemId;

public class RelacionamentoManyToOneTest extends EntityManagerTest {
	
	@Test
    public void verificarRelacionamento() {
        Cliente cliente = entityManager.find(Cliente.class, 1);

        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setTotal(BigDecimal.TEN);

        pedido.setCliente(cliente);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao.getCliente());
    }
	
	@Test
    public void verificarRelacionamentoItemPedido() {
		entityManager.getTransaction().begin();
		
        Cliente cliente = entityManager.find(Cliente.class, 1);
        Produto produto = entityManager.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setTotal(BigDecimal.TEN);
        pedido.setCliente(cliente);

        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setId(new PedidoItemId());
        pedidoItem.setPrecoProduto(produto.getPreco());
        pedidoItem.setQuantidade(1);
        pedidoItem.setPedido(pedido);
        pedidoItem.setProduto(produto);
        
        entityManager.persist(pedido);
        entityManager.persist(pedidoItem);
        entityManager.getTransaction().commit();

        entityManager.clear();

        PedidoItem pedidoItemVerificacao = entityManager.find(PedidoItem.class, pedidoItem.getId());
        Assert.assertNotNull(pedidoItemVerificacao.getPedido());
        Assert.assertNotNull(pedidoItemVerificacao.getProduto());
    }
}
