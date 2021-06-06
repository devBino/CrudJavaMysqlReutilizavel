package models;

import java.util.HashMap;

public class ProdutoModel extends Model {
	
	public ProdutoModel() {
		super("cdProduto");
		setCamposInsert();
		setApelidosColunas();
	}
	
	public void setCamposInsert() {
		
		camposInsert = new HashMap<String,String>();
		
		camposInsert.clear();
		
		camposInsert.put("cdCategoria","int#1#");
		camposInsert.put("cdFornecedor","int#2#");
		camposInsert.put("codBar","string#3#");
		camposInsert.put("nmProduto","string#4#");
		camposInsert.put("valor","double#5#");
		camposInsert.put("qtdeMin","int#6#");
		camposInsert.put("dtUpdate","datetime#7#");
		camposInsert.put("cdUsuarioUpdate","int#8#");
		
	}
	
	public void setApelidosColunas() {
		
		apelidosColunas = new String[] {
			"ID","CATEGORIA ID","FORNECEDOR ID","CÓD.BARRAS","PRODUTO","VALOR","QTDE. MÍNIMA","DATA ATUALIZAÇÃO","USUÁRIO ATUALIZACÃO"
		};
		
	}

}
