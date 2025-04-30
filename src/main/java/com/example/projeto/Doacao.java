package com.example.projeto;

import java.time.LocalDate;

public class Doacao {

    private int id;
    private Doador doador;
    private Entidade entidade;
    private String item;
    private int quantity;
    private LocalDate donationDate;

    public Doacao() {
    }

    public Doacao(Doador doador, Entidade entidade, String item, int quantity, LocalDate donationDate) {
        this.doador = doador;
        this.entidade = entidade;
        this.item = item;
        this.quantity = quantity;
        this.donationDate = donationDate;
    }

    public Doacao(int id, Doador donor, Entidade entidade, String item, int quantity, LocalDate donationDate) {
        this.id = id;
        this.doador = doador;
        this.entidade = entidade;
        this.item = item;
        this.quantity = quantity;
        this.donationDate = donationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Doador getDoador() {
        return doador;
    }

    public void setDoador(Doador doador) {
        this.doador = doador;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }
}