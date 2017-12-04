package excecao;

import anotacao.ConstraintViolada;
import anotacao.ExcecaoDeAplicacao;

@ConstraintViolada(nome="CARRO_MOTORISTA_FK")
@ExcecaoDeAplicacao
public class CategoriaComProdutosException extends RuntimeException
{
	private final static long serialVersionUID = 1;

	public CategoriaComProdutosException()
	{	super();
	}

	public CategoriaComProdutosException(String msg)
	{	super(msg);
	}
}	