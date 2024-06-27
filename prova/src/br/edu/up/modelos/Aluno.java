package br.edu.up.modelos;

public class Aluno {
    private Integer matricula;
    private String nome;
    private Double nota;
    public Aluno(Integer matricula, String nome, Double nota) {
        this.matricula = matricula;
        this.nome = nome;
        this.nota = nota;
    }
    public Integer getMatricula() {
        return matricula;
    }
    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Double getNota() {
        return nota;
    }
    public void setNota(Double nota) {
        this.nota = nota;
    }
    @Override
    public String toString() {
        return "Aluno [matricula=" + matricula + ", nome=" + nome + ", nota=" + nota + "]";
    }

    public String toCSV() {
        return matricula + ";" + nome + ";" + nota;
    }
    
}
