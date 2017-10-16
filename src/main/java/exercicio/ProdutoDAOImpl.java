package exercicio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

public class ProdutoDAOImpl implements ProdutoDAO
{	
	public Long inclui(Produto umProduto)
	{	try
		{	EntityManager em = JPAUtil.getEntityManager();

			System.out.println(umProduto.getCategoria());
			em.persist(umProduto);
			return umProduto.getId();
		} 
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void altera(Produto umProduto) 
		throws ObjetoNaoEncontradoException 
	{	try
		{	EntityManager em = JPAUtil.getEntityManager();

			Produto produto = em.find(Produto.class, umProduto.getId(), LockModeType.PESSIMISTIC_WRITE);
			
			if(produto == null)
			{	throw new ObjetoNaoEncontradoException();
			}
		
			em.merge(umProduto);
		}
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void exclui(Long id)
		throws ObjetoNaoEncontradoException 
	{	try
		{	EntityManager em = JPAUtil.getEntityManager();

			Query query = em.createQuery("delete from exercicio.Produto p where p.id = :id");
			query.setParameter("id", id);

			int result = query.executeUpdate();
		}
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Produto recuperaUmProduto(Long id)
		throws ObjetoNaoEncontradoException 
	{	try
		{	EntityManager em = JPAUtil.getEntityManager();

			Produto umProduto = (Produto)em
				.find(Produto.class, id);
			
			if (umProduto == null)
			{	throw new ObjetoNaoEncontradoException();
			}

			return umProduto;
		} 
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Produto recuperaUmProdutoComLock(long id) 
		throws ObjetoNaoEncontradoException 
	{	try
		{	EntityManager em = JPAUtil.getEntityManager();

			Produto umProduto = em.find(Produto.class, id, LockModeType.PESSIMISTIC_WRITE);

			if (umProduto == null)
			{	throw new ObjetoNaoEncontradoException();
			}

			return umProduto;
		} 
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}


	public List<Produto> recuperaProdutosPorCategoria(Long categoriaId) throws ObjetoNaoEncontradoException {
		try
		{
			EntityManager em = JPAUtil.getEntityManager();

			Query query = em.createQuery("select p from exercicio.Produto p where p.categoria.id = :oNome");
			query.setParameter("oNome", categoriaId);

			List<Produto> list = query.getResultList();
			if(list.isEmpty()){
				throw new ObjetoNaoEncontradoException();
			}
			return list;
		}
		catch(RuntimeException e)
		{
			throw new InfraestruturaException(e);
		}
	}


	
	@SuppressWarnings("unchecked")
	public List<Produto> recuperaProdutos()
	{	try
		{	EntityManager em = JPAUtil.getEntityManager();

			List<Produto> produtos = em
				.createQuery("select p from exercicio.Produto p")
				.getResultList();

			return produtos;
		} 
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}
}