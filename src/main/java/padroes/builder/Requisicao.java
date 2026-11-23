package padroes.builder;

import java.util.Collections;
import java.util.Map;

public class Requisicao {

    private final String url;
    private final String metodo;
    private final Map<String, String> cabecalhos;
    private final String corpo;
    private final int timeoutSegundos;

    Requisicao(String url, String metodo, Map<String, String> cabecalhos, String corpo, int timeoutSegundos) {
        this.url = url;
        this.metodo = metodo;
        this.cabecalhos = Collections.unmodifiableMap(cabecalhos);
        this.corpo = corpo;
        this.timeoutSegundos = timeoutSegundos;
    }

    public String getUrl() {
        return url;
    }

    public String getMetodo() {
        return metodo;
    }

    public Map<String, String> getCabecalhos() {
        return cabecalhos;
    }

    public String getCorpo() {
        return corpo;
    }

    public int getTimeoutSegundos() {
        return timeoutSegundos;
    }
}
