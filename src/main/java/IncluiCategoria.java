import modelo.Categoria;
import modelo.Produto;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class IncluiCategoria {
    private JTextField categoriaIdTextField;
    private JTextField categoriaNameTextField;
    private JButton salvarCategoriaButton;
    private JButton removerCategoriaButton;
    private JPanel panel;
    private Categoria c;

    private IncluiCategoria(Categoria c, IncluiCategoriaAcao incluiCategoriaAcao, JFrame frame) {
        this.c = c;

        salvarCategoriaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String result = incluiCategoriaAcao.salvar(Long.parseLong(categoriaIdTextField.getText()), categoriaNameTextField.getText());
                if(result == null){
                    JOptionPane.showMessageDialog(null, "Categoria incluida com sucesso!");
                    frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel incluir a produta. " + result);
                }
            }
        });
        removerCategoriaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String result = incluiCategoriaAcao.remover(c.getId());
                if(result == null){
                    JOptionPane.showMessageDialog(null, "Produto removido com sucesso.");
                    frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao remover produto. " + result);
                }
            }
        });
    }

    public static void main(IncluiCategoriaAcao incluiCategoriaAcao, Categoria c) {
        JFrame frame = new JFrame("IncluiCategoria");
        frame.setContentPane(new IncluiCategoria(c, incluiCategoriaAcao, frame).panel);
        frame.pack();
        frame.setVisible(true);
        //frame.setSize(500,150);
        frame.setLocation(250, 250);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE );
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Long id;
        String name;
        if(c != null && c.getId() != null) {
            id = c.getId();
        } else {
            id = 0L;
        }
        if(c != null && c.getNome() != null) {
            name = c.getNome();
        } else {
            name = "";
        }

        categoriaIdTextField = new JTextField(id.toString());
        categoriaNameTextField = new JTextField(name);
        removerCategoriaButton = new JButton();

        removerCategoriaButton.setEnabled(id > 0);
    }

    public interface IncluiCategoriaAcao {
        String salvar(Long id, String nome);
        String remover(Long id);
    }

}
