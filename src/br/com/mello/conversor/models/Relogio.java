package br.com.mello.conversor.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Relogio {
    private String dataEHoraFormatacaoPadrao;
    private String dataEHoraFormatacaoSimples;
    private String dataApenas;

    public String getDataEHoraFormatacaoPadrao() {
        this.atualizarRelogio();
        return dataEHoraFormatacaoPadrao;
    }

    public String getDataEHoraFormatacaoSimples() {
        this.atualizarRelogio();
        return dataEHoraFormatacaoSimples;
    }

    public String getDataApenas() {
        this.atualizarRelogio();
        return dataApenas;
    }

    public void atualizarRelogio(){
        //Importando e formatando data e hora para geração de logs ao final
        LocalDateTime dataEhora = LocalDateTime.now(); // pega "agora"
        DateTimeFormatter formatacaoPadrao = DateTimeFormatter.ofPattern("(dd/MM/yyyy) HH:mm:ss");
        DateTimeFormatter formatacaoSimples = DateTimeFormatter.ofPattern("(dd/MM) HH:mm:ss");
        DateTimeFormatter dataApenasF = DateTimeFormatter.ofPattern("dd.MMMM.yyyy");

        this.dataEHoraFormatacaoPadrao = String.format("[%s]", dataEhora.format(formatacaoPadrao));
        this.dataEHoraFormatacaoSimples = String.format("[%s]", dataEhora.format(formatacaoSimples));
        this.dataApenas = dataEhora.format(dataApenasF);
    }
}
