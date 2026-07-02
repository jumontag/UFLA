package br.ufla.gct052.biblioteca.model;

public class Servidor extends Usuario{
    private String setor;
    private String cargo;

    public Servidor(String id, String nome, String setor, String cargo){
        super(id, nome);
        if(setor == null || setor.isBlank()){
            throw new IllegalArgumentException("Setor nulo ou vazio.");
        }
        this.setor = setor;

        if(cargo == null || cargo.isBlank()){
            throw new IllegalArgumentException("Cargo nulo ou vazio.");
        }
        this.cargo = cargo;
    }

    public String getSetor() {
        return setor;
    }

    public String getCargo() {
        return cargo;
    }

    @Override
    public int getLimiteEmprestimos(){
        return 4;
    }

    @Override
    public String tipo(){
        return "Servidor";
    }
}
