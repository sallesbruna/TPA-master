package exercicio;

import java.util.List;

public interface ProdutoDAO
{	
	public long inclui(Produto umProduto); 

	public void altera(Produto umProduto)
		throws ObjetoNaoEncontradoException; 
	
	public void exclui(long id) 
		throws ObjetoNaoEncontradoException; 
	
	public Produto recuperaUmProduto(long numero) 
		throws ObjetoNaoEncontradoException; 
	
	public List<Produto> recuperaProdutos();
}