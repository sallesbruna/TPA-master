package excecao;

import anotacao.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class ViolacaoDeConstraintDesconhecidaException extends RuntimeException
{	
	private final static long serialVersionUID = 1;
	
	public ViolacaoDeConstraintDesconhecidaException()
	{	super();
	}

	public ViolacaoDeConstraintDesconhecidaException(String msg)
	{	super(msg);
	}
}	