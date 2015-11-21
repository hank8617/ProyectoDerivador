package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import scala.Array;

import com.avaje.ebean.Model;


@Entity
@Table(name="sitio_de_alquiler")
public class SitioDeAlquiler extends Model {

	@Id
	@Column(name="id", nullable=false)
	public Long id;  
	
	@Column(name="nombre", nullable=false, unique=true)
	public String nombre;
	
	@Column(name="indicativo_telefono_fijo")
	public String indicativoTelefonoFijo;
	
	@Column(name="telefono_fijo")
	public String telefonoFijo;
	
	@Column(name="email")
	public String email;
	
	@Column(name="celular")
	public String celular;
	
	@Column(name="tarifa_x_h")
	public String tarifa_x_h;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sitioDeAlquiler")
	public List<Estacion> estaciones;	
	
	@ManyToOne
	public User usuario;
	
	public static Finder<Long, SitioDeAlquiler> find;
	
	public SitioDeAlquiler() {
		super();
		find = new Finder<Long, SitioDeAlquiler>(SitioDeAlquiler.class);
		estaciones = new ArrayList<Estacion>();
	}
}
