package odata;

import java.util.Map;
import java.lang.StringBuilder;

public class Crud extends Database {

	public Crud() {
		super();
	}
	
	public void prepararInsert(String prTabela, Map<String,String> prCampos) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("insert into ");
		sql.append(prTabela);
		sql.append(" ");
		sql.append("(");
		sql.append(montarColunas(prCampos));
		sql.append(")");
		sql.append(" values ");
		sql.append("(");
		sql.append(montarInterrogacoesValores(prCampos));
		sql.append(");");
		
		sqlInsert = sql.toString();
		
		prepararStatementInsert(prCampos);
		
	}
	

}