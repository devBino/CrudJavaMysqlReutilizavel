package odata;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import models.Model;

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
	
	public ArrayList<String[]> prepararLista(ResultSet dados, Model model){
		
		ArrayList<String[]> lista = new ArrayList<String[]>();
		
		try {
			
			dados.first();
			
			while( !dados.isAfterLast() ) {
				
				String[] arrLinha = new String[model.getCampos().size() + 1];
				
				arrLinha[0] = Integer.toString(dados.getInt(model.getColunaId()));
				
				for(String chave : model.getCampos().keySet() ) {
					
					String[] arrInfo = model.getCampos().get(chave).split("#");
					int indiceColuna = Integer.parseInt( arrInfo[1] );
					
					switch(arrInfo[0]) {
					
						case "string":
							arrLinha[indiceColuna] = dados.getString(chave); 		
							break;
							
						case "int":
							arrLinha[indiceColuna] =  Integer.toString(dados.getInt(chave));
							break;
							
						case "double":
							arrLinha[indiceColuna] = Double.toString(dados.getDouble(chave));
							break;
							
						default:
							break;
					}
					
				}
				
				lista.add(arrLinha);
				
				dados.next();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	

}