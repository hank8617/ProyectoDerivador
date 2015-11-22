package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.format.Formats;

/**
 * jm.soto569
 * Los retos cumplidos por el usuario
 */
@Entity
@Table(name = "retos_usuario")
public class RetoUsuario extends AppModel{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_reto_usuario", nullable=false)
	@GeneratedValue
	public Long id;

	@Formats.DateTime(pattern = "dd-MM-yyyy")
	@Column(name="fecha")
	public Date fecha;
	
	@Column(name="estado", nullable=false)
	public String estado;

	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id")
	public User usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_reto", referencedColumnName = "id")
	public Reto reto;

	public static AppModel.Finder<Long, RetoUsuario> find;
	
	public  RetoUsuario() {
		super();
		find = new AppModel.Finder<Long, RetoUsuario> (RetoUsuario.class);
	}

}
