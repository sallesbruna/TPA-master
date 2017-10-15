package exercicio;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="PRODUTO")
public class Produto
{

	private Long id;
	private Date dataCadastro;
	private String nome;
	private Date dataVenda;
	private Categoria categoria;

	// ********* Construtores *********

	public Produto()
	{
	}

	public static Produto criarProduto(String nome, Date dataCadastro, Categoria categoria){
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
	
	public String getNome()
	{	return nome;
	}

	@Column (name="CATEGORIA_ID")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	public Categoria getCategoria(){
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

	public void setCategoria(Categoria categoria)
	{	this.categoria = categoria;
	}

	@Override
	public String toString(){
		return "{ Número = " + getId()  +
				"\n Nome = " + getNome() +
				"\n Categoria = " + getCategoria().toString() +
				"\n Data Cadastro = " + getDataCadastro() +
				"\n Data Venda = " + getDataVenda() +
				"\n }";
	}
}

