package br.gov.ma.detran.examespraticosmobile.lixeira;

import java.io.Serializable;

public class RetornoResultadoExames implements Serializable {
	private static final long serialVersionUID = 1L;
	private int tipoRetorno;
	private String mensagemRetorno;	
		
	public int getTipoRetorno() {
		return tipoRetorno;
	}
	public void setTipoRetorno(int tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}
	public String getMensagemRetorno() {
		return mensagemRetorno;
	}
	public void setMensagemRetorno(String mensagemRetorno) {
		this.mensagemRetorno = mensagemRetorno;
	}
	
}
