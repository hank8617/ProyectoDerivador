package models;

import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Model;

@Entity
@Table(name="metrica")
public class Metrica extends Model{
	
	@Id
	@Column(name="id_metrica", nullable=false)
	@GeneratedValue
	private Long idMetrica;
	
	@Column(name="nombre_metrica", nullable=false)
	private String nombreMetrica;
	
	@Column(name="unidad_medida", nullable=false)
	private String unidadMedida;
	
	@OneToMany(mappedBy = "metrica")
	private List<MetricasXRecorrido> metricasXRecorrido;
	
	public static Finder<Long, Metrica> find;
	
	public Metrica(){
		super();
		find = new Finder<Long, Metrica>(Metrica.class);
	}

	public Metrica(Long idMetrica, String nombreMetrica, String unidadMedida) {
		super();
		find = new Finder<Long, Metrica>(Metrica.class);
		this.idMetrica = idMetrica;
		this.nombreMetrica = nombreMetrica;
		this.unidadMedida = unidadMedida;
	}

	public Long getIdMetrica() {
		return idMetrica;
	}

	public void setIdMetrica(Long idMetrica) {
		this.idMetrica = idMetrica;
	}

	public String getNombreMetrica() {
		return nombreMetrica;
	}

	public void setNombreMetrica(String nombreMetrica) {
		this.nombreMetrica = nombreMetrica;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	
}
