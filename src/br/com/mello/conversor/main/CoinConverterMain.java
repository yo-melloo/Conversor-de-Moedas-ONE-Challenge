package br.com.mello.conversor.main;


import br.com.mello.conversor.models.CoinConverterMenu;
import br.com.mello.conversor.models.ConversorDeMoedas;

public class CoinConverterMain {
    public static void main(String[] args) {
        CoinConverterMenu menu = new CoinConverterMenu();
        ConversorDeMoedas conversor = new ConversorDeMoedas();
        boolean exec = true;

        while (exec) {
            exec = menu.iniciarMenu();
        }

    }
}

