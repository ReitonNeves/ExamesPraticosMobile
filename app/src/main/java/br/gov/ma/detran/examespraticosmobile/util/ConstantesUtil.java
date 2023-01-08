package br.gov.ma.detran.examespraticosmobile.util;

public class ConstantesUtil {

    public Ambiente ambiente;
    public String URL_REST = "";
    public String SALT_TOKEN = "";
    public String URL_WEBSERVICE_RESULTADO_EXAME_DETRANNET;

    public ConstantesUtil(){
        setValoresLocalhost();
    }

    public ConstantesUtil(Ambiente ambiente){
        if (ambiente == Ambiente.DESENVOLVIMENTO){
            setValoresDesenvolvimento();
        } else if (ambiente == Ambiente.PRODUCAO){
            setValoresProducao();
        }
    }

    private void setValoresLocalhost() {
        ambiente = Ambiente.LOCALHOST;
        URL_REST = "http://localhost:8080/ExamesPraticosRest/rest";
        URL_WEBSERVICE_RESULTADO_EXAME_DETRANNET = "https://ws.homologacao.detrannet.detran.ma.gov.br/wsResultadoExames/wsResultadoExames.asmx";
//        URL_REST = "http://192.168.0.170:7080/ExamesPraticosRest/rest";
//        URL_REST = "http://192.168.0.149:9080/ExamesPraticosRest/rest";
//        URL_REST = "http://desenv:8080/ExamesPraticosRest/rest";
        SALT_TOKEN = "asldhasdhlAoason9082108901";
    }

    private void setValoresDesenvolvimento() {
        ambiente = Ambiente.DESENVOLVIMENTO;
        URL_REST = "http://desenv:8080/ExamesPraticosRest/rest";
        URL_WEBSERVICE_RESULTADO_EXAME_DETRANNET = "https://ws.homologacao.detrannet.detran.ma.gov.br/wsResultadoExames/wsResultadoExames.asmx";
        SALT_TOKEN = "asldhasdhlAoason9082108901";
    }

    private void setValoresProducao() {
        ambiente = Ambiente.PRODUCAO;
        URL_REST = "http://licenciamento.detran.ma.gov.br/ExamesPraticosRest/rest";
        SALT_TOKEN = "987@@9db9a$$s#8db$u9a!@ns8du9a#s8du9as8du";
    }

    public String getURL_WEBSERVICE_RESULTADO_EXAME_DETRANNET() {
        return URL_WEBSERVICE_RESULTADO_EXAME_DETRANNET;
    }

    public void setURL_WEBSERVICE_RESULTADO_EXAME_DETRANNET(String URL_WEBSERVICE_RESULTADO_EXAME_DETRANNET) {
        this.URL_WEBSERVICE_RESULTADO_EXAME_DETRANNET = URL_WEBSERVICE_RESULTADO_EXAME_DETRANNET;
    }

    public enum Ambiente {
        LOCALHOST(1),
        DESENVOLVIMENTO(2),
        PRODUCAO(3);

        private int id;

        Ambiente(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

}
