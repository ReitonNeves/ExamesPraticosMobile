package br.gov.ma.detran.examespraticosmobile.lixeira;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.util.DataHoraUtil;

public class ResultadoExames implements KvmSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String senha;
	private String data;
	private String tipo;
	private String renach;
	private String local;
	private String turma;
	private String nome;
	private String resultado;
	private String cpf_examinador;
	private String numeroprova;
	private String numeroacertos;
	private String nota;

	public ResultadoExames(AGC_Prova_Candidato exame) {
		this.usuario = DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());;
		this.senha = DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());;
		this.data = "20220825";//DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());
		this.tipo = DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());
		this.renach = DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());
		this.local = DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());
		this.turma = DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());
		this.nome = DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());
		this.resultado = DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());
		this.cpf_examinador = DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());
		this.numeroprova = DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());;
		this.numeroacertos = DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());;
		this.nota = DataHoraUtil.retornarDatayyyyMMdd(exame.getDataExame());;
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
			case 0:
				return usuario;
			case 1:
				return senha;
			case 2:
				return data;
			case 3:
				return tipo;
			case 4:
				return renach;
			case 5:
				return local;
			case 6:
				return turma;
			case 7:
				return nome;
			case 8:
				return resultado;
			case 9:
				return cpf_examinador;
			case 10:
				return numeroprova;
			case 11:
				return numeroacertos;
			case 12:
				return nota;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 13;
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
			case 0:
				this.usuario = value.toString();
			case 1:
				this.senha = value.toString();
			case 2:
				this.data = value.toString();
			case 3:
				this.tipo = value.toString();
			case 4:
				this.renach = value.toString();
			case 5:
				this.local = value.toString();
			case 6:
				this.turma = value.toString();
			case 7:
				this.nome = value.toString();
			case 8:
				this.resultado = value.toString();
			case 9:
				this.cpf_examinador = value.toString();
			case 10:
				this.numeroprova = value.toString();
			case 11:
				this.numeroacertos = value.toString();
			case 12:
				this.nota = value.toString();
		}
	}

	@Override
	public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
		switch (index) {
			case 0:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_USUARIO";
				break;
			case 1:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_SENHA";
				break;
			case 2:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_DATA";
				break;
			case 3:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_TIPO";
				break;
			case 4:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_RENACH";
				break;
			case 5:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_LOCAL";
				break;
			case 6:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_TURMA";
				break;
			case 7:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_NOME";
				break;
			case 8:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_RESULTADO";
				break;
			case 9:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_CPF_EXAMINADOR";
				break;
			case 10:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_NUMEROPROVA";
				break;
			case 11:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_NUMEROACERTOS";
				break;
			case 12:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "WSIN_NOTA";
				break;
		}
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRenach() {
		return renach;
	}

	public void setRenach(String renach) {
		this.renach = renach;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getCpf_examinador() {
		return cpf_examinador;
	}

	public void setCpf_examinador(String cpf_examinador) {
		this.cpf_examinador = cpf_examinador;
	}

	public String getNumeroprova() {
		return numeroprova;
	}

	public void setNumeroprova(String numeroprova) {
		this.numeroprova = numeroprova;
	}

	public String getNumeroacertos() {
		return numeroacertos;
	}

	public void setNumeroacertos(String numeroacertos) {
		this.numeroacertos = numeroacertos;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
}
