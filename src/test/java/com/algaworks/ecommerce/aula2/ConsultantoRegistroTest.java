package com.algaworks.ecommerce.aula2;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class ConsultantoRegistroTest extends EntityManagerTest{
	
	@Test
	public void buscarPorId() {
		Produto produto = entityManager.find(Produto.class, 1);
		
		Assert.assertNotNull(produto);
		Assert.assertEquals("Kindle", produto.getNome());
	}
	
	@Test
    public void atualizarAReferencia() {
        Produto produto = entityManager.find(Produto.class, 1);
        produto.setNome("Microfone Samson");
        
        // --- busca novamente as informações no banco
        entityManager.refresh(produto);

        Assert.assertEquals("Kindle", produto.getNome());
    }
}
