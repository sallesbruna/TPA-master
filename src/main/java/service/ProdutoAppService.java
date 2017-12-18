package service;

import anotacao.RoleAdmin;
import anotacao.RoleUser1;
import dao.CategoriaDAO;
import excecao.CategoriaNaoEncontradaException;
import excecao.ObjetoNaoEncontradoException;
import excecao.ProdutoNaoEncontradoException;
import dao.ProdutoDAO;
import modelo.Categoria;
import modelo.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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

	@RoleAdmin
	@Transactional
	public Produto incluiOuAltera(Long id, String nome, Long categoriaId) {
		try {
			Produto produto = produtoDAO.recuperaUmProduto(id);
			produto.setNome(nome);
			produto.setCategoria(categoriaId);
			produto.setDataCadastro(new Date(new java.util.Date().getTime()));
			return produto;
		} catch (ObjetoNaoEncontradoException e) {
			Produto p = new Produto();
			p.setNome(nome);
			p.setCategoria(categoriaId);
			p.setDataCadastro(new Date(new java.util.Date().getTime()));
			produtoDAO.inclui(p);
			return p;
		}
	}

	@RoleAdmin
	@Transactional
	public Produto inclui(Produto umProduto) {
		Produto p = produtoDAO.inclui(umProduto);
		return p;
	}

	@RoleAdmin
	@Transactional
	public void altera(Produto umProduto){
			produtoDAO.altera(umProduto);
	}

	@RoleAdmin
	@Transactional
	public void exclui(Long produtoId) throws ProdutoNaoEncontradoException {
		Produto p = recuperaUmProduto(produtoId);

		produtoDAO.exclui(p);
	}

	@RoleUser1
	public Produto recuperaUmProduto(Long numero) throws ProdutoNaoEncontradoException {
		if(numero == null || numero <= 0) return new Produto();

		try
		{
			Produto umProduto = produtoDAO.recuperaUmProduto(numero);
			
			return umProduto;
		} 
		catch(ObjetoNaoEncontradoException e)
		{
			throw new ProdutoNaoEncontradoException("Produto n�o encontrado");
		}
	}

	@RoleUser1
	public List<Produto> recuperaProdutos()
	{
		List<Produto> produtos = produtoDAO.recuperaProdutos();

		return produtos;
	}

	@RoleUser1
	public List<Produto> recuperaProdutosPorCategoria(String nomeCategoria) {
		Categoria categoria;
		try {
			categoria = categoriaDAO.recuperaUmaCategoriaPorNome(nomeCategoria);
		} catch (ObjetoNaoEncontradoException e) {
			return new ArrayList<>();
		}
		List<Produto> produtos = produtoDAO.recuperaProdutos()
				.stream()
				.filter(x -> x.getCategoria().equals(categoria.getId()))
				.collect(Collectors.toList());
		return produtos;
	}
}