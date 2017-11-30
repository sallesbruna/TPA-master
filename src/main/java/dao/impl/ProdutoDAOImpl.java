package dao.impl;

import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import dao.ProdutoDAO;
import modelo.Categoria;
import modelo.Produto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

public abstract class ProdutoDAOImpl extends JPADaoGenerico<Produto, Long>  implements ProdutoDAO
{	
	public ProdutoDAOImpl(){
		super(Produto.class);
	}
}