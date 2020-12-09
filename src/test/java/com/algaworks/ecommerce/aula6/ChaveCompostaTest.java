package com.algaworks.ecommerce.aula6;

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

public class ChaveCompostaTest extends EntityManagerTest {
	
	@Test
    public void salvarItem() {
        entityManager.getTransaction().begin();

        Cliente cliente = entityManager.find(Cliente.class, 1);
        Produto produto = entityManager.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(produto.getPreco());

        entityManager.persist(pedido);

        entityManager.flush();

        PedidoItem itemPedido = new PedidoItem();
        itemPedido.setId(new PedidoItemId(pedido.getId(), produto.getId()));
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setPrecoProduto(produto.getPreco());
        itemPedido.setQuantidade(1);

        entityManager.persist(itemPedido);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao);
        Assert.assertFalse(pedidoVerificacao.getItens().isEmpty());
    }

    @Test
    public void bucarItem() {
        PedidoItem itemPedido = entityManager.find(
                PedidoItem.class, new PedidoItemId(1, 1));

        Assert.assertNotNull(itemPedido);
    }

}
