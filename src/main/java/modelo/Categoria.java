package modelo;

import java.util.List;

import javax.persistence.*;

@NamedQueries(
    {
        @NamedQuery
        (	name = "Categoria.recuperaUmaCategoria",
                query = "select c from Categoria c where c.id = ?1"
        ),
        @NamedQuery
        (	name = "Categoria.recuperaCatetorias",
                query = "select c from Categoria c order by c.id"
        ),
        @NamedQuery
        (	name = "Categoria.recuperaUmaCategoriaPorNome",
                query = "select c from Categoria c where c.nome = ?1"
        )
    })
@Entity
@Table(name="CATEGORIA")
public class Categoria {

    private Long id;
    private String nome;
    private List<Produto> produtos;

    public Categoria()
    {
    }

    public static Categoria criarCategoria(String nome){
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        return categoria;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    public Long getId() {
        return id;
    }

    @Column(name="NOME")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="CATEGORIA_ID")
    public List<Produto> getProdutos(){
        return produtos;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setProdutos(List<Produto> produtos){
        this.produtos = produtos;
    }

    @Override
    public String toString(){
        return "{ " +
                "\n Número = " + getId()  +
                "\n Nome = " + getNome() +
                "\n }";
    }
}
