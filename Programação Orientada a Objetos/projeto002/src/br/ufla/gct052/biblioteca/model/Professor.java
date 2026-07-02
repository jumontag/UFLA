package br.ufla.gct052.biblioteca.model;

public class Professor extends Usuario{
    private String departamento;
    private String titulacao;

    public Professor(String id, String nome, String departamento, String titulacao){
        super(id, nome);
        if(departamento == null || departamento.isBlank()){
            throw new IllegalArgumentException("Departamento nulo ou vazio.");
        }
        this.departamento = departamento;
        
        if(titulacao == null || titulacao.isBlank()){
            throw new IllegalArgumentException("Titulação nula ou vazia.");
        }
        this.titulacao = titulacao;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getTitulacao() {
        return titulacao;
    }

    @Override
    public int getLimiteEmprestimos(){
        return 5;
    }

    @Override
    public String tipo(){
        return "Professor";
    }
}