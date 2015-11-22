package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.avaje.ebean.Model;

@Entity
@Table(name="recorrido")
public class Recorrido extends Model {

	@Id
	@Column(name="id_recorrido", nullable=false)
	@GeneratedValue
	private Long idRecorrido;
	
	@Column(name="tipo", nullable=false)
	private int tipo;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="hora_frecuente")
	private String horaFrecuente;
	
	@Column(name="dia_frecuente")
	private String diaFrecuente; 
		
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recorrido")
	private List<MetricasXRecorrido> metricasXRecorrido;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recorrido")
	private List<Ruta> lstRuta;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recorrido")
	private List<UsuarioXRecorrido> lstUsuarioXRecorrido;
	
	public static Finder<Long, Recorrido> find;
	
	public Recorrido()
	{
		super();
		find = new Finder<Long, Recorrido>(Recorrido.class);
		lstRuta= new ArrayList<Ruta>();
		lstUsuarioXRecorrido = new ArrayList<UsuarioXRecorrido>();
	}
	
	public Recorrido(Long idRecorrido, int tipo, String nombre, String descripcion, String horaFrecuente, String diaFrecuente){
		super();
		find = new Finder<Long, Recorrido>(Recorrido.class);
		this.tipo = tipo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.horaFrecuente = horaFrecuente;
		this.diaFrecuente = diaFrecuente;
	}
	
	public List<UsuarioXRecorrido> getLstUsuarioXRecorrido() {
		return lstUsuarioXRecorrido;
	}

	public void setLstUsuarioXRecorrido(List<UsuarioXRecorrido> lstUsuarioXRecorrido) {
		this.lstUsuarioXRecorrido = lstUsuarioXRecorrido;
	}
	
	public List<Ruta> getLstRuta() {
		return lstRuta;
	}

	public void setLstRuta(List<Ruta> lstRuta) {
		this.lstRuta = lstRuta;
	}

	public Long getIdRecorrido() {
		return idRecorrido;
	}

	public void setIdRecorrido(Long idRecorrido) {
		this.idRecorrido = idRecorrido;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDiaFrecuente() {
		return diaFrecuente;
	}

	public void setDiaFrecuente(String diaFrecuente) {
		this.diaFrecuente = diaFrecuente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getHoraFrecuente() {
		return horaFrecuente;
	}

	public void setHoraFrecuente(String horaFrecuente) {
		this.horaFrecuente = horaFrecuente;
	}

	public List<MetricasXRecorrido> getMetricasXRecorrido() {
		return metricasXRecorrido;
	}

	public void setMetricasXRecorrido(List<MetricasXRecorrido> metricasXRecorrido) {
		this.metricasXRecorrido = metricasXRecorrido;
	}
}
