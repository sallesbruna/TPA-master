package aspecto;

import anotacao.RoleAdmin;
import anotacao.RoleUser2;
import anotacao.RoleUser3;

import java.util.ArrayList;
import java.util.Arrays;

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

}
