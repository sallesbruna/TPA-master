package dao;

import anotacao.RecuperaConjunto;
import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import excecao.ObjetoNaoEncontradoException;
import modelo.Produto;

import java.util.List;


public interface ProdutoDAO extends DaoGenerico<Produto, Long>
{


	@RecuperaObjeto
	Produto recuperaUmProduto(Long numero)
			throws ObjetoNaoEncontradoException;

	@RecuperaLista
	List<Produto> recuperaProdutos();

}