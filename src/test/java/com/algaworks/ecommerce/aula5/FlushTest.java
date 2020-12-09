package com.algaworks.ecommerce.aula5;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.enums.StatusPedido;

public class FlushTest extends EntityManagerTest {
	
	@Test(expected = Exception.class)
    public void chamarFlush() {
        try {
            entityManager.getTransaction().begin();

            Pedido pedido = entityManager.find(Pedido.class, 1);
            pedido.setStatus(StatusPedido.PAGO);

            entityManager.flush();

            if (pedido.getPagamento() == null) {
                throw new RuntimeException("Pedido ainda n�o foi pago.");
            }

//            Uma consulta obriga o JPA a sincronizar o que ele tem na mem�ria (sem usar o flush explicitamente).
//            Pedido pedidoPago = entityManager
//                    .createQuery("select p from Pedido p where p.id = 1", Pedido.class)
//                    .getSingleResult();
//            Assert.assertEquals(pedido.getStatus(), pedidoPago.getStatus());

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
}