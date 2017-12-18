import modelo.Produto;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditarProduto {
    private JPanel panel;
    private JTextField produtoNameTextField;
    private JButton salvarProdutoButton;
    private JTextField categoriaTextField;
    private Produto produto;

    public EditarProduto(IncluiProduto.IncluirProdutoAcao acao, Produto produto, JFrame frame) {
        this.produto = produto;
        salvarProdutoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(acao.salvar(produtoNameTextField.getText(), categoriaTextField.getText())){
                    JOptionPane.showMessageDialog(null, "Produto alterado com sucesso!");

                    frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel alterado o produto");
                }
            }
        });
    }

    public static void main(IncluiProduto.IncluirProdutoAcao acao, Produto produto) {
        JFrame frame = new JFrame("IncluiProduto");
        frame.setContentPane(new EditarProduto(acao, produto, frame).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        produtoNameTextField = new JTextField(produto.getNome());
        categoriaTextField = new JTextField(produto.getCategoriaNome());
    }

    public interface IncluirProdutoAcao {
        boolean salvar(String produtoNome, String descricao);
    }
}
