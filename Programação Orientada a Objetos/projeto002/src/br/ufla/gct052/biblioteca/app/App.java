package br.ufla.gct052.biblioteca.app;

import br.ufla.gct052.biblioteca.model.*;

public class App {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        // Cadastrando usuários
        Aluno a1 = new Aluno("001", "Chaves", "Engenharia de Software", 5);
        Professor p1 = new Professor("002", "Girafales", "ICTIN", "Mestre");
        Servidor s1 = new Servidor("003", "Jaiminho", "Secretaria", "Assistente");
        biblioteca.cadastrarUsuario(a1);
        biblioteca.cadastrarUsuario(p1);
        biblioteca.cadastrarUsuario(s1);
        System.out.println("-- Usuários cadastrados.");

        // Cadastrando exemplares
        Exemplar e1 = new Exemplar("101", "Conceitos de Linguagens de Programação");
        Exemplar e2 = new Exemplar("102", "Fundamentos da Programação de Computadores");
        Exemplar e3 = new Exemplar("103", "Algoritmos: Teoria e Prática");
        Exemplar e4 = new Exemplar("104", "Estatística Básica");
        Exemplar e5 = new Exemplar("105", "Cálculo Numérico");
        Exemplar e6 = new Exemplar("106", "Análise Numérica");
        biblioteca.cadastrarExemplar(e1);
        biblioteca.cadastrarExemplar(e2);
        biblioteca.cadastrarExemplar(e3);
        biblioteca.cadastrarExemplar(e4);
        biblioteca.cadastrarExemplar(e5);
        biblioteca.cadastrarExemplar(e6);
        System.out.println("-- Exemplares cadastrados.");
        
        System.out.println("\nUsuários cadastrados:");
        for (int i = 0; i < biblioteca.getUsuarios().size(); i++) {
            System.out.println("\n" + biblioteca.getUsuarios().get(i));
        }

        // ArrayList listaUsuarios = biblioteca.getUsuarios();
        // for (int i = 0; i < listaUsuarios.size(); i++) {
        //     System.out.println(listaUsuarios.get(i));
        // }

        // for(Usuario u: usuarios){
        //     System.out.println(u.toString());
        //     System.out.println(u.getLimiteEmprestimos());
        // } 

        System.out.println("\n-- Emprestando livros ao aluno.");
        biblioteca.emprestar("001", "101");
        System.out.println("- Primeiro livro emprestado.\nNúmero de empréstimos ativos: " + a1.getEmprestimosAtivos());
        biblioteca.emprestar("001", "102");
        System.out.println("- Segundo livro emprestado.\nNúmero de empréstimos ativos: " + a1.getEmprestimosAtivos());
        biblioteca.emprestar("001", "103");
        System.out.println("- Terceiro livro emprestado.\nNúmero de empréstimos ativos: " + a1.getEmprestimosAtivos());
        biblioteca.emprestar("001", "104");
        System.out.println("- Quarto livro (não foi emprestado).\nNúmero de empréstimos ativos: " + a1.getEmprestimosAtivos());

        System.out.println("\n-- Devolvendo todos os livros emprestados ao aluno.");
        biblioteca.devolver("001", "101");
        biblioteca.devolver("001", "102");
        biblioteca.devolver("001", "103");
        System.out.println("Número de empréstimos ativos no nome do aluno: " + a1.getEmprestimosAtivos());

        System.out.println("\n-- Emprestando livros ao professor.");
        biblioteca.emprestar("002", "101");
        System.out.println("- Primeiro livro emprestado.\nNúmero de empréstimos ativos: " + p1.getEmprestimosAtivos());
        biblioteca.emprestar("002", "102");
        System.out.println("- Segundo livro emprestado.\nNúmero de empréstimos ativos: " + p1.getEmprestimosAtivos());
        biblioteca.emprestar("002", "103");
        System.out.println("- Terceiro livro emprestado.\nNúmero de empréstimos ativos: " + p1.getEmprestimosAtivos());
        biblioteca.emprestar("002", "104");
        System.out.println("- Quarto livro emprestado.\nNúmero de empréstimos ativos: " + p1.getEmprestimosAtivos());
        biblioteca.emprestar("002", "105");
        System.out.println("- Quinto livro emprestado.\nNúmero de empréstimos ativos: " + p1.getEmprestimosAtivos());
        biblioteca.emprestar("002", "105");
        System.out.println("- Sexto livro (não foi emprestado).\nNúmero de empréstimos ativos: " + p1.getEmprestimosAtivos());

        System.out.println("\n-- Tentando emprestar ao servidor um livro que está emprestado ao professor.");
        System.out.println("Antes: Empréstimos ativos para o servidor " + s1.getNome() + " | " + s1.getId() + ": " + s1.getEmprestimosAtivos());
        if(biblioteca.emprestar("003", "103") == false){
            System.out.println("- Livro não emprestado.");
        } else {
            biblioteca.emprestar("003", "103");
        } 
        System.out.println("Depois: Empréstimos ativos para o servidor " + s1.getNome() + " | " + s1.getId() + ": " + s1.getEmprestimosAtivos());

        Aluno a2 = new Aluno("001", "Chiquinha", "Engenharia Elétrica", 2);
        try {
            biblioteca.cadastrarUsuario(a2);
        } catch (Exception e) {
            System.out.println("\n! Tentativa de cadastro de usuário com id duplicado é impedida.");
        }

        Exemplar e7 = new Exemplar("101", "Harry Potter");
        try {
            biblioteca.cadastrarExemplar(e7);
        } catch (Exception e) {
            System.out.println("! Tentativa de cadastro de exemplar com código duplicado é impedida.");
        }
    }
}