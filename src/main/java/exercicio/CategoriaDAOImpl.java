package exercicio;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

public class CategoriaDAOImpl implements CategoriaDAO
{
    public Long inclui(Categoria umaCategoria)
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

            Query query = em.createQuery("delete from exercicio.Categoria p where p.id = :id");
            query.setParameter("id", id);

            int result = query.executeUpdate();
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

    @Override
    public Categoria recuperaUmaCategoriaPorNome(String nome) throws ObjetoNaoEncontradoException {
        try
        {
            EntityManager em = JPAUtil.getEntityManager();

            Query query = em.createQuery("select p from exercicio.Categoria p where p.nome = :oNome");
            query.setParameter("oNome", nome);

            List list = query.getResultList();
            if(list.isEmpty()){
                throw new ObjetoNaoEncontradoException();
            }

            Categoria umaCategoria = (Categoria)list.get(0);

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
                    .createQuery("select p from exercicio.Categoria p order by p.id asc")
                    .getResultList();

            return categorias;
        }
        catch(RuntimeException e)
        {
            throw new InfraestruturaException(e);
        }
    }
}