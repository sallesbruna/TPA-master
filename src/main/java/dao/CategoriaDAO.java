package dao;

import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import excecao.CategoriaNaoEncontradaException;
import modelo.Categoria;
import excecao.ObjetoNaoEncontradoException;

import java.util.List;

public interface CategoriaDAO extends DaoGenerico<Categoria, Long>
{

    @RecuperaObjeto
    Categoria recuperaUmaCategoria(long numero) throws ObjetoNaoEncontradoException;

    @RecuperaLista
    List<Categoria> recuperaCatetorias();

    @RecuperaObjeto
    Categoria recuperaUmaCategoriaPorNome(String nome) throws ObjetoNaoEncontradoException;

}