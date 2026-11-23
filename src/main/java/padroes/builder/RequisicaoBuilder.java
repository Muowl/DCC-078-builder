package padroes.builder;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequisicaoBuilder {

    private String url;
    private String metodo = "GET";
    private final Map<String, String> cabecalhos = new LinkedHashMap<>();
    private String corpo;
    private int timeoutSegundos = 30;

    public RequisicaoBuilder url(String url) {
        this.url = url;
        return this;
    }

    public RequisicaoBuilder metodo(String metodo) {
        this.metodo = metodo.toUpperCase();
        return this;
    }

    public RequisicaoBuilder cabecalho(String nome, String valor) {
        this.cabecalhos.put(nome, valor);
        return this;
    }

    public RequisicaoBuilder corpo(String corpo) {
        this.corpo = corpo;
        return this;
    }

    public RequisicaoBuilder timeoutSegundos(int timeoutSegundos) {
        this.timeoutSegundos = timeoutSegundos;
        return this;
    }

    public Requisicao construir() {
        if (url == null || url.isBlank()) {
            throw new IllegalStateException("A URL é obrigatória");
        }
        if (timeoutSegundos <= 0) {
            throw new IllegalStateException("O timeout deve ser positivo");
        }
        if (metodo.equals("GET") && corpo != null) {
            throw new IllegalStateException("Requisição GET não pode ter corpo");
        }
        return new Requisicao(url, metodo, new LinkedHashMap<>(cabecalhos), corpo, timeoutSegundos);
    }
}
