package br.ufla.gct052.biblioteca.model;

public class Exemplar {
    private String codigo;
    private String titulo;
    private boolean disponivel;

    public Exemplar(String codigo, String titulo){
        if(codigo == null || codigo.isBlank()){
            throw new IllegalArgumentException("Código nulo ou vazio.");
        }
        this.codigo = codigo;

        if(titulo == null || titulo.isBlank()){
            throw new IllegalArgumentException("Título nulo ou vazio.");
        }
        this.titulo = titulo;
        
        disponivel = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void emprestar(){
        if(isDisponivel() == false){
            throw new IllegalStateException("Exemplar não disponível.");
        }
        this.disponivel = false;
    }

    public void devolver(){
        this.disponivel = true;
    }

    @Override
    public String toString(){
        return "código: " + getCodigo() + ".\ntítulo: " + getTitulo();
    }
}
