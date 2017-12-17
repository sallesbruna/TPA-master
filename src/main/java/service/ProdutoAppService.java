package service;

import dao.CategoriaDAO;
import excecao.CategoriaNaoEncontradaException;
import excecao.ObjetoNaoEncontradoException;
import excecao.ProdutoNaoEncontradoException;
import dao.ProdutoDAO;
import modelo.Categoria;
import modelo.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoAppService
{	
	private ProdutoDAO produtoDAO;
	private CategoriaDAO categoriaDAO;

	@Autowired
	public void setProdutoDAO(ProdutoDAO produtoDAO){
		this.produtoDAO = produtoDAO;
	}

	@Autowired
	public void setCategoriaDAO(CategoriaDAO categoriaDAO){
		this.categoriaDAO = categoriaDAO;
	}


	public ProdutoAppService(){

	}

	@Transactional
	public Produto inclui(Produto umProduto) {
		Produto p = produtoDAO.inclui(umProduto);
		return p;
	}

	@Transactional
	public void altera(Produto umProduto){
			produtoDAO.altera(umProduto);
	}

	@Transactional
	public void exclui(Long produtoId) throws ProdutoNaoEncontradoException {
		Produto p = recuperaUmProduto(produtoId);

		produtoDAO.exclui(p);
	}

	public Produto recuperaUmProduto(Long numero) throws ProdutoNaoEncontradoException {
		try
		{
			Produto umProduto = produtoDAO.recuperaUmProduto(numero);
			
			return umProduto;
		} 
		catch(ObjetoNaoEncontradoException e)
		{
			throw new ProdutoNaoEncontradoException("Produto não encontrado");
		}
	}

	public List<Produto> recuperaProdutos()
	{
		List<Produto> produtos = produtoDAO.recuperaProdutos();

		return produtos;
	}


	public List<Produto> recuperaProdutosPorCategoria(String nomeCategoria) {
		Categoria categoria;
		try {
			categoria = categoriaDAO.recuperaUmaCategoriaPorNome(nomeCategoria);
		} catch (CategoriaNaoEncontradaException e) {
			return new ArrayList<>();
		}
		List<Produto> produtos = produtoDAO.recuperaProdutos()
				.stream()
				.filter(x -> x.getCategoria().equals(categoria.getId()))
				.collect(Collectors.toList());
		return produtos;
	}
}