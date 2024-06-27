package br.edu.up;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.up.modelos.Aluno;

public class Programa {
    public static void main(String[] args) {
        System.out.println("Iniciando o programa...");
        GerenciadorDeAluno gerenciador = new GerenciadorDeAluno();
        List<Aluno> alunos = gerenciador.lerArquivoAlunos("C:/_ws/segundo semestre/vinicius_sofware/desenv_software/prova-java/prova/src/br/edu/up/daos/alunos.csv");
        System.out.println("Leitura do arquivo concluída.");
        gerenciador.gravarResumo(alunos, "C:/_ws/segundo semestre/vinicius_sofware/desenv_software/prova-java/prova/src/br/edu/up/daos/resumo.csv");
        System.out.println("Resumo gravado com sucesso.");
    }
}

class GerenciadorDeAluno {
    public List<Aluno> lerArquivoAlunos(String nomeDoArquivo) {
        List<Aluno> alunos = new ArrayList<>();
        try {
            File arquivo = new File(nomeDoArquivo);
            Scanner leitor = new Scanner(arquivo, StandardCharsets.UTF_8.name());
            if (leitor.hasNextLine()) {
                leitor.nextLine(); // Ignorar o cabeçalho
            }
    
            int linhaNumero = 0;
            while (leitor.hasNextLine()) {
                linhaNumero++;
                String linha = leitor.nextLine().trim();
                if (linha.isEmpty()) {
                    System.out.println("Linha " + linhaNumero + " está vazia ou contém apenas espaços. Ignorada.");
                    continue;
                }
                String[] dados = linha.split(";");
                if (dados.length != 3) {
                    System.out.println("Linha " + linhaNumero + " não está no formato correto: " + linha);
                    continue;
                }
                try {
                    Integer matricula = Integer.parseInt(dados[0].trim());
                    String nome = dados[1].trim();
                    Double nota;
                    if (dados[2].trim().isEmpty()) {
                        nota = 0.0; // Tratar caso a nota esteja vazia como zero
                    } else {
                        nota = Double.valueOf(dados[2].trim().replace(",", "."));
                    }
                    Aluno aluno = new Aluno(matricula, nome, nota);
                    alunos.add(aluno);
                } catch (NumberFormatException e) {
                    System.out.println("Erro ao converter os dados na linha " + linhaNumero + ": " + linha + " - " + e.getMessage());
                }
            }
            leitor.close();
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
        return alunos;
    }
    

    public void gravarResumo(List<Aluno> alunos, String nomeDoArquivo) {
        int quantidadeAlunos = alunos.size();
        int aprovados = 0;
        int reprovados = 0;
        double menorNota = Double.MAX_VALUE;
        double maiorNota = Double.MIN_VALUE;
        double somaNotas = 0.0;

        for (Aluno aluno : alunos) {
            double nota = aluno.getNota();
            if (nota >= 6.0) {
                aprovados++;
            } else {
                reprovados++;
            }
            if (nota < menorNota) {
                menorNota = nota;
            }
            if (nota > maiorNota) {
                maiorNota = nota;
            }
            somaNotas += nota;
        }

        double media = somaNotas / quantidadeAlunos;

        try {
            FileWriter arquivoGravar = new FileWriter(nomeDoArquivo, StandardCharsets.UTF_8);
            PrintWriter gravador = new PrintWriter(arquivoGravar);
            gravador.println("Quantidade de alunos;Aprovados;Reprovados;Menor nota;Maior nota;Média");
            gravador.printf("%d;%d;%d;%.2f;%.2f;%.2f%n", quantidadeAlunos, aprovados, reprovados, menorNota, maiorNota, media);
            gravador.close();
        } catch (IOException e) {
            System.out.println("Não foi possível gravar o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


