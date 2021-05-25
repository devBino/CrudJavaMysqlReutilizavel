package models;

import java.util.Map;

public class Model {

	protected Map<String,String> camposInsert;
	protected String colunaId;
	protected String[] apelidosColunas;
	
	public Model(String prColunaId) {
		colunaId = prColunaId;
	}
	
	public Map<String,String> getCampos(){
		return camposInsert;
	}
	
	public String getColunaId() {
		return colunaId;
	}
	
	public String[] getApelidosColunas() {
		return apelidosColunas;
	}
	
}
