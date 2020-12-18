package com.algaworks.ecommerce.aula10;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Categoria_;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.PedidoItem;
import com.algaworks.ecommerce.model.PedidoItem_;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.Produto_;
import com.algaworks.ecommerce.model.id.PedidoItemId_;

public class SubqueriesCriteriaTest extends EntityManagerTest {
	
	@Test
    public void pesquisarComAllExercicio() {
//        Todos os produtos que sempre foram vendidos pelo mesmo pre�o.
//        String jpql = "select distinct p from PedidoItem ip join ip.produto p where " +
//                " ip.precoProduto = ALL " +
//                " (select precoProduto from PedidoItem where produto = p and id <> ip.id)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<PedidoItem> root = criteriaQuery.from(PedidoItem.class);

        criteriaQuery.select(root.get(PedidoItem_.produto));
		criteriaQuery.distinct(true);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<PedidoItem> subqueryRoot = subquery.from(PedidoItem.class);
        subquery.select(subqueryRoot.get(PedidoItem_.precoProduto));
        subquery.where(
                criteriaBuilder.equal(subqueryRoot.get(PedidoItem_.produto), root.get(PedidoItem_.produto)),
                criteriaBuilder.notEqual(subqueryRoot, root)
        );

        criteriaQuery.where(
                criteriaBuilder.equal(
                        root.get(PedidoItem_.precoProduto), criteriaBuilder.all(subquery))
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }
	
	@Test
    public void pesquisarComAny02() {
//        Todos os produtos que j� foram vendidos por um preco diferente do atual
//        String jpql = "select p from Produto p " +
//                " where p.preco <> ANY (select precoProduto from PedidoItem where produto = p)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<PedidoItem> subqueryRoot = subquery.from(PedidoItem.class);
        subquery.select(subqueryRoot.get(PedidoItem_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(PedidoItem_.produto), root));

        criteriaQuery.where(
                criteriaBuilder.notEqual(
                        root.get(Produto_.preco), criteriaBuilder.any(subquery))
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));

    }

    @Test
    public void pesquisarComAny01() {
//        Todos os produtos que j� foram vendidos, pelo menos, uma vez pelo pre�o atual.
//        String jpql = "select p from Produto p " +
//                " where p.preco = ANY (select precoProduto from PedidoItem where produto = p)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<PedidoItem> subqueryRoot = subquery.from(PedidoItem.class);
        subquery.select(subqueryRoot.get(PedidoItem_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(PedidoItem_.produto), root));

        criteriaQuery.where(
                criteriaBuilder.equal(
                        root.get(Produto_.preco), criteriaBuilder.any(subquery))
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));

    }
    
	@Test
    public void pesquisarComAll02() {
//        Todos os produtos n�o foram vendidos mais depois que encareceram
//        String jpql = "select p from Produto p where " +
//                " p.preco > ALL (select precoProduto from PedidoItem where produto = p)";
//                " and exists (select 1 from PedidoItem where produto = p)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<PedidoItem> subqueryRoot = subquery.from(PedidoItem.class);
        subquery.select(subqueryRoot.get(PedidoItem_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(PedidoItem_.produto), root));

        criteriaQuery.where(
                criteriaBuilder.greaterThan(
                        root.get(Produto_.preco), criteriaBuilder.all(subquery)),
                criteriaBuilder.exists(subquery)
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAll01() {
//        Todos os produtos que SEMPRE foram vendidos pelo preco atual.
//        String jpql = "select p from Produto p where " +
//                " p.preco = ALL (select precoProduto from PedidoItem where produto = p)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<PedidoItem> subqueryRoot = subquery.from(PedidoItem.class);
        subquery.select(subqueryRoot.get(PedidoItem_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(PedidoItem_.produto), root));

        criteriaQuery.where(criteriaBuilder.equal(
                root.get(Produto_.preco), criteriaBuilder.all(subquery)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }
    
	@Test
    public void perquisarComExistsExercicio() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<PedidoItem> subqueryRoot = subquery.from(PedidoItem.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(
                criteriaBuilder.equal(subqueryRoot.get(PedidoItem_.produto), root),
                criteriaBuilder.notEqual(
                        subqueryRoot.get(PedidoItem_.precoProduto), root.get(Produto_.preco))
        );

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComINExercicio() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<PedidoItem> subqueryRoot = subquery.from(PedidoItem.class);
        Join<PedidoItem, Produto> subqueryJoinProduto = subqueryRoot.join(PedidoItem_.produto);
        Join<Produto, Categoria> subqueryJoinProdutoCategoria = subqueryJoinProduto
                .join(Produto_.categorias);
        subquery.select(subqueryRoot.get(PedidoItem_.id).get(PedidoItemId_.pedidoId));
        subquery.where(criteriaBuilder.equal(subqueryJoinProdutoCategoria.get(Categoria_.id), 2));

        criteriaQuery.where(root.get(Pedido_.id).in(subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void perquisarComSubqueryExercicio() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root);

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.count(criteriaBuilder.literal(1)));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(Pedido_.cliente), root));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, 2L));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }
    
	@Test
    public void pesquisarComExists() {
//        Todos os produtos que j� foram vendidos.
//        String jpql = "select p from Produto p where exists " +
//                " (select 1 from PedidoItem ip2 join ip2.produto p2 where p2 = p)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<PedidoItem> subqueryRoot = subquery.from(PedidoItem.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(PedidoItem_.produto), root));

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

	
	@Test
    public void pesquisarComIN() {
//        String jpql = "select p from Pedido p where p.id in " +
//                " (select p2.id from PedidoItem i2 " +
//                "      join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<PedidoItem> subqueryRoot = subquery.from(PedidoItem.class);
        Join<PedidoItem, Pedido> subqueryJoinPedido = subqueryRoot.join(PedidoItem_.pedido);
        Join<PedidoItem, Produto> subqueryJoinProduto = subqueryRoot.join(PedidoItem_.produto);
        subquery.select(subqueryJoinPedido.get(Pedido_.id));
        subquery.where(criteriaBuilder.greaterThan(
                subqueryJoinProduto.get(Produto_.preco), new BigDecimal(100)));

        criteriaQuery.where(root.get(Pedido_.id).in(subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }
	
	@Test
    public void pesquisarSubqueries03() {
//        Bons clientes.
//        String jpql = "select c from Cliente c where " +
//                " 500 < (select sum(p.total) from Pedido p where p.cliente = c)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.sum(subqueryRoot.get(Pedido_.total)));
        subquery.where(criteriaBuilder.equal(
                root, subqueryRoot.get(Pedido_.cliente)));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, new BigDecimal(1300)));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }
	
	@Test
    public void pesquisarSubqueries02() {
//         Todos os pedidos acima da m�dia de vendas
//        String jpql = "select p from Pedido p where " +
//                " p.total > (select avg(total) from Pedido)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.avg(subqueryRoot.get(Pedido_.total)).as(BigDecimal.class));

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Pedido_.total), subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Total: " + obj.getTotal()));
    }

    @Test
    public void pesquisarSubqueries01() {
//         O produto ou os produtos mais caros da base.
//        String jpql = "select p from Produto p where " +
//                " p.preco = (select max(preco) from Produto)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Produto> subqueryRoot = subquery.from(Produto.class);
        subquery.select(criteriaBuilder.max(subqueryRoot.get(Produto_.preco)));

        criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.preco), subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Nome: " + obj.getNome() + ", Pre�o: " + obj.getPreco()));
    }
}
