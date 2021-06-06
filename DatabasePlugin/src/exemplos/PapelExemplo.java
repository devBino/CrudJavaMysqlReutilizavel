package exemplos;

import java.util.Map;
import java.util.ArrayList;
import odata.Crud;
import models.PapelModel;


/**
 * 
 * @author Fernando Bino
 * @description
 * 	Essa classe de exemplo, vai utilizar uma model PapelModel, e essa classe é PapelExemplo
 *  apenas pra exemplificar um entidade do banco de dados chamada Papel
 *  que no caso são papeis de investimentos financeiros.
 *  
 *  para manipulação dos campos, essa model por via de regra deve 
 *  conter um atributo camposInsert acessado por getCampos()
 *  que retorna um Map<String,String> que será utilizado para manipulação 
 *  das colunas da tabela bem como dos seus valores
 *
 */
public class PapelExemplo {

	private Crud crud;
	
	public PapelExemplo() {
		crud = new Crud();
	}
	
	public void salvarPapel() {
		
		PapelModel papelModel = new PapelModel();
		
		Map<String,String> campos = papelModel.getCampos();
		
		campos.put("nmPapel", campos.get("nmPapel")+"OZ1D");
		campos.put("subTipo", campos.get("subTipo")+"4");
		campos.put("cdUsuario", campos.get("cdUsuario")+"2");
		campos.put("cotacao", campos.get("cotacao")+"70345.23");
		campos.put("cdTipo", campos.get("cdTipo")+"3");
		campos.put("taxaIr", campos.get("taxaIr")+"0.00");
		
		crud.prepararInsert("papel",campos);
		int regSalvos = crud.salvar();
		
		System.out.println(regSalvos + " Registro(s) salvo(s)...");
		
	}
	
	public void alterarPapel(int prId) {
		
	}
	
	public void deletarPapel(int prId) {
		
		PapelModel papelModel = new PapelModel();
		
		crud.prepararDelete("papel",papelModel.getColunaId(),prId);
		int regDeletados = crud.deletar();
		
		System.out.println(regDeletados + " Registro(s) deletado(s)...");
		
	}
	
	public void getPapeis() {
		
		PapelModel papelModel = new PapelModel();
		ArrayList<String[]> listaPapeis = crud.prepararLista( crud.listar("papel","1000"), papelModel );
		StringBuilder resultado = new StringBuilder();
		
		for(String coluna : papelModel.getApelidosColunas()){
			resultado.append(coluna + "\t");
		}
		
		resultado.append("\n");
		
		for(int i=0; i<listaPapeis.size(); i++) {
			
			for(int j=0; j<listaPapeis.get(i).length; j++) {
				resultado.append(listaPapeis.get(i)[j] + "\t");
			}
			
			resultado.append("\n");
			
		}
		
		System.out.println(resultado.toString());
		
	}
	
	
}
