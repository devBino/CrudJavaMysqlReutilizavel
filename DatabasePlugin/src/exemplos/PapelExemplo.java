package exemplos;

import java.lang.StringBuilder;
import java.util.Map;
import java.sql.ResultSet;

import odata.Crud;
import models.PapelModel;

public class PapelExemplo {

	public Crud crud;
	
	public PapelExemplo() {
		crud = new Crud();
	}
	
	public void salvarPapel() {
		
		PapelModel p = new PapelModel();
		
		Map<String,String> campos = p.getCampos();
		
		campos.put("nmPapel", campos.get("nmPapel")+"meu papel teste");
		campos.put("subTipo", campos.get("subTipo")+"3");
		campos.put("cdUsuario", campos.get("cdUsuario")+"2");
		campos.put("cotacao", campos.get("cotacao")+"909022.08");
		campos.put("cdTipo", campos.get("cdTipo")+"2");
		campos.put("taxaIr", campos.get("taxaIr")+"15.00");
		
		crud.prepararInsert("papel",campos);
		int regSalvos = crud.salvar();
		
		System.out.println(regSalvos + " Registro(s) salvo(s)...");
		
	}
	
	public void deletarPapel(int prId) {
		
		PapelModel p = new PapelModel();
		
		crud.prepararDelete("papel",p.getColunaId(),prId);
		int regDeletados = crud.deletar();
		
		System.out.println(regDeletados + " Registro(s) deletado(s)...");
		
	}
	
	public void getPapeis() {
		
		ResultSet dados = crud.listar("papel","1000");
		StringBuilder resultado = new StringBuilder();
		
		resultado.append("ID\t");
		resultado.append("DESCRIÇÃO\t");
		resultado.append("VALOR\n");
		resultado.append("----------------------------------------------------\n");
		
		try {
			
			dados.first();
			
			while( !dados.isAfterLast() ) {
				
				resultado.append(dados.getInt("cdPapel"));
				resultado.append("\t");
				resultado.append(dados.getString("nmPapel"));
				resultado.append("\t");
				resultado.append(dados.getDouble("taxaIr"));
				resultado.append("\n");
				
				dados.next();
			}
			
		}catch(Exception e) {
			System.err.println(e);
		}
		
		System.out.println(resultado.toString());
		
	}
	
	
}
