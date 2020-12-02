package com.algaworks.ecommerce.aula3;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.enums.SexoCliente;

public class MapeandoEnumeracoesTest extends EntityManagerTest {
	
	@Test
    public void testarEnum() {
        Cliente cliente = new Cliente();
        cliente.setNome("Diana de Temiscera");
        cliente.setSexo(SexoCliente.FEMININO);

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(clienteVerificacao);
    }
}
