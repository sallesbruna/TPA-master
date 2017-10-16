package exercicio;

import org.hibernate.annotations.SourceType;
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


		    JPAUtil.beginTransaction();

            numero = CategoriaDAO.inclui(umaCategoria);

		    JPAUtil.commitTransaction();

            return numero;

        }
        catch(InfraestruturaException e)
        {
            try
            {
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

    public void altera(Categoria umaCategoria) throws CategoriaNaoEncontradaException
    {
        try
        {
            JPAUtil.beginTransaction();
            CategoriaDAO.altera(umaCategoria);

            JPAUtil.commitTransaction();

        }
        catch(ObjetoNaoEncontradoException e)
        {
            JPAUtil.rollbackTransaction();

            throw new CategoriaNaoEncontradaException("Categoria não encontrada");
        }
        catch(InfraestruturaException e)
        {
            try
            {
                JPAUtil.rollbackTransaction();
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

    public void exclui(long numero) throws CategoriaNaoEncontradaException
    {
        try
        {
            JPAUtil.beginTransaction();

            CategoriaDAO.exclui(numero);

            JPAUtil.commitTransaction();
        }
        catch(ObjetoNaoEncontradoException e)
        {
            JPAUtil.rollbackTransaction();

            throw new CategoriaNaoEncontradaException("Categoria não encontrada");
        }
        catch(InfraestruturaException e)
        {
            try
            {
                JPAUtil.rollbackTransaction();
            }
            catch(InfraestruturaException ie)
            {
            }

        throw e;
        }
        finally
        {
            JPAUtil.closeEntityManager();
        }
    }

    public Categoria recuperaUmaCategoria(long numero) throws CategoriaNaoEncontradaException
    {
        try
        {
            Categoria umaCategoria = CategoriaDAO.recuperaUmaCategoria(numero);

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

        Categoria categoria = null;
        String mensagem = null;

        try {
            categoria = recuperaCategoriaPorNome(nome);

                if(categoria == null) {
                    JPAUtil.beginTransaction();
                    Long id = inclui(Categoria.criarCategoria(nome));
                    System.out.println("criou nova categoria");
                    categoria = CategoriaDAO.recuperaUmaCategoria(id);
                    System.out.println("recuperou categoria");
                    JPAUtil.commitTransaction();
                }
            } catch (ObjetoNaoEncontradoException e1) {
                JPAUtil.rollbackTransaction();
            } finally {
                JPAUtil.closeEntityManager();
            }

            mensagem = "Nova categoria adicionada!";


        return mensagem == null
            ? new Result<>(categoria)
            : new Result<>(categoria, mensagem);
    }

    public Categoria recuperaCategoriaPorNome(String nome)
    {
        try
        {
            Categoria umaCategoria = CategoriaDAO.recuperaUmaCategoriaPorNome(nome);
            return umaCategoria;
        }
        catch(ObjetoNaoEncontradoException e)
        {
            System.out.println("categoria nao encontrada.");
            return null;
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