package com.example.pedratesouraepapel.model;

public class Jogador {
private String nome;
private int pontos;

public Jogador(String nome) {
this.nome = nome;
this.pontos = 0;
}
public void acumulaPontos(){
    this.pontos += 1;
}
public String getNome(){
    return nome;
}
public int getPontos(){
    return pontos;
}
}


