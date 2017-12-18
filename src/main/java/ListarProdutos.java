import aspecto.PermissoesSingleton;
import modelo.Categoria;
import modelo.Produto;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListarProdutos {
    private JList produtosLista;
    private JPanel panel;
    private JButton atualizarButton;
    private JButton verProdutoButton;
    private JList categoriasLista;
    private JButton verCategoriaButton;
    private JButton novaCategoriaButton;
    private JButton novoProdutoButton;
    private JCheckBox atualizarAutomaticamenteCheckBox;
    private JComboBox comboBoxPermissoes;
    private ListarProdutosAcao listarProdutosAcao;
    private List<Produto> __produtinhos;
    private List<Categoria> __categorias;

    private List<Produto> getProdutinhos() {
        return __produtinhos;
    }

    private List<Categoria> getCategorias() {
        return __categorias;
    }

    private void atualizarListaDeProdutos() {
        __produtinhos = listarProdutosAcao.getListaProdutos();

        int index = produtosLista.getSelectedIndex();
        ((DefaultListModel<String>)produtosLista.getModel()).clear();
        for (Produto p: getProdutinhos()) {

            ((DefaultListModel<String>)produtosLista.getModel()).addElement(p.toString());
        }
        produtosLista.setSelectedIndex(index);
    }

    private void atualizarListaDeCategorias() {
        __categorias = listarProdutosAcao.getListaCategorias();

        int index = categoriasLista.getSelectedIndex();
        ((DefaultListModel<String>)categoriasLista.getModel()).clear();
        for (Categoria p: getCategorias()) {

            ((DefaultListModel<String>)categoriasLista.getModel()).addElement(p.toString());
        }
        categoriasLista.setSelectedIndex(index);
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

    private void atualizarRecorrente() {
        try {
            Thread.sleep(1000);
            atualizarListaDeProdutos();
            atualizarListaDeCategorias();
            atualizarRecorrente();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ListarProdutos(List<Produto> p, List<Categoria> c, ListarProdutosAcao listarProdutosAcao) {
        this.listarProdutosAcao = listarProdutosAcao;
        this.__produtinhos = p;
        this.__categorias = c;

        atualizarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                atualizarListaDeProdutos();
                atualizarListaDeCategorias();
            }
        });

        verProdutoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int index = produtosLista.getSelectedIndex();

                try{
                    String selectedValue = (String) produtosLista.getSelectedValue();
                    if(selectedValue != null){
                        String theId = selectedValue.split(",")[0].split(" ")[3];
                        Long id = Long.parseLong(theId);
                        Optional<Produto> first = getProdutinhos().stream().filter(x -> x.getId().equals(id)).findFirst();
                        if(first.isPresent()){
                            index = getProdutinhos().indexOf(first.get());
                        }
                    }
                } catch(Exception ex){ }

                if(index == -1){
                    JOptionPane.showMessageDialog(null, "Nenhum produto selecionado.");
                }else{
                    verProduto(getProdutinhos(), listarProdutosAcao, index);
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
                    verCategoria(getCategorias(), listarProdutosAcao, index);
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

        atualizarAutomaticamenteCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                toggleAtualizarAutomatico(atualizarAutomaticamenteCheckBox.isSelected());
            }
        });
        produtosLista.addMouseListener(new MouseAdapter() {
        });
    }

    Thread atualizarAutomaticoThread;
    void toggleAtualizarAutomatico(boolean automatico) {
        if(atualizarAutomaticoThread != null){
            atualizarAutomaticoThread.interrupt();
            atualizarAutomaticoThread = null;
        }

        if(automatico) {

            this.atualizarAutomaticoThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    atualizarRecorrente();
                }
            });
            this.atualizarAutomaticoThread.start();;
        }
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
        comboBoxPermissoes = new JComboBox();

        ArrayList<String> permissoesVigentes = PermissoesSingleton.getPermissoesSingleton().getPermissoes();
        ArrayList<String> todasPermissoes = PermissoesSingleton.getPermissoesSingleton().getTodasPermissoes();
        List<ComboItem> comboItems = todasPermissoes.stream().map(x -> new ComboItem(x, x)).collect(Collectors.toList());
        comboItems.forEach(x -> comboBoxPermissoes.addItem(x));
        comboBoxPermissoes.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ComboItem<String> item = (ComboItem<String>) comboBoxPermissoes.getSelectedItem();

                PermissoesSingleton.getPermissoesSingleton().removeTodasPermissoes();
                PermissoesSingleton.getPermissoesSingleton().adicionaPermissao(item.getKey());
            }
        });
        if(permissoesVigentes.size() > 0){
            for(int i = 0; i < comboBoxPermissoes.getItemCount(); i++){
                String item = ((ComboItem<String>) comboBoxPermissoes.getItemAt(i)).getKey();
                String permissao = permissoesVigentes.get(0);
                if(item.equals(permissao)){
                    comboBoxPermissoes.setSelectedIndex(i);
                }
            }
        }

        produtosLista.setModel(new DefaultListModel<String>());
        categoriasLista.setModel(new DefaultListModel<String>());

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
