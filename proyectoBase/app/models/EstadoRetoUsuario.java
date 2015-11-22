package models;

public enum EstadoRetoUsuario {
	
	VALIDO("Valido"),
	UTILIZADO("Utilizado"),
	INVALIDO("Invalido");
	
	private String estado;
	
	private EstadoRetoUsuario(String estado){
		this.estado = estado;
	}
	
	public String getEstado(){
		return estado;
	}
	
}
