package com.algaworks.ecommerce.aula11;

import java.util.List;

import javax.persistence.Query;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.PedidoItem;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.dto.ProdutoDTO;

@SuppressWarnings("unchecked")
public class ConsultaNativaTest extends EntityManagerTest {
	
	//@Test
    public void usarColumnResultRetornarDTO() {
        String sql = "select * from ecm_produto";

        Query query = entityManager.createNativeQuery(sql, "ecm_produto.ProdutoDTO");

        List<ProdutoDTO> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("ProdutoDTO => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }
	
	/*@Test a tabela ecm_produto não está no banco, mas o exemplo serve para ver como preencher uma entidade fazendo uma SQL em uma tabela diferente
	 * usando @SqlResultSetMapping e mapeando os campos da tabela utilizada na SQL x os campos da entidade*/
    public void usarFieldResult() {
        String sql = "select * from ecm_produto";

        Query query = entityManager.createNativeQuery(sql, "ecm_produto.Produto");

        List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }
	
	@Test
    public void usarSQLResultSetMapping02() {
        String sql = "select ip.*, p.* from item_pedido ip join produto p on p.id = ip.produto_id";

        Query query = entityManager.createNativeQuery(sql,
                "item_pedido-produto.ItemPedido-Produto");

        List<Object[]> lista = query.getResultList();

        lista.stream().forEach(arr -> System.out.println(
                String.format("Pedido => ID: %s --- Produto => ID: %s, Nome: %s",
                        ((PedidoItem) arr[0]).getId().getPedidoId(),
                        ((Produto)arr[1]).getId(), ((Produto)arr[1]).getNome())));
    }
    
	@Test
    public void passarParametros() {
        String sql = "select id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto from produto where id >= :id";

        Query query = entityManager.createNativeQuery(sql, Produto.class);
        query.setParameter("id", 2);

        List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }
	
	@Test
    public void executarSQLRetornandoEntidade() {
		/*para preencher o objeto, todos os atributos existentes na classe devem ser retornados pel SQL
		 * Não precisa ser da mesma tabela, ou ter valor, mas todos os atributos da classe tem que retornar
		 * em caso de relacionamentos, apenas os relacionamentos em que a classe de retorno for o owner da relação, em caso de "mappedBy", não é necessário
		 * */
        String sql = "select id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto from produto";

        Query query = entityManager.createNativeQuery(sql, Produto.class);

        List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }
	
	@Test
    public void executarSQL() {
        String sql = "select id, nome from produto";
        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> lista = query.getResultList();

        lista.stream().forEach(arr -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", arr[0], arr[1])));
    }
}
