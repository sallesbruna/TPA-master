package exercicio;

import java.util.Date;
import java.util.List;
import corejava.Console;

public class Principal
{	public static void main (String[] args) 
	{	
		String nome;
		String nomeCategoria;
		String dataCadastro;
		Produto umProduto;
		Categoria umaCategoria;

		CategoriaAppService categoriaAppService = new CategoriaAppService();
		ProdutoAppService produtoAppService = new ProdutoAppService(categoriaAppService);

		boolean continua = true;
		while (continua)
		{	System.out.println("\n O que você deseja fazer?");
			System.out.println("\n 1. Cadastrar um produto");
			System.out.println("\n 2. Alterar um produto");
			System.out.println("\n 3. Remover um produto");
			System.out.println("\n 4. Listar todos os produtos");
			System.out.println("\n 5. Listar produtos por categoria");
			System.out.println("\n 6. Cadastrar categoria");
			System.out.println("\n 7. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um número entre 1 e 7:");
					
			switch (opcao)
			{
				case 1:
				{
					nome = Console.readLine('\n' + 
						"Informe o nome do produto: ");
					dataCadastro = Util.dateToStr(new java.sql.Date(new Date().getTime()));
					nomeCategoria = Console.readLine(
							"Informe a categoria do produto: ");

					Result<Categoria> categoriaResult = categoriaAppService.recuperaCategoriaPorNomeOuInsere(nomeCategoria);
					if(categoriaResult.hasMessage()){
						System.out.println(categoriaResult.getMessage());
					}
					umaCategoria = categoriaResult.getPayload();
					umProduto = Produto.criarProduto(nome, Util.strToDate(dataCadastro), umaCategoria);

					long numero = produtoAppService.inclui(umProduto);
					
					System.out.println('\n' + "Produto número " + 
					    numero + " incluído com sucesso!");	

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do produto que você deseja alterar: ");
										
					try
					{	umProduto = produtoAppService.recuperaUmProduto(resposta);
					}
					catch(ProdutoNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}

					System.out.println(umProduto.toString());

												
					System.out.println("\n O que você deseja alterar?");
					System.out.println("\n 1. Nome");
					System.out.println("\n 2. Categoria");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um número de 1 a 2:");
					
					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.
										readLine("Digite o novo nome: ");
							
							umProduto.setNome(novoNome);

							try
							{	produtoAppService.altera(umProduto);

								System.out.println('\n' + 
									"Alteração de nome efetuada com sucesso!");
							}
							catch(ProdutoNaoEncontradoException e)
							{	System.out.println('\n' + e.getMessage());
							}
								
							break;
					
						case 2:
							String novaCategoria = Console.
									readLine("Digite a nova categoria: ");

							Result<Categoria> categoriaResult = categoriaAppService.recuperaCategoriaPorNomeOuInsere(novaCategoria);
							if(categoriaResult.hasMessage()){
								System.out.println(categoriaResult.getMessage());
							}
							Categoria categoria = categoriaResult.getPayload();
							umProduto.setCategoria(categoria);

							try
							{	produtoAppService.altera(umProduto);

								System.out.println('\n' + 
									"Alteração de descrição efetuada " +
									"com sucesso!");						
							}
							catch(ProdutoNaoEncontradoException e)
							{	System.out.println('\n' + e.getMessage());
							}
								
							break;

						default:
							System.out.println('\n' + "Opção inválida!");
					}

					break;
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do produto que você deseja remover: ");
									
					try
					{	umProduto = produtoAppService.
										recuperaUmProduto(resposta);
					}
					catch(ProdutoNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome());
														
					String resp = Console.readLine('\n' + 
						"Confirma a remoção do produto?");

					if(resp.equals("s"))
					{	try
						{	produtoAppService.exclui (umProduto.getId());
							System.out.println('\n' + 
								"Produto removido com sucesso!");
						}
						catch(ProdutoNaoEncontradoException e)
						{	System.out.println('\n' + e.getMessage());
						}
					}
					else
					{	System.out.println('\n' + 
							"Produto não removido.");
					}
					
					break;
				}

				case 4:
				{	
					List<Produto> produtos = produtoAppService.recuperaProdutos();

					for (Produto produto : produtos)
					{	
						System.out.println(produto.toString());
					}
					
					break;
				}


				case 5:
				{
					String novoNome = Console.readLine("Digite o nome para a Categoria: ");
					List<Produto> produtos = produtoAppService.recuperaProdutosPorCategoria(novoNome);

					for (Produto produto : produtos)
					{
						System.out.println(produto.toString());
					}

					break;
						
				}



				case 6:
				{
					String novoNome = Console.readLine("Digite o novo nome para a Categoria: ");
					Categoria c = Categoria.criarCategoria(novoNome);
					categoriaAppService.inclui(c);

					break;
				}


				default:
					System.out.println('\n' + "Opção inválida!");
			}
		}		
	}
}
