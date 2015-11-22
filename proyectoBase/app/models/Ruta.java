package models;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name="ruta")
public class Ruta extends Model {
	
	
	@Id
	@Column(name="id_ruta", nullable=false)
	@GeneratedValue
	private Long idRuta;
	
	@ManyToOne
	private Recorrido recorrido;
	
	@Column(name="fecha_inicio_ruta")
	private Date fechaInicioRuta;
	
	@Column(name="fecha_fin_ruta")
	private Date fechaFinRuta;
	
	@Column(name="latitud_inicio")
	private float latitudInicio;
	
	@Column(name="longitud_incio")
	private float longitudInicio;
	
	@Column(name="latitud_fin")
	private float latitudFin;
	
	@Column(name="longitud_fin")
	private float longitudFin;
	
	@Column(name="lugar_inicio")
	private String lugarInicio;
	
	@Column(name="lugar_fin")
	private String lugarFin;
	
	public static Finder<Long, Ruta> find;
	
	public Ruta(){
		super();
		find = new Finder<Long, Ruta>(Ruta.class);
		recorrido = new Recorrido();
	}
	
	
	public Long getIdRuta() {
		return idRuta;
	}
	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}
	public Recorrido getIdRecorrido() {
		return recorrido;
	}
	public void setIdRecorrido(Recorrido recorrido) {
		this.recorrido = recorrido;
	}
	public Date getFechaInicioRuta() {
		return fechaInicioRuta;
	}
	public void setFechaInicioRuta(Date fechaInicioRuta) {
		this.fechaInicioRuta = fechaInicioRuta;
	}
	public Date getFechaFinRuta() {
		return fechaFinRuta;
	}
	public void setFechaFinRuta(Date fechaFinRuta) {
		this.fechaFinRuta = fechaFinRuta;
	}
	public float getLatitudInicio() {
		return latitudInicio;
	}
	public void setLatitudInicio(float latitudInicio) {
		this.latitudInicio = latitudInicio;
	}
	public float getLongitudInicio() {
		return longitudInicio;
	}
	public void setLongitudInicio(float longitudInicio) {
		this.longitudInicio = longitudInicio;
	}
	public float getLatitudFin() {
		return latitudFin;
	}
	public void setLatitudFin(float latitudFin) {
		this.latitudFin = latitudFin;
	}
	public float getLongitudFin() {
		return longitudFin;
	}
	public void setLongitudFin(float longitudFin) {
		this.longitudFin = longitudFin;
	}
	public String getLugarInicio() {
		return lugarInicio;
	}
	public void setLugarInicio(String lugarInicio) {
		this.lugarInicio = lugarInicio;
	}
	public String getLugarFin() {
		return lugarFin;
	}
	public void setLugarFin(String lugarFin) {
		this.lugarFin = lugarFin;
	}
}
