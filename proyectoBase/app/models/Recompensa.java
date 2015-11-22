package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.format.Formats;

@Entity
@Table(name="recompensa")
public class Recompensa extends AppModel {

	@Id	
	@Column(name="id", nullable=false)
	@GeneratedValue
	public Long id;
	
	@Column(name="puntaje_requerido", nullable=false)
	public Long puntajeRequerido;
	
	@Column(name="recompensa", nullable=false)
	public String recompensa;
	
	@Column(name="telefono_contacto", nullable=false)
	public String telefonoContacto;
	
	@Column(name="celular_contacto")
	public String celularContacto;
	
	@Formats.DateTime(pattern = "dd-MM-yyyy")
	@Column(name="fecha_limite", nullable=false)
	public Date fechaLimite;
	
	@Column(name="estado")
	public String estado;	
	
	public static AppModel.Finder<Long, Recompensa> find;

	public Recompensa(Long id, Long puntajeRequerido, String recompensa, String telefonoContacto,
			String celularContacto, Date fechaLimite, String estado) {
		super();
		find = new AppModel.Finder<Long, Recompensa> (Recompensa.class);
		this.id = id;
		this.puntajeRequerido = puntajeRequerido;
		this.recompensa = recompensa;
		this.telefonoContacto = telefonoContacto;
		this.celularContacto = celularContacto;
		this.fechaLimite = fechaLimite;
		this.estado = estado;
	}

	public Recompensa() {
		super();
		find = new AppModel.Finder<Long, Recompensa> (Recompensa.class);
	}
	
	
	
}
