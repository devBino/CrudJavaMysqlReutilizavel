package models;

import java.util.Map;
import java.util.HashMap;

public class Papel {

	private Map<String,String> camposInsert;
	
	public Papel() {
		setCamposInsert();
	}
	
	public void setCamposInsert() {
		
		camposInsert = new HashMap<String,String>();
		
		camposInsert.clear();
		
		camposInsert.put("nmPapel","string#1#");
		camposInsert.put("cotacao","double#2#");
		camposInsert.put("cdTipo","int#3#");
		camposInsert.put("subTipo","int#4#");
		camposInsert.put("taxaIr","double#5#");
		camposInsert.put("cdUsuario","int#6#");
		
	}
	
	public Map<String,String> getCampos(){
		return camposInsert;
	}
	
	public String getColunaId() {
		return "cdPapel";
	}
	
}
