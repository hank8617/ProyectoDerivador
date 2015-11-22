package models;

import java.util.ArrayList;
import java.util.List;

public enum OperadorCondicionReto {

	MAYOR(">"),
	MAYOR_O_IGUAL(">="),
	MENOR("<"),
	MENOR_O_IGUAL("<="),
	IGUAL("="),
	DIFERENTE("!=");
	
	private String operador;
	
	private OperadorCondicionReto(String operador){
		this.operador = operador;
	}
	
	public String getOperador(){
		return operador;
	}
	
	public static List<String> getSymbols(){
		List<String> sym = new ArrayList<>();
		for(OperadorCondicionReto o : OperadorCondicionReto.values()){
			sym.add(o.getOperador());
		}
		return sym;
	}
}
