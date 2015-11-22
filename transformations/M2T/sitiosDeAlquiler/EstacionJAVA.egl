package models;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@DiscriminatorValue("estacion")
public class Estacion extends Sitio {	      
	
	@Column(name="hora_inicio_atencion")
	public String horaInicioAtencion;
	
	@Column(name="hora_fin_atencion")
	public String horaFinAtencion;
	
	@Column(name="para_alquiler")
	public boolean paraAlquiler;
	
	@Column(name="para_entrega")
	public boolean paraEntrega;
		
	@ManyToOne
	public SitioDeAlquiler sitioDeAlquiler;
	
	public static Finder<Long, Estacion> find;
	
	public Estacion() {
		super();
		find = new Finder<Long, Estacion>(Estacion.class);
	}	
}
