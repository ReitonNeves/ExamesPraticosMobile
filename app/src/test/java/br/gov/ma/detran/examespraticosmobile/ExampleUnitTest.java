package br.gov.ma.detran.examespraticosmobile;

import org.junit.Test;

import static org.junit.Assert.*;

import br.gov.ma.detran.examespraticosmobile.util.DataHoraUtil;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(true, DataHoraUtil.valida("2022-09-08"));
        assertEquals(false, DataHoraUtil.valida("25-12/"));
        assertEquals(false, DataHoraUtil.valida("2022-12-32"));
        assertEquals(false, DataHoraUtil.valida("2022-13-13"));
        assertEquals(false, DataHoraUtil.valida(""));
    }
}