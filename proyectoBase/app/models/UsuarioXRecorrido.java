package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avaje.ebean.Model;

@Entity
@Table(name="usuario_x_recorrido")
public class UsuarioXRecorrido extends Model {

	@Id
	@Column(name="id_usuario_recorrido", nullable=false)
	@GeneratedValue
	private Long idUsuarioXRecorrido;
	
	@ManyToOne
	@Column(nullable=false)
	private User usuario;

	@ManyToOne
	@Column(nullable=false)
	private Recorrido recorrido;
	
	@Column(name="ind_confirmado", nullable=false)
	private Boolean indConfirmado;
	
	@Column(name="ind_administrador", nullable=false)
	private Boolean indAdministrador;
	
	public static Finder<Long, UsuarioXRecorrido> find;
	
	public UsuarioXRecorrido(){
		super();
		find = new Finder<Long, UsuarioXRecorrido>(UsuarioXRecorrido.class);
	}

	public UsuarioXRecorrido(Long idUsuarioXRecorrido, User usuario,
			Recorrido recorrido, Boolean indConfirmado, Boolean indAdministrador) {
		super();
		this.idUsuarioXRecorrido = idUsuarioXRecorrido;
		this.usuario = usuario;
		this.recorrido = recorrido;
		this.indConfirmado = indConfirmado;
		this.indAdministrador = indAdministrador;
	}


	public Long getIdUsuarioXRecorrido() {
		return idUsuarioXRecorrido;
	}

	public void setIdUsuarioXRecorrido(Long idUsuarioXRecorrido) {
		this.idUsuarioXRecorrido = idUsuarioXRecorrido;
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

	public Boolean getIndConfirmado() {
		return indConfirmado;
	}

	public void setIndConfirmado(Boolean indConfirmado) {
		this.indConfirmado = indConfirmado;
	}

	public Boolean getIndAdministrador() {
		return indAdministrador;
	}

	public void setIndAdministrador(Boolean indAdministrador) {
		this.indAdministrador = indAdministrador;
	}
}
