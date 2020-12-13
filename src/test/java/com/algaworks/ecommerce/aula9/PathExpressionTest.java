package com.algaworks.ecommerce.aula9;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;

public class PathExpressionTest extends EntityManagerTest {
	
	@Test
    public void buscarPedidosComProdutoEspecifico() {
        String jpql = "select p from Pedido p join p.itens i where i.id.produtoId = 1";
//        String jpql = "select p from Pedido p join p.itens i join i.produto pro where pro.id = 1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.size() == 2);
    }

	
	@Test
    public void usarPathExpressions() {
        String jpql = "select p.cliente.nome from Pedido p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
	
}
