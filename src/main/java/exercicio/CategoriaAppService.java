package exercicio;

import sun.util.resources.cldr.tg.CalendarData_tg_Cyrl_TJ;

import java.util.Collections;
import java.util.List;

public class CategoriaAppService
{
    private static CategoriaDAO CategoriaDAO = FabricaDeDAOs.getDAO(CategoriaDAO.class);

    public long inclui(Categoria umaCategoria)
    {
        try
        {
            long numero = 0;
            // NENHUMA VALIDAÇÃO ESTÁ SENDO REALIZADA AQUI!!!



		/*==>*/		JPAUtil.beginTransaction();
            ProdutoAppService.semaforo++;

            numero = CategoriaDAO.inclui(umaCategoria);
            if(ProdutoAppService.semaforo == 0)
            {
		/*==>*/			JPAUtil.commitTransaction();
                ProdutoAppService.semaforo--;
            }
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
            if(ProdutoAppService.semaforo == 0)
                JPAUtil.closeEntityManager();
        }

    }

    public void altera(Categoria umaCategoria)
            throws CategoriaNaoEncontradaException
    {	try
    {	JPAUtil.beginTransaction();
        JPAUtil.semaforo++;
        CategoriaDAO.altera(umaCategoria);

        JPAUtil.commitTransaction();

        JPAUtil.semaforo--;
    }
    catch(ObjetoNaoEncontradoException e)
    {
        JPAUtil.rollbackTransaction();

        throw new CategoriaNaoEncontradaException("Categoria não encontrada");
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

    public void exclui(long numero)
            throws CategoriaNaoEncontradaException
    {	try
    {	JPAUtil.beginTransaction();

        CategoriaDAO.exclui(numero);

        JPAUtil.commitTransaction();
    }
    catch(ObjetoNaoEncontradoException e)
    {
        JPAUtil.rollbackTransaction();

        throw new CategoriaNaoEncontradaException("Categoria não encontrada");
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

    public Categoria recuperaUmaCategoria(long numero)
            throws CategoriaNaoEncontradaException
    {
        try
        {	Categoria umaCategoria = CategoriaDAO.recuperaUmaCategoria(numero);

            return umaCategoria;
        }
        catch(ObjetoNaoEncontradoException e)
        {	throw new CategoriaNaoEncontradaException("Categoria não encontrada");
        }
        finally
        {   JPAUtil.closeEntityManager();
        }
    }


    public Result<Categoria> recuperaCategoriaPorNomeOuInsere(String nome){

        Categoria categoria;
        String mensagem = null;

        try {
            categoria = recuperaCategoriaPorNome(nome);
        } catch (CategoriaNaoEncontradaException e) {
            categoria = Categoria.criarCategoria("nome");

            //INCLUI NOVA CATEGORIA SE CATEGORIA COM NOME FORNECIDO NAO EXISTIR
            inclui(categoria);
            //----

            mensagem = "Nova categoria adicionada!";
        }

        return mensagem == null
            ? new Result<>(categoria)
            : new Result<>(categoria, mensagem);
    }

    public Categoria recuperaCategoriaPorNome(String nome) throws CategoriaNaoEncontradaException
    {
        try
        {
            Categoria umaCategoria = CategoriaDAO.recuperaUmaCategoriaPorNome(nome);
            return umaCategoria;
        }
        catch(ObjetoNaoEncontradoException e)
        {
            throw new CategoriaNaoEncontradaException("Categoria não encontrada");
        }
        finally
        {
            JPAUtil.closeEntityManager();
        }
    }

    public List<Categoria> recuperaCategorias(){
        try
        {
            return CategoriaDAO.recuperaCategorias();
        }
        finally
        {
            JPAUtil.closeEntityManager();
            return Collections.emptyList();
        }
    }
}