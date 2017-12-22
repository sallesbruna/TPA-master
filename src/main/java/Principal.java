import java.util.Date;
import java.util.List;

import anotacao.RoleAdmin;
import anotacao.RoleUser1;
import anotacao.RoleUser2;
import anotacao.RoleUser3;
import aspecto.PermissoesSingleton;
import dao.Result;
import excecao.CategoriaNaoEncontradaException;
import excecao.ProdutoNaoEncontradoException;
import modelo.Categoria;
import modelo.Produto;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.CategoriaAppService;
import service.ProdutoAppService;
import util.Util;

public class Principal {
	public static void main(String[] args) {

		PermissoesSingleton.getPermissoesSingleton().adicionaPermissao(RoleUser1.PERMISSAO);

		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		ProdutoAppService produtoAppService = (ProdutoAppService) fabrica.getBean("produtoAppService");
		CategoriaAppService categoriaAppService = (CategoriaAppService) fabrica.getBean("categoriaAppService");


        ListarProdutos.main(produtoAppService.recuperaProdutos(), categoriaAppService.recuperaCategorias(),
                new ListarProdutos.ListarProdutosAcao() {

                    @Override
                    public List<Produto> getListaProdutos() {
                        return produtoAppService.recuperaProdutos();
                    }

                    @Override
                    public String visualizarNovoProduto(){
                        Produto p = new Produto();

                        IncluiProduto.main(new IncluiProduto.IncluirProdutoAcao() {
                            @Override
                            public String salvar(Long id, String nome, Long categoriaId) {
                                try {
                                    Produto p = new Produto();
                                    p.setNome(nome);
                                    p.setCategoria(categoriaId);
                                    p.setDataCadastro(new java.sql.Date(new java.util.Date().getTime()));

                                    produtoAppService.inclui(p);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return e.getMessage();
                                }
                                return null;
                            }

                            @Override
                            public String remover(Long id) {

                                try {
                                    produtoAppService.exclui(id);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return e.getMessage();
                                }
                                return null;
                            }

                            @Override
                            public void selecionaCategoriaId(ListarCategorias.CategoriaSelecionadaAcao categoriaSelecionadaAcao) {


                                ListarCategorias.main(new ListarCategorias.ListarCategoriasAcao() {
                                    @Override
                                    public List<Categoria> getListaCategorias() {
                                        return categoriaAppService.recuperaCategorias();
                                    }

                                    @Override
                                    public void selecionaCategoria(Categoria categoria) {
                                        categoriaSelecionadaAcao.onCategoriaSelecionada(categoria);
                                    }
                                });
                            }

                        }, p, categoriaAppService.recuperaCategorias());

                        return null;
                    }

                    @Override
                    public String visualizarProdutoExistente(Long produto) {
                        try {
                            Produto p = produtoAppService.recuperaUmProduto(produto);

                            IncluiProduto.main(new IncluiProduto.IncluirProdutoAcao() {
                                @Override
                                public String salvar(Long id, String nome, Long categoriaId) {
                                    try {

                                        Produto produto = produtoAppService.recuperaUmProduto(id);
                                        produto.setNome(nome);
                                        produto.setCategoria(categoriaId);
                                        produto.setDataCadastro(new java.sql.Date(new java.util.Date().getTime()));

                                        produtoAppService.altera(produto);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return e.getMessage();
                                    }
                                    return null;
                                }

                                @Override
                                public String remover(Long id) {

                                    try {
                                        produtoAppService.exclui(id);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return e.getMessage();
                                    }
                                    return null;
                                }

                                @Override
                                public void selecionaCategoriaId(ListarCategorias.CategoriaSelecionadaAcao categoriaSelecionadaAcao) {


                                    ListarCategorias.main(new ListarCategorias.ListarCategoriasAcao() {
                                        @Override
                                        public List<Categoria> getListaCategorias() {
                                            return categoriaAppService.recuperaCategorias();
                                        }

                                        @Override
                                        public void selecionaCategoria(Categoria categoria) {
                                            categoriaSelecionadaAcao.onCategoriaSelecionada(categoria);
                                        }
                                    });
                                }


                            }, p, categoriaAppService.recuperaCategorias());

                            return null;
                        } catch(Exception e){
                            e.printStackTrace();
                            return e.getMessage();
                        }

                    }

                    @Override
                    public List<Categoria> getListaCategorias() {
                        return categoriaAppService.recuperaCategorias();
                    }

                    @Override
                    public String visualizarCategoriaExistente(Long categoria) {
                        try {
                            IncluiCategoria.main(new IncluiCategoria.IncluiCategoriaAcao() {
                                @Override
                                public String salvar(Long id, String nome) {

                                    try {
                                        Categoria c = categoriaAppService.recuperaUmaCategoria(id);
                                        c.setNome(nome);
                                        categoriaAppService.altera(c);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return e.getMessage();
                                    }
                                    return null;
                                }

                                @Override
                                public String remover(Long id) {

                                    try {
                                        categoriaAppService.exclui(id);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return e.getMessage();
                                    }
                                    return null;
                                }

                            }, categoriaAppService.recuperaUmaCategoria(categoria));

                            return null;
                        } catch(Exception ex){
                            return ex.getMessage();
                        }

                    }

                    @Override
                    public String novaCategoria() {
                        try {
                            IncluiCategoria.main(new IncluiCategoria.IncluiCategoriaAcao() {
                                @Override
                                public String salvar(Long id, String nome) {

                                    try {
                                        Categoria c = new Categoria();
                                        c.setNome(nome);
                                        categoriaAppService.inclui(c);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return e.getMessage();
                                    }
                                    return null;
                                }

                                @Override
                                public String remover(Long id) {

                                    try {
                                        categoriaAppService.exclui(id);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return e.getMessage();
                                    }
                                    return null;
                                }

                            }, new Categoria());

                            return null;
                        } catch(Exception ex){
                            return ex.getMessage();
                        }

                    }

                });


	}
}
