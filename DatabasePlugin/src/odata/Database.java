package odata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Map;

import configs.Params;

public class Database {

	private StringBuilder urlBanco;
	private String tipoSgbd;
	private String porta;
	private String usuario;
	private String senha;
	private String banco;
	private String servidor;
	private Connection con;
	
	protected String sqlInsert;
	protected PreparedStatement prepareInsert;
	protected String sqlUpdate;
	protected PreparedStatement prepareUpdate;
	protected String sqlDelete;
	protected PreparedStatement prepareDelete;
	
	public Database() {
		setTipoSgbd("mysql");
		setPorta("3306");
		setUsuario(Params.getUser());
		setSenha(Params.getPass());
		setBanco(Params.getBanco());
		setServidor(Params.getHost());
		
		urlBanco = new StringBuilder();
		urlBanco.append("jdbc:");
		urlBanco.append(tipoSgbd);
		urlBanco.append("://");
		urlBanco.append(servidor);
		urlBanco.append(":");
		urlBanco.append(porta);
		urlBanco.append("/");
		urlBanco.append(banco);
		
		con = getConnection();
	}
	
	public Database(String prUsuario, String prSenha, String prBanco, String prServidor) {
		
		setTipoSgbd("mysql");
		setPorta("3306");
		setUsuario(prUsuario);
		setSenha(prSenha);
		setBanco(prBanco);
		setServidor(prServidor);
		
		urlBanco = new StringBuilder();
		urlBanco.append("jdbc:");
		urlBanco.append(tipoSgbd);
		urlBanco.append("://");
		urlBanco.append(servidor);
		urlBanco.append(":");
		urlBanco.append(porta);
		urlBanco.append("/");
		urlBanco.append(banco);
		
		con = getConnection();
		
	}
	
	public Connection getConnection() {
		try {
			Connection c = DriverManager.getConnection(urlBanco.toString(),usuario,senha);
			return c;
		}catch(Exception e) {
			System.err.println(e);
			return null;
		}
	}
	
	public void setTipoSgbd(String prTipoSgbd) {
		tipoSgbd = prTipoSgbd;
	}
	
	public String getTipoSgbd() {
		return tipoSgbd;
	}
	
	public void setPorta(String prPorta) {
		porta = prPorta;
	}
	
	public String getPorta() {
		return porta;
	}
	
	public void setUsuario(String prUsuario) {
		usuario = prUsuario;
	}
	
	public void setSenha(String prSenha) {
		senha = prSenha;
	}
	
	public void setBanco(String prBanco) {
		banco = prBanco;
	}
	
	public String getBanco() {
		return banco;
	}
	
	public void setServidor(String prServidor) {
		servidor = prServidor;
	}
	
	public String getServidor() {
		return servidor;
	}
	
	public String montarColunas(Map<String,String> prCampos) {
		
		StringBuilder colunas = new StringBuilder();
		String[] arrColunas = new String[prCampos.size()];
		
		for(String str : prCampos.keySet()) {
			
			String[] arrInfo = prCampos.get(str).split("#");
			arrColunas[ Integer.parseInt(arrInfo[1]) - 1 ] = str;
			
		}
		
		for(int i=0; i<arrColunas.length; i++) {
			String virgula = (i < prCampos.size() - 1) ? "," : "";
			colunas.append(arrColunas[i] + virgula);
		}
		
		return colunas.toString();
	}
	
	public String montarInterrogacoesValores(Map<String,String> prCampos) {
	
		String marcadores = "?,";
		String strReturn = marcadores.repeat( prCampos.size() );
		
		return strReturn.substring(0, strReturn.length() - 1);
		
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

	public void prepararStatementInsert(Map<String,String> prCampos) {
		
		try {
			
			prepareInsert = con.prepareStatement(sqlInsert);
	
			for(String str : prCampos.keySet()) {
				
				String[] arrInfo = prCampos.get(str).split("#");
				
				switch(arrInfo[0]){
					case "string":
						prepareInsert.setString(new Integer(arrInfo[1]), new String(arrInfo[2]));
						break;
					case "int":
						prepareInsert.setInt(new Integer(arrInfo[1]), new Integer( Integer.parseInt(arrInfo[2]) ));
						break;
					case "double":
						prepareInsert.setDouble(new Integer(arrInfo[1]), new Double( Double.parseDouble( arrInfo[2]) ));
						break;
					case "datetime":
						prepareInsert.setString(new Integer(arrInfo[1]), new String(arrInfo[2]));
						break;
					default:
						prepareInsert.setString(new Integer(arrInfo[1]), new String(arrInfo[2]));
						break;
				}
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void prepararUpdate(String prTabela, String prColuna, int prId, Map<String,String> prCampos) {
	
		StringBuilder sql = new StringBuilder();
		String[] arrDadosSets = montarSetUpdate(prCampos);
		
		sql.append("update ");
		sql.append(prTabela);
		sql.append(" set ");
		sql.append(arrDadosSets[0]);
		sql.append(" where ");
		sql.append(prColuna);
		sql.append("=?");	
		
		sqlUpdate = sql.toString();
		
		prepararStatementUpdate(prCampos,arrDadosSets[1],prId);
		
	}
	
	public void prepararStatementUpdate(Map<String,String> prCampos, String prIndicesColunas, int prId) {
		
		try {
			
			prepareUpdate = con.prepareStatement(sqlUpdate);
			
			int indice = 0;
			
			for(String str : prCampos.keySet()) {
				
				String arrInfo[] = prCampos.get(str).split("#");
				
				if( prIndicesColunas.contains( arrInfo[1] ) ) {
					
					indice = prIndicesColunas.replace(";","").indexOf(arrInfo[1]) + 1;
					
					switch(arrInfo[0]){
						case "string":
							prepareUpdate.setString(indice, new String(arrInfo[2]));
							break;
						case "int":
							prepareUpdate.setInt(indice, new Integer( Integer.parseInt(arrInfo[2]) ));
							break;
						case "double":
							prepareUpdate.setDouble(indice, new Double( Double.parseDouble( arrInfo[2]) ));
							break;
						case "datetime":
							prepareUpdate.setString(indice, new String(arrInfo[2]));
							break;
						default:
							prepareUpdate.setString(indice, new String(arrInfo[2]));
							break;
					}
					
				}
				
			}
			
			indice += 1;
			
			prepareUpdate.setInt(indice, prId);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public String[] montarSetUpdate(Map<String,String> prCampos) {
		
		StringBuilder strSets = new StringBuilder();
		StringBuilder indicesCampos = new StringBuilder();
		String[] arrSets = new String[2];
		
		
		for(String str : prCampos.keySet()) {
			
			String[] arrInfo = prCampos.get(str).split("#");
			
			if( arrInfo.length > 2 ) {
				strSets.append(str + "=?,");
				indicesCampos.append(arrInfo[1]);
			}
			
		}
		
		arrSets[0] = strSets.toString().substring(0, strSets.toString().length() - 1 );
		arrSets[1] = indicesCampos.toString();
		
		return arrSets;
		
	}
	
	public void prepararDelete(String prTabela, String prColuna, int prId) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("delete from ");
		sql.append(prTabela);
		sql.append(" where ");
		sql.append(prColuna);
		sql.append(" = ?");
		
		sqlDelete = sql.toString();
		
		prepararStatementDelete(prId);
		
	}
	
	public void prepararStatementDelete(int prId) {
		try {
			
			prepareDelete = con.prepareStatement(sqlDelete);
			prepareDelete.setInt(1, prId);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}