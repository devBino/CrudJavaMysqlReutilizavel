package app;

import exemplos.PapelExemplo;
import exemplos.ProdutoExemplo;

public class Start {

	public static void main(String[] args) {
		executarTestesProduto();
	}
	
	public static void executarTestesPapel() {
				
		PapelExemplo papel = new PapelExemplo();
		papel.getPapeis();
		
	}
	
	public static void executarTestesProduto() {
		
		ProdutoExemplo produto = new ProdutoExemplo();
		produto.getProdutos();
		
	}

}