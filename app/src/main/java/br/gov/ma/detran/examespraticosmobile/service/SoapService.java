package br.gov.ma.detran.examespraticosmobile.service;

import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

public class SoapService {
    private static final String TAG = SoapService.class.getSimpleName();

    public static String callWSThreadSoapPrimitive(String strURL, String strSoapAction, SoapObject request, List<HeaderProperty> headers) {

        try {
            StringBuffer result;
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            envelope.dotNet = true;
            HttpTransportSE ht = new HttpTransportSE(strURL);
            ht.debug = true;
            ht.call(strSoapAction, envelope, headers);

            Log.d("dump Request: " ,ht.requestDump);
            Log.d("dump response: " ,ht.responseDump);

            SoapObject response = (SoapObject) envelope.getResponse();

            result = new StringBuffer(response.toString());
            Log.i(TAG, "result: " + result.toString());
            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
