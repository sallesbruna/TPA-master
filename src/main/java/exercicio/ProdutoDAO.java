package exercicio;

import java.util.List;

public interface ProdutoDAO
{	
	public Long inclui(Produto umProduto);

	public void altera(Produto umProduto)
		throws ObjetoNaoEncontradoException; 
	
	public void exclui(Long id)
		throws ObjetoNaoEncontradoException; 
	
	public Produto recuperaUmProduto(Long numero)
		throws ObjetoNaoEncontradoException; 
	
	public List<Produto> recuperaProdutos();

	public List<Produto> recuperaProdutosPorCategoria(Long categoriaId) throws ObjetoNaoEncontradoException;

}