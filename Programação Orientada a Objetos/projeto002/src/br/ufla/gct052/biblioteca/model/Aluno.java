package br.ufla.gct052.biblioteca.model;

public class Aluno extends Usuario{
    private String curso;
    private int periodo;

    public Aluno(String id, String nome, String curso, int periodo){
        super(id, nome);
        if(curso == null || curso.isBlank()){
            throw new IllegalArgumentException("Curso nulo ou vazio.");
        }
        this.curso = curso;

        if(periodo < 1 || periodo > 12){
            throw new IllegalArgumentException("Período inválido.");
        }
        this.periodo = periodo;
    }

    public String getCurso(){
        return curso;
    }

    public int getPeriodo(){
        return periodo;
    }

    @Override
    public int getLimiteEmprestimos(){
        return 3;
    }

    @Override
    public String tipo(){
        return "Aluno";
    }
}