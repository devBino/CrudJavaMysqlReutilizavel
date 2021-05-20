package odata;

import java.lang.StringBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	public Boolean salvar(String prTabela, Map<String,String> prCampos ) {
	
		return true;
	}
	
	public String montarColunas(Map<String,String> prCampos) {
		
		StringBuilder colunas = new StringBuilder();
		String[] arrColunas = new String[prCampos.size()];
		
		for(String str : prCampos.keySet()) {
			
			String info = prCampos.get(str);
			String[] arrInfo = info.split("#");
			
			arrColunas[ new Integer( Integer.parseInt(arrInfo[1]) - 1 ) ] = str;
			
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
	
	
	public void prepararStatementInsert(Map<String,String> prCampos) {
		
		try {
			
			prepareInsert = con.prepareStatement(sqlInsert);
	
			for(String str : prCampos.keySet()) {
				
				String info = prCampos.get(str);
				String[] arrInfo = info.split("#");
				
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
					default:
						prepareInsert.setString(new Integer(arrInfo[1]), new String(arrInfo[2]));
						break;
				}
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
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
	
	
}