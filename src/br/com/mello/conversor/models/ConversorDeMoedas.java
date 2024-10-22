package br.com.mello.conversor.models;

public class ConversorDeMoedas {
    private Moeda primeiraMoeda;
    private Moeda segundaMoeda;

    public void adicionarMoedas(Moeda m1, Moeda m2){
        this.primeiraMoeda = m1;
        this.segundaMoeda = m2;
    }

    public double converter(){
        double conversao = primeiraMoeda.getPossibilidades().get(segundaMoeda.getCodigo());
        return conversao * primeiraMoeda.getQuantidade();
    }

    @Override
    public String toString() {
        return String.format("""
                Primeira moeda: %s
                Segunda moeda: %s
                """, this.primeiraMoeda, this.segundaMoeda);
    }
}
