package br.com.mello.conversor.models;

import java.util.ArrayList;
import java.util.Map;

public record MoedaApi(String base_code, String result, Map<String, Double> conversion_rates) {
}
