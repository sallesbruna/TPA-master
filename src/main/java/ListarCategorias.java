import modelo.Categoria;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ListarCategorias {
    private final ListarCategoriasAcao listarCategoriasAcao;
    private JList categoriasLista;
    private JPanel panel1;
    private JButton selecionarCategoriaButton;

    private List<Categoria> __categorias;

    private List<Categoria> getCategorias() {
        return __categorias;
    }

    private void atualizarListaDeCategorias() {
        __categorias = listarCategoriasAcao.getListaCategorias();

        int index = categoriasLista.getSelectedIndex();
        ((DefaultListModel<String>)categoriasLista.getModel()).clear();
        for (Categoria p: getCategorias()) {

            ((DefaultListModel<String>)categoriasLista.getModel()).addElement(p.toString());
        }
        categoriasLista.setSelectedIndex(index);
    }

    public ListarCategorias(ListarCategoriasAcao listarCategoriasAcao, JFrame frame) {
        this.listarCategoriasAcao = listarCategoriasAcao;

        selecionarCategoriaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int categoriasListaSelectedIndex = categoriasLista.getSelectedIndex();
                if (categoriasListaSelectedIndex == -1) {
                    JOptionPane.showMessageDialog(null, "Nenhuma categoria selecionada.");
                } else {
                    listarCategoriasAcao.selecionaCategoria(getCategorias().get(categoriasListaSelectedIndex));
                    frame.setVisible(false);
                }


            }
        });
    }

    public static void main(ListarCategoriasAcao listarCategoriasAcao) {
        JFrame frame = new JFrame("IncluiCategoria");
        frame.setContentPane(new ListarCategorias(listarCategoriasAcao, frame).panel1);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500,150);
        frame.setLocation(250, 250);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE );
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        categoriasLista = new JList();
        categoriasLista.setModel(new DefaultListModel<String>());

        atualizarListaDeCategorias();
    }


    public interface ListarCategoriasAcao {
        List<Categoria> getListaCategorias();
        void selecionaCategoria(Categoria categoria);
    }

    public interface CategoriaSelecionadaAcao {
        void onCategoriaSelecionada(Categoria categoria);
    }
}
