package modelo;

import service.CategoriaAppService;
import util.Util;
import excecao.CategoriaNaoEncontradaException;

import java.sql.Date;

import javax.persistence.*;


@NamedQueries(
	{
		@NamedQuery
		(	name = "Produto.recuperaUmProduto",
			query = "select p from Produto p where p.id = ?1"
		),
		@NamedQuery
		(	name = "Produto.recuperaProdutos",
			query = "select p from Produto p "
		)
	})
@Entity
@Table(name="PRODUTO")
public class Produto
{

	private Long id;
	private Date dataCadastro;
	private String nome;
	private Date dataVenda;
	private Long categoria;
	private String categoriaNome;

	// ********* Construtores *********

	public Produto()
	{
	}

	public static Produto criarProduto(String nome, Date dataCadastro, Long categoria){
		Produto p = new Produto();
		p.setNome(nome);
		p.setDataCadastro(dataCadastro);
		p.setCategoria(categoria);
		return p;
	}

	// ********* Métodos do Tipo Get *********
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getId()
	{	return id;
	}

	@Column(name="NOME")
	public String getNome()
	{	return nome;
	}

	//@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	//@JoinColumn(name="CATEGORIA_ID", nullable = false)
	@Column(name="CATEGORIA_ID")
	public Long getCategoria(){
		return categoria;
	}

	@Column(name="DATA_CADASTRO")
	public Date getDataCadastro()
	{	return dataCadastro;
	}

	@Transient
	public String getDataCadastroMasc()
	{	return Util.dateToStr(dataCadastro);
	}

	@Column(name="DATA_VENDA")
	public Date getDataVenda()
	{	return dataVenda;
	}
	
	@Transient
	public String getDataVendaMasc()
	{	return Util.dateToStr(dataVenda);
	}


	// ********* Métodos do Tipo Set *********

	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}

	public void setNome(String nome)
	{	this.nome = nome;
	}

	
	public void setDataCadastro(Date dataCadastro)
	{	this.dataCadastro = dataCadastro;	
	}
	
	public void setDataVenda(Date dataVenda)
	{	this.dataVenda = dataVenda;
	}

	public void setCategoria(Long categoria)
	{	this.categoria = categoria;
	}

	@Override
	public String toString(){
		return "{ " +
				"\n Número = " + getId() +
				"\n Nome = " + getNome() +
				"\n Data Cadastro = " + getDataCadastro() +
				"\n Data Venda = " + getDataVenda() +
				"\n }";

	}

	@Transient
    public String getCategoriaNome() {
        return "asdf";
    }

	public void setCategoriaNome(String categoriaNome) {
		this.categoriaNome = categoriaNome;
	}
}

