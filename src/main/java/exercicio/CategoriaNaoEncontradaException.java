package exercicio;

public class CategoriaNaoEncontradaException extends Exception {
    private final static long serialVersionUID = 1;

    private int codigo;

    public CategoriaNaoEncontradaException(String msg)
    {	super(msg);
    }

    public CategoriaNaoEncontradaException(int codigo, String msg)
    {	super(msg);
        this.codigo = codigo;
    }

    public int getCodigoDeErro()
    {	return codigo;
    }

}
