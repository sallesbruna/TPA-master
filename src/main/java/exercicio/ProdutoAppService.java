package exercicio;

import java.util.List;

public class ProdutoAppService
{	
	private static ProdutoDAO produtoDAO = FabricaDeDAOs.getDAO(ProdutoDAO.class);
	private CategoriaAppService categoriaAppService;
	public static int semaforo = 0;

	public ProdutoAppService(CategoriaAppService categoriaAppService){
		this.categoriaAppService = categoriaAppService;
	}

	public long inclui(Produto umProduto) 
	{	try
		{	
			// NENHUMA VALIDAÇÃO ESTÁ SENDO REALIZADA AQUI!!!
		
/*==>*/		JPAUtil.beginTransaction();
			

			long numero = produtoDAO.inclui(umProduto);

/*==>*/		JPAUtil.commitTransaction();
			
			return numero;
		} 
		catch(InfraestruturaException e)
		{	try
/*==>*/		{
				JPAUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{				
			}
			
		    throw e;
		}
		finally
/*==>*/ {   
			JPAUtil.closeEntityManager();
		}
	}

	public void altera(Produto umProduto)
		throws ProdutoNaoEncontradoException
	{	try
		{	JPAUtil.beginTransaction();
			produtoDAO.altera(umProduto);

			JPAUtil.commitTransaction();

		} 
		catch(ObjetoNaoEncontradoException e)
		{	
			JPAUtil.rollbackTransaction();

			throw new ProdutoNaoEncontradoException("Produto não encontrado");
		}
		catch(InfraestruturaException e)
		{	try
			{	JPAUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{				
			}

		    throw e;
		}
		finally
		{   JPAUtil.closeEntityManager();
		}
	}
	
	public void altera(Produto umProduto, Categoria categoria)
			throws ProdutoNaoEncontradoException
		{	try
			{	JPAUtil.beginTransaction();
				semaforo++;
				categoriaAppService.inclui(categoria);
				
				produtoDAO.altera(umProduto);
				
				JPAUtil.commitTransaction();
				semaforo--;
			} 
			catch(ObjetoNaoEncontradoException e)
			{	
				JPAUtil.rollbackTransaction();

				throw new ProdutoNaoEncontradoException("Produto não encontrado");
			}
			catch(InfraestruturaException e)
			{	try
				{	JPAUtil.rollbackTransaction();
				}
				catch(InfraestruturaException ie)
				{				
				}

			    throw e;
			}
			finally
			{   if(semaforo == 0)
					JPAUtil.closeEntityManager();
			}
		}
		
	public void exclui(long numero) 
		throws ProdutoNaoEncontradoException
	{	try
		{	JPAUtil.beginTransaction();

			produtoDAO.exclui(numero);

			JPAUtil.commitTransaction();
		} 
		catch(ObjetoNaoEncontradoException e)
		{	
			JPAUtil.rollbackTransaction();

		    throw new ProdutoNaoEncontradoException("Produto não encontrado");
		}
		catch(InfraestruturaException e)
		{	try
			{	JPAUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{				
			}

		    throw e;
		}
		finally
		{   JPAUtil.closeEntityManager();
		}
	}

	public Produto recuperaUmProduto(long numero) 
		throws ProdutoNaoEncontradoException
	{	
		try
		{	Produto umProduto = produtoDAO.recuperaUmProduto(numero);
			
			return umProduto;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new ProdutoNaoEncontradoException("Produto não encontrado");
		}
		finally
		{   JPAUtil.closeEntityManager();
		}
	}

	public List<Produto> recuperaProdutos() 
	{	try
		{	List<Produto> produtos = produtoDAO.recuperaProdutos();

			return produtos;
		} 
		finally
		{   JPAUtil.closeEntityManager();
		}
	}
}