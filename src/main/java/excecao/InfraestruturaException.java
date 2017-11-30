package excecao;

public class InfraestruturaException extends RuntimeException
{	
	private final static long serialVersionUID = 1;
	
	public InfraestruturaException(Exception e)
	{	super(e);
	}

	public InfraestruturaException(String s) {
		super(s);
	}
}