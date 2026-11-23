package padroes.builder;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequisicaoBuilderTest {

    @Test
    void deveConstruirRequisicaoComValoresPadrao() {
        Requisicao requisicao = new RequisicaoBuilder().url("https://api.exemplo.com/itens").construir();

        assertEquals("https://api.exemplo.com/itens", requisicao.getUrl());
        assertEquals("GET", requisicao.getMetodo());
        assertEquals(30, requisicao.getTimeoutSegundos());
        assertNull(requisicao.getCorpo());
        assertEquals(Map.of(), requisicao.getCabecalhos());
    }

    @Test
    void deveConstruirRequisicaoCompletaEncadeandoChamadas() {
        Requisicao requisicao = new RequisicaoBuilder()
                .url("https://api.exemplo.com/itens")
                .metodo("post")
                .cabecalho("Content-Type", "application/json")
                .corpo("{\"nome\":\"caneta\"}")
                .timeoutSegundos(10)
                .construir();

        assertEquals("POST", requisicao.getMetodo());
        assertEquals("{\"nome\":\"caneta\"}", requisicao.getCorpo());
        assertEquals(10, requisicao.getTimeoutSegundos());
        assertEquals("application/json", requisicao.getCabecalhos().get("Content-Type"));
    }

    @Test
    void deveExigirUrl() {
        assertThrows(IllegalStateException.class, () -> new RequisicaoBuilder().construir());
        assertThrows(IllegalStateException.class, () -> new RequisicaoBuilder().url(" ").construir());
    }

    @Test
    void deveRejeitarTimeoutInvalidoECorpoEmGet() {
        assertThrows(IllegalStateException.class,
                () -> new RequisicaoBuilder().url("https://a.com").timeoutSegundos(0).construir());
        assertThrows(IllegalStateException.class,
                () -> new RequisicaoBuilder().url("https://a.com").corpo("x").construir());
    }

    @Test
    void deveGerarObjetoImutavelEIndependenteDoBuilder() {
        RequisicaoBuilder builder = new RequisicaoBuilder().url("https://a.com").cabecalho("A", "1");
        Requisicao primeira = builder.construir();
        builder.cabecalho("B", "2");

        assertEquals(1, primeira.getCabecalhos().size());
        assertThrows(UnsupportedOperationException.class, () -> primeira.getCabecalhos().put("C", "3"));
    }

    @Test
    void diretorDeveMontarConsultaJson() {
        Requisicao requisicao = new DiretorRequisicao().consultaJson("https://a.com/x");

        assertEquals("GET", requisicao.getMetodo());
        assertEquals("application/json", requisicao.getCabecalhos().get("Accept"));
    }

    @Test
    void diretorDeveMontarEnvioJson() {
        Requisicao requisicao = new DiretorRequisicao().envioJson("https://a.com/x", "{}");

        assertEquals("POST", requisicao.getMetodo());
        assertEquals("{}", requisicao.getCorpo());
        assertEquals(60, requisicao.getTimeoutSegundos());
        assertEquals("application/json", requisicao.getCabecalhos().get("Content-Type"));
    }
}
