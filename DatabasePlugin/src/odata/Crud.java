package odata;

import java.lang.StringBuilder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Crud extends Database {

	public Crud() {
		super();
	}
	
	public int salvar() {
		
		int qtdeSalvo = 0; 
		
		try {
			
			qtdeSalvo = prepareInsert.executeUpdate();
			
			prepareInsert.clearBatch();
			prepareInsert.clearParameters();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return qtdeSalvo;
	}
	
	public int deletar() {
		int qtdeDeletado = 0;
		
		try {
			
			qtdeDeletado = prepareDelete.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return qtdeDeletado;
	}

	public ResultSet listar(String prTabela, String prLimit) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ");
		sql.append(prTabela);
		sql.append(" limit ");
		
		if( prLimit != null ) {
			sql.append(prLimit);
		}else {
			sql.append("100");
		}
		
		Connection con = getConnection();
		ResultSet dados = null;
		
		try {
			
			Statement stmt = con.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY
			);
			
			dados = stmt.executeQuery(sql.toString());
			
			return dados;
			
		}catch(Exception e) {
			return null;
		}
		
	}
	

}