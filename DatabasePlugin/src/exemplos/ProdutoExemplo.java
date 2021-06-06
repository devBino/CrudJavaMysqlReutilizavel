package exemplos;

import java.util.Map;
import java.util.ArrayList;
import java.util.Random;
import odata.Crud;
import models.ProdutoModel;

public class ProdutoExemplo {

	private Crud crud;
	
	public ProdutoExemplo() {
		crud = new Crud();
	}
	
	public void salvarProduto() {
		
		ProdutoModel produtoModel = new ProdutoModel();
		
		Map<String,String> campos = produtoModel.getCampos();
		
		Random rand = new Random();
		int numeroBase = rand.nextInt(1000); 
		
		campos.put("cdCategoria", campos.get("cdCategoria") + "1" );
		campos.put("cdFornecedor",campos.get("cdFornecedor") + "1");
		campos.put("codBar", campos.get("codBar") + "1000" + String.valueOf(numeroBase));
		campos.put("nmProduto", campos.get("nmProduto") + "PRODUTO TESTE " + String.valueOf(numeroBase));
		campos.put("valor",campos.get("valor") + String.valueOf(numeroBase)+".99");
		campos.put("qtdeMin",campos.get("qtdeMin") + "10");
		campos.put("dtUpdate",campos.get("dtUpdate") + "2021-06-06 11:55:45");
		campos.put("cdUsuarioUpdate",campos.get("cdUsuarioUpdate") + "1");
		
		crud.prepararInsert("produto", campos);
		int regSalvos = crud.salvar();
		
		System.out.println(regSalvos + " Registro(s) salvo(s)...");

	}
	
	public void alterarProduto(int prId) {
		
	}
	
	public void deletarProduto(int prId) {
		
		ProdutoModel produtoModel = new ProdutoModel();
		crud.prepararDelete("produto",produtoModel.getColunaId(), prId);
		int regDeletados = crud.deletar();
		
		System.out.println(regDeletados + " Registro(s) deletado(s)...");
		
	}
	
	public void getProdutos() {
		
		ProdutoModel produtoModel = new ProdutoModel();
		ArrayList<String[]> listaProdutos = crud.prepararLista( crud.listar("produto","1000"), produtoModel );
		StringBuilder resultado = new StringBuilder();
		
		for(String coluna : produtoModel.getApelidosColunas()) {
			resultado.append(coluna + "\t");
		}
		
		resultado.append("\n");
		
		for(int i=0; i<listaProdutos.size(); i++) {
			
			for(int j=0; j<listaProdutos.get(i).length; j++) {
				resultado.append(listaProdutos.get(i)[j] + "\t");
			}
			
			resultado.append("\n");
			
		}
		
		System.out.println(resultado.toString());
		
		
	}
	
}
