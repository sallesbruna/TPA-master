import java.util.Date;
import java.util.List;
import dao.Result;
import excecao.CategoriaNaoEncontradaException;
import excecao.ProdutoNaoEncontradoException;
import modelo.Categoria;
import modelo.Produto;
import org.hibernate.annotations.SourceType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.CategoriaAppService;
import service.ProdutoAppService;
import util.Util;

public class Principal {
	public static void main(String[] args) {
		String nome;
		String nomeCategoria;
		String dataCadastro;
		Produto umProduto;
		Categoria umaCategoria = null;


		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		ProdutoAppService produtoAppService = (ProdutoAppService) fabrica.getBean("produtoAppService");
		CategoriaAppService categoriaAppService = (CategoriaAppService) fabrica.getBean("categoriaAppService");

		boolean continua = true;
		while (continua) {
			System.out.println("\n O que voce deseja fazer?");
			System.out.println("\n 1. Cadastrar um produto");
			System.out.println("\n 2. Alterar um produto");
			System.out.println("\n 3. Remover um produto");
			System.out.println("\n 4. Listar todos os produtos");
			System.out.println("\n 5. Listar produtos por categoria");
			System.out.println("\n 6. Cadastrar categoria");
			System.out.println("\n 7. Remover categoria");
			System.out.println("\n 8. Listar todas as categorias");
			System.out.println("\n 9. Sair");

			int opcao = Console.readInt('\n' +
					"Digite um numero entre 1 e 7:");

			switch (opcao) {
				case 1: {
					nome = Console.readLine('\n' +
							"Informe o nome do produto: ");
					dataCadastro = Util.dateToStr(new java.sql.Date(new Date().getTime()));
					nomeCategoria = Console.readLine(
							"Informe a categoria do produto: ");

					Result<Categoria> categoriaResult = categoriaAppService.recuperaCategoriaPorNomeOuInsere(nomeCategoria);
					if (categoriaResult.hasMessage()) {
						System.out.println(categoriaResult.getMessage());
					}
					umaCategoria = categoriaResult.getPayload();
					umProduto = Produto.criarProduto(nome, Util.strToDate(dataCadastro), umaCategoria.getId());

					Produto numero = produtoAppService.inclui(umProduto);

					System.out.println('\n' + "Produto numero " +
							numero + " incluido com sucesso!");

					break;
				}

				case 2: {
					int resposta = Console.readInt('\n' +
							"Digite o numero do produto que voce deseja alterar: ");

					try {
						umProduto = produtoAppService.recuperaUmProduto((long) resposta);
					} catch (ProdutoNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
						break;
					}

					System.out.println(umProduto.toString());


					System.out.println("\n O que voce deseja alterar?");
					System.out.println("\n 1. Nome");
					System.out.println("\n 2. Categoria");

					int opcaoAlteracao = Console.readInt('\n' +
							"Digite um numero de 1 a 2:");

					switch (opcaoAlteracao) {
						case 1:
							String novoNome = Console.
									readLine("Digite o novo nome: ");

							umProduto.setNome(novoNome);

							produtoAppService.altera(umProduto);

							System.out.println('\n' +
									"Alteracao de nome efetuada com sucesso!");

							break;

						case 2:
							String novaCategoria = Console.
									readLine("Digite a nova categoria: ");

							Result<Categoria> categoriaResult = categoriaAppService.recuperaCategoriaPorNomeOuInsere(novaCategoria);
							if (categoriaResult.hasMessage()) {
								System.out.println(categoriaResult.getMessage());
							}
							Categoria categoria = categoriaResult.getPayload();
							umProduto.setCategoria(categoria.getId());

							produtoAppService.altera(umProduto);

							System.out.println('\n' +
									"Alteracao de descricao efetuada " +
									"com sucesso!");

							break;

						default:
							System.out.println('\n' + "Opcao invalida!");
					}

					break;
				}

				case 3: {
					int resposta = Console.readInt('\n' +
							"Digite o numero do produto que voce deseja remover: ");

					try {
						umProduto = produtoAppService.
								recuperaUmProduto((long) resposta);
					} catch (ProdutoNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
						break;
					}

					System.out.println('\n' +
							"Numero = " + umProduto.getId() +
							"    Nome = " + umProduto.getNome());

					String resp = Console.readLine('\n' +
							"Confirma a remocao do produto? (s|n)");

					if (resp.toLowerCase().equals("s")) {
						try {
							produtoAppService.exclui(umProduto.getId());

							System.out.println('\n' +
									"Produto removido com sucesso!");
						} catch (ProdutoNaoEncontradoException e) {

							System.out.println('\n' +
									"Produto não encontrado.");
						}

					} else {
						System.out.println('\n' +
								"Produto nao removido.");
					}

					break;
				}

				case 4: {
					List<Produto> produtos = produtoAppService.recuperaProdutos();

					for (Produto produto : produtos) {

						System.out.println(produto.toString());
					}

					break;
				}


				case 5: {
					String categ = Console.readLine('\n' + "Digite a categoria que deseja buscar produtos");
					CategoriaAppService.recuperaCategoriaPorNome(categ);

					break;
				}


				case 6: {
					String novoNome = Console.readLine("Digite o novo nome para a Categoria: ");
					Categoria c = Categoria.criarCategoria(novoNome);
					categoriaAppService.inclui(c);

					break;
				}

				case 7: {
					int resposta = Console.readInt('\n' +
							"Digite o nome da categoria que voce deseja remover: ");

					try {
						categoriaAppService.exclui((long) resposta);

					} catch (CategoriaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
						break;
					}

					System.out.println('\n' +
							"Numero = " + umaCategoria.getId() +
							"    Nome = " + umaCategoria.getNome());

					String resp = Console.readLine('\n' +
							"Confirma a remocao da categoria? (s|n)");

					if (resp.toLowerCase().equals("s")) {
						try {
							categoriaAppService.exclui(umaCategoria.getId());

							System.out.println('\n' +
									"Categoria removida com sucesso!");
						} catch (CategoriaNaoEncontradaException e) {

							System.out.println('\n' +
									"Categoria não encontrada.");
						}
					}
					break;
				}

				case 8: {
					List<Categoria> categoriaList = categoriaAppService.recuperaCategorias();
					for (Categoria categoria : categoriaList){
						System.out.println(categoria.toString());
					}

					break;
				}

				default:
					System.out.println('\n' + "Opcao invalida!");
			}


		}


	}
}