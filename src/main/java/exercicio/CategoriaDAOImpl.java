package exercicio;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

public class CategoriaDAOImpl implements CategoriaDAO
{
    public long inclui(Categoria umaCategoria)
    {	try
        {
            EntityManager em = JPAUtil.getEntityManager();
            em.persist(umaCategoria);
            return umaCategoria.getId();
        }
        catch(RuntimeException e)
        {
            throw new InfraestruturaException(e);
        }
    }

    public void altera(Categoria umaCategoria) throws ObjetoNaoEncontradoException
    {
        try
        {
            EntityManager em = JPAUtil.getEntityManager();

            Categoria Categoria = em.find(Categoria.class, umaCategoria.getId(), LockModeType.PESSIMISTIC_WRITE);

            if(Categoria == null)
            {
                throw new ObjetoNaoEncontradoException();
            }

            em.merge(umaCategoria);
        }
        catch(RuntimeException e)
        {
            throw new InfraestruturaException(e);
        }
    }

    public void exclui(Long id) throws ObjetoNaoEncontradoException
    {
        try
        {
            EntityManager em = JPAUtil.getEntityManager();

            Categoria Categoria = em.find(Categoria.class, id, LockModeType.PESSIMISTIC_WRITE);

            if(Categoria == null)
            {
                throw new ObjetoNaoEncontradoException();
            }

            em.remove(Categoria);
        }
        catch(RuntimeException e)
        {
            throw new InfraestruturaException(e);
        }
    }

    @Override
    public Categoria recuperaUmaCategoria(Long id) throws ObjetoNaoEncontradoException {
        try
        {
            EntityManager em = JPAUtil.getEntityManager();

            Categoria umaCategoria = (Categoria)em.find(Categoria.class, id);

            if (umaCategoria == null)
            {	throw new ObjetoNaoEncontradoException();
            }

            return umaCategoria;
        }
        catch(RuntimeException e)
        {
            throw new InfraestruturaException(e);
        }
    }

    public Categoria recuperaUmLanceComLock(long id) throws ObjetoNaoEncontradoException
    {
        try
        {
            EntityManager em = JPAUtil.getEntityManager();

            Categoria umaCategoria = em.find(Categoria.class, id, LockModeType.PESSIMISTIC_WRITE);

            if (umaCategoria == null)
            {
                throw new ObjetoNaoEncontradoException();
            }

            return umaCategoria;
        }
        catch(RuntimeException e)
        {
            throw new InfraestruturaException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Categoria> recuperaCategorias()
    {
        try
        {
            EntityManager em = JPAUtil.getEntityManager();

            List<Categoria> categorias = em
                    .createQuery("select p from CATEGORIA p order by p.id asc")
                    .getResultList();

            return categorias;
        }
        catch(RuntimeException e)
        {
            throw new InfraestruturaException(e);
        }
    }
}