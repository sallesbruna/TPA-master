package exercicio;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="CATEGORIA")
public class Categoria {

    private long id;
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
