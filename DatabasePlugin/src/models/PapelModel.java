package models;

import java.util.HashMap;

public class PapelModel extends Model {

	public PapelModel() {
		super("cdPapel");
		setCamposInsert();
		setApelidosColunas();
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
	
	public void setApelidosColunas() {
		
		apelidosColunas = new String[] {
			"ID","PAPEL","COTAÇÃO","TIPO","SUB TIPO","TAXA IR","USUARIO"
		};
		
	}
	
}
