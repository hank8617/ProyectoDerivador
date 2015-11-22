package models;

public enum FuncionReto {

	MAX("max","Máximo"),
	MIN("min","Mínimo"),
	PROMEDIO("avg","Promedio"),
	CONTAR("count","Contar"),
	SUMA("sum","Suma");
	
	private String funcion;
	private String nombre;
	
	private FuncionReto(String funcion, String nombre){
		this.funcion = funcion;
		this.nombre = nombre;
	}
	
	public String getFuncion(){
		return funcion;
	}
	
	public String getNombre(){
		return nombre;
	}
}
