package br.com.mello.conversor.main;


import br.com.mello.conversor.models.CoinConverterMenu;
import br.com.mello.conversor.models.ConversorDeMoedas;

public class CoinConverterAppPlay {
    public static void main(String[] args) {
        CoinConverterMenu menu = new CoinConverterMenu();
        ConversorDeMoedas conversorDeMoedas = new ConversorDeMoedas();
        boolean exec = true;

        while (exec) {
            exec = menu.iniciarMenu();
        }

    }
}

