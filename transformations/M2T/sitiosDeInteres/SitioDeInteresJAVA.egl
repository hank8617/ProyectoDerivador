package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@DiscriminatorValue("interes")
public class SitioDeInteres extends Sitio {	      
	
	@Column(name="descripcion")
	public String descripcion;
	
	public static Finder<Long, SitioDeInteres> find;
	
	public SitioDeInteres() {
		super();
		find = new Finder<Long, SitioDeInteres>(SitioDeInteres.class);
	}	
}
