package aspecto;

import anotacao.RoleAdmin;
import anotacao.RoleUser1;
import anotacao.RoleUser2;
import anotacao.RoleUser3;

import java.util.ArrayList;

public class PermissoesSingleton {

    private static PermissoesSingleton instance = null;
    private ArrayList<String> permissoes = new ArrayList<>();

    private PermissoesSingleton(){
    }

    public synchronized static PermissoesSingleton getPermissoesSingleton() {
        if(instance != null) return instance;
        return instance = new PermissoesSingleton();
    }

    public boolean possuiPermissao(String permissao){
        return permissoes.stream().anyMatch(x -> x.equals(permissao));
    }

    public void adicionaPermissao(String permissao){
        permissoes.add(permissao);
    }

    public void removePermissao(String permissao){
        permissoes.remove(permissao);
    }

    public ArrayList<String> getPermissoes() {
        return permissoes;
    }

    public void removeTodasPermissoes() {
        permissoes = new ArrayList<>();
    }

    public ArrayList<String> getTodasPermissoes() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(RoleAdmin.PERMISSAO);
        strings.add(RoleUser2.PERMISSAO);
        strings.add(RoleUser3.PERMISSAO);
        strings.add(RoleUser1.PERMISSAO);
        return strings;
    }

}
