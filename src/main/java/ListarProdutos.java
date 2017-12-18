import modelo.Produto;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ListarProdutos {
    private JList produtosLista;
    private JPanel panel;
    private JButton listarProdutosBotao;
    private JButton removerSelecionadoButton;
    private JButton editarSelecionadoButton;
    private ListarProdutosAcao listarProdutosAcao;

    private void atualizarListaDeProdutos() {
        produtosLista.removeAll();

        DefaultListModel<String> model = new DefaultListModel<String>();
        produtosLista.setModel(model);

        List<Produto> produtinhos = listarProdutosAcao.Listar();
        for (Produto p: produtinhos) {

            ((DefaultListModel<String>)produtosLista.getModel()).addElement(p.toString());
            produtosLista.add(new JLabel());
        }
    }

    private boolean removerProduto(List<Produto> produtinhos, RemoverProdutoAcao acao, int index){
        if(acao.Remover(produtinhos.get(index).getId())){
            atualizarListaDeProdutos();
            return true;
        }
        return false;
    }


    private void editarProduto(List<Produto> produtinhos, EditarProdutoAcao acao, int index){
        Produto p = produtinhos.get(index);
        EditarProduto.main(new IncluiProduto.IncluirProdutoAcao() {
            @Override
            public boolean salvar(String produtoNome, String descricao) {
                Produto pEditado = new Produto();
                pEditado.setNome(produtoNome);
                pEditado.setCategoriaNome(descricao);

                boolean editou = acao.Editar(pEditado) != null;
                if(editou){
                    atualizarListaDeProdutos();
                }

                return editou;
            }
        }, p);
    }

    public ListarProdutos(List<Produto> produtinhos, ListarProdutosAcao listarProdutosAcao, RemoverProdutoAcao removerProdutoAcao, EditarProdutoAcao editarProdutoAcao) {
        this.listarProdutosAcao = listarProdutosAcao;

        listarProdutosBotao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                atualizarListaDeProdutos();
            }
        });

        removerSelecionadoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int index = produtosLista.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(null, "Nenhum produto selecionado.");
                }else{
                    if(removerProduto(produtinhos, removerProdutoAcao, index)){
                        JOptionPane.showMessageDialog(null, "Produto removido com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao remover produto.");
                    }
                }
            }
        });
        editarSelecionadoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int index = produtosLista.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(null, "Nenhum produto selecionado.");
                }else{
                    editarProduto(produtinhos, editarProdutoAcao, index);
                }
            }
        });
    }

    public static void main(List<Produto> produtinhos,
                            ListarProdutosAcao listarProdutosAcao,
                            RemoverProdutoAcao removerProdutoAcao,
                            EditarProdutoAcao editarProdutoAcao
                            ) {
        JFrame frame = new JFrame("ListarProdutos");
        frame.setContentPane(new ListarProdutos(produtinhos, listarProdutosAcao, removerProdutoAcao, editarProdutoAcao).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500,500);
    }

    public interface ListarProdutosAcao {
        List<Produto> Listar();
    }

    public interface RemoverProdutoAcao {
        boolean Remover(Long id);
    }

    public interface EditarProdutoAcao {
        Produto Editar(Produto produto);
    }
}
