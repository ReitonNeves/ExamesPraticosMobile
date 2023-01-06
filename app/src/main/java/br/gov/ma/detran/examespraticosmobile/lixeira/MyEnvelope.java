package br.gov.ma.detran.examespraticosmobile.lixeira;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class MyEnvelope extends SoapSerializationEnvelope {

    public MyEnvelope(int version) {
        super(version);
    }

    @Override
    public void write(XmlSerializer writer) throws IOException {
        env = "http://schemas.xmlsoap.org/soap/envelope/";
        String tem = "https://www.detran.ma.gov.br/";
        writer.startDocument("UTF-8", true);
        writer.setPrefix("SOAP-ENV", env);
        writer.setPrefix("ns1", tem);
        writer.startTag(env, "Envelope");
        writer.startTag(env, "Body");
        writer.startTag(tem, "det:NJXFB003");
        writeBody(writer);
        writer.endTag(tem, "det:NJXFB003");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();
    }
}
