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

}