package br.ufla.gct052.biblioteca.model;

import java.util.ArrayList;

public class Biblioteca {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Exemplar> exemplares = new ArrayList<>();

    public void cadastrarUsuario(Usuario u){
        if(u == null){
            throw new IllegalArgumentException("Usuário nulo.");
        }

        String idUsuario = u.getId();
        for(Usuario i: usuarios){
            if(i.getId().equals(idUsuario)){
                throw new IllegalArgumentException("ID já cadastrado.");
            }
        }
        usuarios.add(u);
    }

    public void cadastrarExemplar(Exemplar e){
        if(e == null){
            throw new IllegalArgumentException("Exemplar nulo.");
        }

        String codigoExemplar = e.getCodigo();
        for(Exemplar i: exemplares){
            if(i.getCodigo().equals(codigoExemplar)){
                throw new IllegalArgumentException("Exemplar já cadastrado.");
            }
        }
        exemplares.add(e);
    }

    public Usuario buscarUsuarioPorId(String id){
        for(Usuario i: usuarios){
            if(i.getId().equals(id)){
                return i;
            }
        }
        throw new IllegalArgumentException("Usuário não encontrado.");
    }
    
    public Exemplar buscarExemplarPorCodigo(String codigo){
        for(Exemplar i: exemplares){
            if(i.getCodigo().equals(codigo)){
                return i;
            }
        }
        throw new IllegalArgumentException("Exemplar não encontrado.");
    }

    public boolean emprestar(String idUsuario, String codExemplar){
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        Exemplar exemplar = buscarExemplarPorCodigo(codExemplar);

        if(usuario.podeEmprestar() && exemplar.isDisponivel()){
            exemplar.emprestar();
            usuario.incrementarEmprestimos();
            return true;
        }
        return false;
    }

    public boolean devolver(String idUsuario, String codExemplar){
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        Exemplar exemplar = buscarExemplarPorCodigo(codExemplar);

        if(exemplar.isDisponivel()){
            return false;
        }
        
        exemplar.devolver();
        usuario.decrementarEmprestimos();
        return true;
    }

    public ArrayList<Usuario> getUsuarios(){
        return new ArrayList<>(usuarios);
    }

    public ArrayList<Exemplar> getExemplares(){
        return new ArrayList<>(exemplares);
    }
}
