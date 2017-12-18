import modelo.Categoria;
import modelo.Produto;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IncluiProduto {
    private JTextField productId;
    private JTextField produtoNameTextField;
    private JPanel panel;
    private JButton salvarProdutoButton;
    private JComboBox categoriaProdutoComboBox;
    private JButton removerProdutoButton;
    private Produto p;
    private List<Categoria> categorias;

    private Long getCategoriaSelecionadaId() {
        if(categoriaProdutoComboBox.getSelectedIndex() > -1){
            ComboItem<Long> item = (ComboItem<Long>) categoriaProdutoComboBox.getSelectedItem();
            return item.getValue();
        }
        return 0L;
    }

    private IncluiProduto(Produto p, List<Categoria> categorias, IncluirProdutoAcao acao, JFrame frame) {
        this.p = p;
        this.categorias = categorias;

        salvarProdutoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String result = acao.salvar(Long.parseLong(productId.getText()), produtoNameTextField.getText(), getCategoriaSelecionadaId());
                if(result == null){
                    JOptionPane.showMessageDialog(null, "Produto incluido com sucesso!");
                    frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel incluir o produto. " + result);
                }
            }
        });

        removerProdutoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String result = acao.remover(p.getId());
                if(result == null){
                    JOptionPane.showMessageDialog(null, "Produto removido com sucesso.");
                    frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao remover produto. " + result);
                }
            }
        });

    }

    public static void main(IncluirProdutoAcao incluirProdutoAcao, Produto p, List<Categoria> categorias) {
        JFrame frame = new JFrame("IncluiProduto");
        frame.setContentPane(new IncluiProduto(p, categorias, incluirProdutoAcao, frame).panel);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500,180);
        frame.setLocation(250, 250);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE );
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Long produtoId;
        String name;
        final Long categoria ;
        if(p != null && p.getId() != null){
            produtoId = p.getId();
        } else {
            produtoId = 0L;
        }
        if(p != null && p.getNome() != null){
            name = p.getNome();
        } else {
            name = "";
        }
        if(p != null && p.getCategoria() != null){
            categoria = p.getCategoria();
        } else {
            categoria = 0L;
        }

        productId = new JTextField(produtoId.toString());
        produtoNameTextField = new JTextField(name);
        categoriaProdutoComboBox = new JComboBox();
        removerProdutoButton = new JButton();

        List<ComboItem> comboItems = categorias.stream().map(x -> new ComboItem(x.getNome(), x.getId())).collect(Collectors.toList());
        comboItems.forEach(x -> categoriaProdutoComboBox.addItem(x));

        removerProdutoButton.setEnabled(produtoId > 0);

        if(categoria > 0){
            categoriaProdutoComboBox.setSelectedItem(comboItems.stream().filter(x -> x.getValue().equals(categoria)));
        }

    }

    public interface IncluirProdutoAcao {
        String salvar(Long id, String nome, Long categoriaId);
        String remover(Long id);
    }


}

class ComboItem<V>
{
    private String key;
    private V value;

    public ComboItem(String key, V value)
    {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return key;
    }

    public String getKey()
    {
        return key;
    }

    public V getValue()
    {
        return value;
    }
}
