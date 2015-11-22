package models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avaje.ebean.Model;

@Entity
@Table(name="metricas_x_recorridos")
public class MetricasXRecorrido extends Model{
	
	@Id
	@Column(name="id_metrica_recorrido", nullable=false)
	@GeneratedValue
	private Long idMetricaXRecorrido;
	
	@ManyToOne
	@Column(nullable=false)
	private Metrica metrica;
	
	@ManyToOne
	@Column(nullable=false)
	private User usuario;
	
	@ManyToOne
	@Column(nullable=false)
	private Recorrido recorrido;
	
	@Column(name="fecha", nullable=false)
	private Date fecha;
	
	@Column(name="valor_metrica", nullable=false)
	private Double valorMetrica;
	
	public static Finder<Long, MetricasXRecorrido> find;
	
	public MetricasXRecorrido(){
		super();
		find = new Finder<Long, MetricasXRecorrido>(MetricasXRecorrido.class);
	}

	public MetricasXRecorrido(Long idMetricaXRecorrido, Metrica metrica,
			User usuario, Recorrido recorrido, Date fecha,
			Double valorMetrica) {
		super();
		this.idMetricaXRecorrido = idMetricaXRecorrido;
		this.metrica = metrica;
		this.usuario = usuario;
		this.recorrido = recorrido;
		this.fecha = fecha;
		this.valorMetrica = valorMetrica;
	}

	public Long getIdMetricaXRecorrido() {
		return idMetricaXRecorrido;
	}

	public void setIdMetricaXRecorrido(Long idMetricaXRecorrido) {
		this.idMetricaXRecorrido = idMetricaXRecorrido;
	}

	public Metrica getMetrica() {
		return metrica;
	}

	public void setMetrica(Metrica metrica) {
		this.metrica = metrica;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Recorrido getRecorrido() {
		return recorrido;
	}

	public void setRecorrido(Recorrido recorrido) {
		this.recorrido = recorrido;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getValorMetrica() {
		return valorMetrica;
	}

	public void setValorMetrica(Double valorMetrica) {
		this.valorMetrica = valorMetrica;
	}
	
	
	
	
}