package models;

public enum EstadoReto {
	
	ACTIVO("Activo"),
	INACTIVO("Inactivo");
	
	private String estado;
	
	private EstadoReto(String estado){
		this.estado = estado;
	}
	
	public String getEstado(){
		return estado;
	}
	
}
