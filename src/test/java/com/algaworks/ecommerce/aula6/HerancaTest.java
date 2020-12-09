package com.algaworks.ecommerce.aula6;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pagamento;
import com.algaworks.ecommerce.model.PagamentoBoleto;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.enums.StatusPagamento;

public class HerancaTest extends EntityManagerTest {
	
	@Test
    public void salvarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Fernanda Morais");

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(clienteVerificacao.getId());
    }
	
	@Test
    public void buscarPagamentos() {
        @SuppressWarnings("unchecked")
		List<Pagamento> pagamentos = (List<Pagamento>) entityManager.createQuery("select p from Pagamento p").getResultList();

        Assert.assertFalse(pagamentos.isEmpty());
    }

    @Test
    public void incluirPagamentoPedido() {
        Pedido pedido = entityManager.find(Pedido.class, 1);

        PagamentoBoleto pagamentoBoleto = new PagamentoBoleto();
        pagamentoBoleto.setPedido(pedido);
        pagamentoBoleto.setStatus(StatusPagamento.PROCESSANDO);
        pagamentoBoleto.setCodigoBarras("123");

        entityManager.getTransaction().begin();
        entityManager.persist(pagamentoBoleto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao.getPagamento());
    }
}
