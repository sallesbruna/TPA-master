package service;


import dao.Result;
import excecao.CategoriaNaoEncontradaException;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import dao.CategoriaDAO;
import modelo.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CategoriaAppService
{
    private CategoriaDAO categoriaDAO = null;

    public CategoriaAppService() {

    }

    @Autowired
    public void setCategoriaDAO(CategoriaDAO categoriaDAO)
    {
        this.categoriaDAO = categoriaDAO;
    }

    @Transactional
    public Categoria inclui(Categoria umaCategoria)
    {
        return categoriaDAO.inclui(umaCategoria);
    }

    @Transactional
    public void altera(Categoria umaCategoria) throws CategoriaNaoEncontradaException
    {
        try
        {
            categoriaDAO.getPorIdComLock(umaCategoria.getId());
            categoriaDAO.altera(umaCategoria);
        }
        catch(ObjetoNaoEncontradoException e)
        {
            throw new CategoriaNaoEncontradaException("Categoria não encontrada");
        }
    }

    @Transactional
    public void exclui(Long categoriaId) throws CategoriaNaoEncontradaException
    {
        try
        {
            Categoria categoria = categoriaDAO.recuperaUmaCategoria(categoriaId);

            if(categoria.getProdutos().size() > 0)
            {
                throw new InfraestruturaException("Existe produtos com esta categoria, não foi possível remover.");
            }

            categoriaDAO.exclui(categoria);
        }
        catch(ObjetoNaoEncontradoException e)
        {
            throw new CategoriaNaoEncontradaException("Categoria não encontrada");
        }
    }

    public Categoria recuperaUmaCategoria(long numero) throws CategoriaNaoEncontradaException
    {
        try
        {
            return categoriaDAO.getPorId(numero);
        }
        catch(ObjetoNaoEncontradoException e)
        {
            throw new CategoriaNaoEncontradaException("Categoria não encontrada");
        }
    }

    public List<Categoria> recuperaCategorias()
    {
        return categoriaDAO.recuperaCatetorias();
    }


    public Categoria recuperaCategoriaPorNome(String nome) throws ObjetoNaoEncontradoException {
        return categoriaDAO.recuperaUmaCategoriaPorNome(nome);
    }

    @Transactional
    public Result<Categoria> recuperaCategoriaPorNomeOuInsere(String nomeCategoria) {
        try {
            Categoria c = recuperaCategoriaPorNome(nomeCategoria);
            return new Result<Categoria>(c);
        } catch(ObjetoNaoEncontradoException ex) {
            Categoria newCategoria = new Categoria();
            newCategoria.setNome(nomeCategoria);
            Categoria categoriaInserida = inclui(newCategoria);
            return new Result<Categoria>(categoriaInserida);
        }

    }
}