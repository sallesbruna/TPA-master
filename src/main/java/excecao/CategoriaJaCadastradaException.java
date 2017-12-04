package excecao;

import anotacao.ConstraintViolada;
import anotacao.ExcecaoDeAplicacao;

@ConstraintViolada(nome="MOTORISTA_NOME_UN")
@ExcecaoDeAplicacao
public class CategoriaJaCadastradaException extends RuntimeException
{
	private final static long serialVersionUID = 1;

	public CategoriaJaCadastradaException()
	{	super();
	}

	public CategoriaJaCadastradaException(String msg)
	{	super(msg);
	}
}	