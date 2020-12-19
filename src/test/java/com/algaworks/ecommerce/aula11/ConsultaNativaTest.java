package com.algaworks.ecommerce.aula11;

import java.util.List;

import javax.persistence.Query;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.PedidoItem;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.dto.CategoriaDTO;
import com.algaworks.ecommerce.model.dto.ProdutoDTO;

@SuppressWarnings("unchecked")
public class ConsultaNativaTest extends EntityManagerTest {

	@Test
    public void mapearConsultaParaDTOEmArquivoExternoExercicio() {
        Query query = entityManager.createNamedQuery("ecm_categoria.listar.dto");

        List<CategoriaDTO> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("CategoriaDTO => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }
	
	@Test
    public void usarAquivoXML() {
        Query query = entityManager.createNamedQuery("ecm_categoria.listar");

        List<Categoria> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Categoria => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }
	
	@Test
    public void usarUmaNamedNativeQuery02() {
        Query query = entityManager.createNamedQuery("ecm_produto.listar");

        List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }
	
	@Test
    public void usarUmaNamedNativeQuery01() {
        Query query = entityManager.createNamedQuery("produto_loja.listar");

        List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }
	
	@Test
    public void usarColumnResultRetornarDTO() {
        String sql = "select * from ecm_produto";

        Query query = entityManager.createNativeQuery(sql, "ecm_produto.ProdutoDTO");

        List<ProdutoDTO> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("ProdutoDTO => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }
	
	/*@Test a tabela ecm_produto não está no banco, mas o exemplo serve para ver como preencher uma entidade fazendo uma SQL em uma tabela diferente
	 * usando @SqlResultSetMapping e mapeando os campos da tabela utilizada na SQL x os campos da entidade*/
	@Test
    public void usarFieldResult() {
        String sql = "select * from ecm_produto";

        Query query = entityManager.createNativeQuery(sql, "ecm_produto.Produto");

        List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }
	
	@Test
    public void usarSQLResultSetMapping02() {
        String sql = "select ip.*, p.* from pedido_item ip inner join produto p on p.id = ip.produto_id";

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
