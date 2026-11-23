package padroes.builder;

public class DiretorRequisicao {

    public Requisicao consultaJson(String url) {
        return new RequisicaoBuilder()
                .url(url)
                .cabecalho("Accept", "application/json")
                .construir();
    }

    public Requisicao envioJson(String url, String corpo) {
        return new RequisicaoBuilder()
                .url(url)
                .metodo("POST")
                .cabecalho("Content-Type", "application/json")
                .corpo(corpo)
                .timeoutSegundos(60)
                .construir();
    }
}
