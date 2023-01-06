package br.gov.ma.detran.examespraticosmobile.service;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modeloEspecializada.ResultadoExameMap;
import br.gov.ma.detran.examespraticosmobile.views.FecharAgendaActivity;
import gnu.trove.TByteCollection;

public class ResultadoService extends AsyncTask<String, Void, String> {

    private FecharAgendaActivity activity;
    private String soapAction;
    private String methodName;
    private PropertyInfo properties;

    private final static String TAG = ResultadoService.class.getSimpleName();
    public final static String SOAP_ACTION = "https://www.detran.ma.gov.br/";
    public final static String NAME_SPACE = "https://www.detran.ma.gov.br/";
    public final static String URL = "https://ws.homologacao.detrannet.detran.ma.gov.br/wsResultadoExames/wsResultadoExames.asmx?WSDL";
    public final static String METHOD_NAME = "NJXFB003";
    public final static String ENVIAR_RESULTADO_REQUEST = SOAP_ACTION + METHOD_NAME;
    private ResultadoExameMap resultadoExameMap;

    public ResultadoService(FecharAgendaActivity activity, String soapAction, String methodName, ResultadoExameMap resultadoExameMap) {
        this.activity = activity;
        this.methodName = methodName;
        this.soapAction = soapAction;
        this.resultadoExameMap = resultadoExameMap;
    }

    @Override
    protected String doInBackground(String... params) {
        List<HeaderProperty> headers = new ArrayList<>();
        headers.add(new HeaderProperty("Authorization", "Basic MDQ2Mjc2MjUzMDg6I1JubThlbGV0cm8="));
        //create a new soap request object
        SoapObject request = new SoapObject(NAME_SPACE, methodName);
        //add properties for soap object
        resultadoExameMap.forEach((key, value) -> {
            request.addProperty(key, value);
        });
        //request to server and get Soap Primitive response
        return  SoapService.callWSThreadSoapPrimitive(URL, soapAction, request, headers);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result == null) {
            Log.i(TAG, "Sem resultado.");
        } else {
            //invoke call back method of Activity
            activity.callBackDataFromAsyncTask(result);
        }
    }
}
