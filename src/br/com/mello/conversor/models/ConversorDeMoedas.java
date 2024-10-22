package br.com.mello.conversor.models;

public class ConversorDeMoedas {
    private Moeda primeiraMoeda;
    private String segundaMoedaCod;

    public void adicionarMoedas(Moeda m1, String m2Cod){
        this.primeiraMoeda = m1;
        this.segundaMoedaCod = m2Cod;
    }

    public double converter(){
        double conversao = primeiraMoeda.getPossibilidades().get(segundaMoedaCod);
        return conversao * primeiraMoeda.getQuantidade();
    }

    @Override
    public String toString() {
        return String.format("""
                Primeira moeda: %s
                Segunda moeda: %s
                """, this.primeiraMoeda.getCodigo(), this.segundaMoedaCod);
    }
}
