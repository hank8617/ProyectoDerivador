package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.format.Formats;

/**
 * jm.soto569
 */
@Entity
@Table(name = "retos")
public class Reto extends AppModel{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue
	public Long id;

	@Column(name="nombre", nullable=false)
	public String nombre;
	
	@Formats.DateTime(pattern = "yyyy-MM-dd")
	@Column(name="fecha_inicio")
	public Date fechaIni;
	
	@Formats.DateTime(pattern = "yyyy-MM-dd")
	@Column(name="fecha_fin")
	public Date fechaFin;
	
	@ManyToOne
	public Metrica metrica;

	@Column(name="funcion", nullable=false)
	public String funcion;
	
	@Column(name="operador", nullable=false)
	public String operador;
	
	@Column(name="valor_condicion", nullable=false)
	public String valorCondicion;
	
	@Column(name="puntaje", nullable=false)
	public Long puntaje;
	
	@Column(name="estado", nullable=false)
	public String estado;

	public static AppModel.Finder<Long, Reto> find;
	
	public  Reto() {
		super();
		find = new AppModel.Finder<Long, Reto> (Reto.class);
	}

	public Reto(Long id, String nombre, Date fechaIni, Date fechaFin, Metrica metrica, String funcion, String operador,
			String valorCondicion, Long puntaje, String estado) {
		super();
		find = new AppModel.Finder<Long, Reto> (Reto.class);
		this.id = id;
		this.nombre = nombre;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.metrica = metrica;
		this.funcion = funcion;
		this.operador = operador;
		this.valorCondicion = valorCondicion;
		this.puntaje = puntaje;
		this.estado = estado;
	} 
	
	public FuncionReto getFuncion(){
		for(FuncionReto funcion: FuncionReto.values()){
			if(funcion.name().equals(this.funcion)){
				return funcion;
			}
		}
		return null;
	}
}
