package com.example.restservice;

public class Solde {
    private String ref;
    private String solde;
    private String debit;
    public Solde(String ref,String solde,String debit){
        this.ref=ref;
        this.solde=solde;
        this.debit=debit;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
