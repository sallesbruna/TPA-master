package excecao;

import anotacao.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class ExecucaoDeMetodoSemARespectivaPermissaoException extends RuntimeException
{
	private final static long serialVersionUID = 1;

	public ExecucaoDeMetodoSemARespectivaPermissaoException()
	{	super();
	}

	public ExecucaoDeMetodoSemARespectivaPermissaoException(String msg)
	{	super(msg);
	}
}	