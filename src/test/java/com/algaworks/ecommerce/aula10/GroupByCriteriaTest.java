package com.algaworks.ecommerce.aula10;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Categoria_;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Cliente_;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.PedidoItem;
import com.algaworks.ecommerce.model.PedidoItem_;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.Produto_;

public class GroupByCriteriaTest extends EntityManagerTest {
	
	@Test
    public void agruparResultado03Exercicio() {
//        Total de vendas por cliente
//        String jpql = "select c.nome, sum(ip.precoProduto) from PedidoItem ip " +
//                " join ip.pedido p join p.cliente c " +
//                " group by c.id";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<PedidoItem> root = criteriaQuery.from(PedidoItem.class);
        Join<PedidoItem, Pedido> joinPedido = root.join(PedidoItem_.pedido);
        Join<Pedido, Cliente> joinPedidoCliente = joinPedido.join(Pedido_.cliente);

        criteriaQuery.multiselect(
                joinPedidoCliente.get(Cliente_.nome),
                criteriaBuilder.sum(root.get(PedidoItem_.precoProduto))
        );

        criteriaQuery.groupBy(joinPedidoCliente.get(Cliente_.id));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        lista.forEach(arr -> System.out.println("Nome cliente: " + arr[0] + ", Sum: " + arr[1]));
    }
	
	@Test
    public void agruparResultado02() {
//        Total de vendas por categoria.
//        String jpql = "select c.nome, sum(ip.precoProduto) from PedidoItem ip " +
//                " join ip.produto pro join pro.categorias c " +
//                " group by c.id";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<PedidoItem> root = criteriaQuery.from(PedidoItem.class);
        Join<PedidoItem, Produto> joinProduto = root.join(PedidoItem_.produto);
        Join<Produto, Categoria> joinProdutoCategoria = joinProduto.join(Produto_.categorias);

        criteriaQuery.multiselect(
                joinProdutoCategoria.get(Categoria_.nome),
                criteriaBuilder.sum(root.get(PedidoItem_.precoProduto))
        );

        criteriaQuery.groupBy(joinProdutoCategoria.get(Categoria_.id));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        lista.forEach(arr -> System.out.println("Nome categoria: " + arr[0] + ", Sum: " + arr[1]));

    }

    @Test
    public void agruparResultado01() {
//        Quantidade de produtos por categoria.
//        String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Categoria> root = criteriaQuery.from(Categoria.class);
        Join<Categoria, Produto> joinProduto = root.join(Categoria_.produtos, JoinType.LEFT);

        criteriaQuery.multiselect(
                root.get(Categoria_.nome),
                criteriaBuilder.count(joinProduto.get(Produto_.id))
        );

        criteriaQuery.groupBy(root.get(Categoria_.id));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        lista.forEach(arr -> System.out.println("Nome: " + arr[0] + ", Count: " + arr[1]));
    }
}
