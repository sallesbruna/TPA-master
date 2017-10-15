package exercicio;

import java.util.List;

public interface CategoriaDAO
{
    long inclui(Categoria umaCategoria);

    void altera(Categoria umaCategoria)
            throws ObjetoNaoEncontradoException;

    void exclui(Long id)
            throws ObjetoNaoEncontradoException;

    Categoria recuperaUmaCategoria(Long id)
            throws ObjetoNaoEncontradoException;

    Categoria recuperaUmaCategoriaPorNome(String nome)
            throws ObjetoNaoEncontradoException;

    List<Categoria> recuperaCategorias();
}