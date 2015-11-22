package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.format.Formats;

@Entity
@Table(name="recompensa_usuario")
public class RecompensaUsuario extends AppModel{

	@Id
	@Column(name="id_recompensa_usuario", nullable=false)
	@GeneratedValue
	public Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_recompensa", referencedColumnName = "id")
	public Recompensa recompensa;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id")
	public User usuario;
	
	@Formats.DateTime(pattern = "dd-MM-yyyy")
	@Column(name="fecha", nullable=false)
	public Date fecha;
	
	public static AppModel.Finder<Long, RecompensaUsuario> find;

	public RecompensaUsuario() {
		super();
		find = new AppModel.Finder<Long, RecompensaUsuario> (RecompensaUsuario.class);
		this.fecha = new Date();
	}
/*
	public RecompensaUsuario(RecompensaUsuarioPK recompensaUsuarioPK, Date fecha) {
		super();
		find = new AppModel.Finder<RecompensaUsuarioPK, RecompensaUsuario> (RecompensaUsuario.class);
		this.recompensaUsuarioPK = new RecompensaUsuarioPK();
		this.fecha = fecha;
	}*/
	
}
