import modelo.Produto;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IncluiProduto {
    private JTextField produtoNameTextField;
    private JTextField categoriaTextField;
    private JPanel panel;
    private JButton salvarProdutoButton;

    public IncluiProduto(IncluirProdutoAcao acao, JFrame frame) {
        salvarProdutoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(acao.salvar(produtoNameTextField.getText(), categoriaTextField.getText())){
                    JOptionPane.showMessageDialog(null, "Produto incluido com sucesso!");

                    frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel incluir o produto");
                }
            }
        });
    }

    public static void main(IncluirProdutoAcao acao) {
        JFrame frame = new JFrame("IncluiProduto");
        frame.setContentPane(new IncluiProduto(acao, frame).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public interface IncluirProdutoAcao {
        boolean salvar(String produtoNome, String descricao);
    }
}
