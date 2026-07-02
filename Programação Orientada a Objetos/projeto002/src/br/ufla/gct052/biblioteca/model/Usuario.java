package br.ufla.gct052.biblioteca.model;

abstract class Usuario {
    private final String id;
    private String nome;
    private int emprestimosAtivos;

    public Usuario(String id, String nome){
        if(id == null || id.isBlank()){
            throw new IllegalArgumentException("ID nulo ou vazio.");
        }
        this.id = id;
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome nulo ou vazio.");
        }
        this.nome = nome;
        emprestimosAtivos = 0;
    }

    public String getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public int getEmprestimosAtivos(){
        return emprestimosAtivos;
    }

    protected void incrementarEmprestimos(){
        emprestimosAtivos += 1;
    }

    protected void decrementarEmprestimos(){
        if(emprestimosAtivos == 0){
            throw new IllegalArgumentException("Não existem empréstimos ativos.");
        }
        emprestimosAtivos -= 1;
    }

    public abstract int getLimiteEmprestimos();
    public abstract String tipo();
    public boolean podeEmprestar(){
        if(emprestimosAtivos < getLimiteEmprestimos()){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "Tipo de usuário: " + tipo() + "\nID: " + getId() + "\nNome: " + getNome() + "\nEmpréstimos ativos: " + getEmprestimosAtivos() + "\nLimite de empréstimos: " + getLimiteEmprestimos();
    }
}