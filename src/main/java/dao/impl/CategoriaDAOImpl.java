package dao.impl;

import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import dao.CategoriaDAO;
import modelo.Categoria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

public abstract class CategoriaDAOImpl extends JPADaoGenerico<Categoria, Long> implements CategoriaDAO
{

    public CategoriaDAOImpl()
    {	super(Categoria.class);

    }

    @Override
    public Categoria recuperaUmaCategoriaPorNome(String nome) throws ObjetoNaoEncontradoException {
        try
        {
            Query query = em.createQuery("select p from dao.Categoria p where p.nome = :oNome");
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

}