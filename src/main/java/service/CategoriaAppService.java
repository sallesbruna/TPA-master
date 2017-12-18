package service;


import anotacao.RoleAdmin;
import anotacao.RoleUser1;
import dao.Result;
import excecao.CategoriaJaCadastradaException;
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

    @RoleAdmin
    @Transactional
    public Categoria inclui(Categoria umaCategoria)
    {
        try {
            Categoria c = recuperaCategoriaPorNome(umaCategoria.getNome());
        } catch (CategoriaNaoEncontradaException e) {
            return categoriaDAO.inclui(umaCategoria);
        }

        throw new CategoriaJaCadastradaException();
    }

    @RoleAdmin
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

    @RoleAdmin
    @Transactional
    public void exclui(Long categoriaId) throws CategoriaNaoEncontradaException
    {
        Categoria categoria;
        try{
            categoria = categoriaDAO.recuperaUmaCategoria(categoriaId);
        } catch (ObjetoNaoEncontradoException ex){
            throw new CategoriaNaoEncontradaException("Categoria com o id informado não encontrada.");
        }

        if(categoria.getProdutos().size() > 0)
        {
            throw new InfraestruturaException("Existe produtos com esta categoria, não foi possível remover.");
        }

        categoriaDAO.exclui(categoria);
    }

    @RoleAdmin
    public void excluiPorNome(String resposta) throws CategoriaNaoEncontradaException {
        Categoria c =  recuperaCategoriaPorNome(resposta);
        exclui(c.getId());
    }

    @RoleUser1
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

    @RoleUser1
    public List<Categoria> recuperaCategorias()
    {

        return categoriaDAO.recuperaCatetorias();
    }


    @RoleUser1
    public Categoria recuperaCategoriaPorNome(String nome) throws CategoriaNaoEncontradaException {
        try {
            return categoriaDAO.recuperaUmaCategoriaPorNome(nome);
        } catch (ObjetoNaoEncontradoException ex){
            throw new CategoriaNaoEncontradaException("Categoria com o nome informado não encontrada");
        }
    }

    @Transactional
    public Result<Categoria> recuperaCategoriaPorNomeOuInsere(String nomeCategoria) {
        try {
            Categoria c = recuperaCategoriaPorNome(nomeCategoria);
            return new Result<Categoria>(c);
        } catch(CategoriaNaoEncontradaException ex) {
            Categoria newCategoria = new Categoria();
            newCategoria.setNome(nomeCategoria);
            Categoria categoriaInserida = inclui(newCategoria);
            return new Result<Categoria>(categoriaInserida);
        }

    }

}