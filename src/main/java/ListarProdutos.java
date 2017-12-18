import modelo.Categoria;
import modelo.Produto;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ListarProdutos {
    private JList produtosLista;
    private JPanel panel;
    private JButton listarProdutosBotao;
    private JButton removerSelecionadoButton;
    private JButton verProdutoButton;
    private JButton listarCategoriasButton;
    private JList categoriasLista;
    private JButton verCategoriaButton;
    private JButton novaCategoriaButton;
    private JButton novoProdutoButton;
    private ListarProdutosAcao listarProdutosAcao;

    private void atualizarListaDeProdutos() {
        produtosLista.removeAll();

        DefaultListModel<String> model = new DefaultListModel<String>();
        produtosLista.setModel(model);

        List<Produto> produtinhos = listarProdutosAcao.getListaProdutos();
        for (Produto p: produtinhos) {

            ((DefaultListModel<String>)produtosLista.getModel()).addElement(p.toString());
            produtosLista.add(new JLabel());
        }
    }


    private void atualizarListaDeCategorias() {
        categoriasLista.removeAll();

        DefaultListModel<String> model = new DefaultListModel<String>();
        categoriasLista.setModel(model);

        List<Categoria> categorias = listarProdutosAcao.getListaCategorias();
        for (Categoria p: categorias) {

            ((DefaultListModel<String>)categoriasLista.getModel()).addElement(p.toString());
            categoriasLista.add(new JLabel());
        }
    }

    private void novoProduto(ListarProdutosAcao acao){
        String message = acao.visualizarProduto(null);
        if(message != null){
            JOptionPane.showMessageDialog(null, "Não foi possível abrir produto. " + message);
        }
    }

    private void novaCategoria(ListarProdutosAcao acao){
        String message = acao.visualizarCategoria(null);
        if(message != null){
            JOptionPane.showMessageDialog(null, "Não foi possível abrir categoria. " + message);
        }
    }

    private void verProduto(List<Produto> produtinhos, ListarProdutosAcao acao, int index){
        Produto p = produtinhos.get(index);
        String message = acao.visualizarProduto(p.getId());
        if(message != null){
            JOptionPane.showMessageDialog(null, "Não foi possível abrir produto. " + message);
        }
    }
    private void verCategoria(List<Categoria> categorias, ListarProdutosAcao acao, int index){
        Categoria c = categorias.get(index);
        String message = acao.visualizarCategoria(c.getId());
        if(message != null){
            JOptionPane.showMessageDialog(null, "Não foi possível abrir categoria. " + message);
        }
    }

    public ListarProdutos(List<Produto> produtinhos, List<Categoria> categorias, ListarProdutosAcao listarProdutosAcao) {
        this.listarProdutosAcao = listarProdutosAcao;

        listarProdutosBotao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                atualizarListaDeProdutos();
            }
        });

        listarCategoriasButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                atualizarListaDeCategorias();
            }
        });

        verProdutoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int index = produtosLista.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(null, "Nenhum produto selecionado.");
                }else{
                    verProduto(produtinhos, listarProdutosAcao, index);
                    atualizarListaDeProdutos();
                }
            }
        });

        novoProdutoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                novoProduto(listarProdutosAcao);
                atualizarListaDeProdutos();
            }
        });

        verCategoriaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);


                int index = categoriasLista.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(null, "Nenhuma categoria selecionada.");
                }else{
                    verCategoria(categorias, listarProdutosAcao, index);
                    atualizarListaDeCategorias();
                }
            }
        });
        novaCategoriaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                novaCategoria(listarProdutosAcao);
                atualizarListaDeCategorias();
            }
        });
    }

    public static void main(List<Produto> produtinhos,
                            List<Categoria> categorias,
                            ListarProdutosAcao listarProdutosAcao
                            ) {
        JFrame frame = new JFrame("Produtos");
        frame.setContentPane(new ListarProdutos(produtinhos, categorias, listarProdutosAcao).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500,500);
        frame.setLocation(250, 250);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        produtosLista = new JList();
        categoriasLista = new JList();

        atualizarListaDeProdutos();
        atualizarListaDeCategorias();
    }

    public interface ListarProdutosAcao {
        List<Produto> getListaProdutos();
        String visualizarProduto(Long produto);
        List<Categoria> getListaCategorias();
        String visualizarCategoria(Long categoria);
    }



}
