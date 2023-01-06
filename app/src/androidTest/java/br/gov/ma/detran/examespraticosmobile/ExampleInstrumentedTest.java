package br.gov.ma.detran.examespraticosmobile;

import android.content.Context;
import android.os.RemoteException;
import android.service.carrier.CarrierMessagingService;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        new CarrierMessagingService.ResultCallback<AGC_Prova_Candidato>() {
            @Override
            public void onReceiveResult(@NonNull AGC_Prova_Candidato agc_prova_candidato) throws RemoteException {

            }
        };

        assertEquals("br.gov.ma.detran.examespraticosmobile", appContext.getPackageName());
    }
}
