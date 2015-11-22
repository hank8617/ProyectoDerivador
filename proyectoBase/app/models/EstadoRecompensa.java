package models;

public enum EstadoRecompensa {
	
	ACTIVA("Activa"),
	INACTIVA("Inactiva");
	
	private String estado;
	
	private EstadoRecompensa(String estado){
		this.estado = estado;
	}
	
	public String getEstado(){
		return estado;
	}
	
}
