package notificaciones;

import java.util.List;

public interface ICatalogoNotificaciones {

	
	public enum EstadoParticipacion{
		UNIDO_A_RECORRIDO,
		RETIRADO_DE_RECORRIDO
	}		
	
	
	public enum TipoNotificacion {
		
		CAMBIO_PARTICIPACION_RECORRIDO_FRECUENTE ("views.html.notifications.templates.cambio_participacion_frecuente"), 
		CAMBIO_PARTICIPACION_RECORRIDO_RECREACION ("views.html.notifications.templates.cambio_participacion_recreacion"), 
		INVITACION_A_UN_RECORRIDO_FRECUENTE ("views.html.notifications.templates.invitacion_recorrido_frecuente"), 
		INVITACION_A_UN_RECORRIDO_RECREATIVO ("views.html.notifications.templates.invitacion_recorrido_recreacion"), 
		ALCANCE_DE_UN_NUEVO_RETO ("views.html.notifications.templates.nuevo_reto_alcanzado"), 
		RECLAMO_DE_RECOMPENSA ("views.html.notifications.templates.recompensa_reclamada");

		private final String templateNotificacion;

		private TipoNotificacion(String templateNotificacion) {
			this.templateNotificacion = templateNotificacion;

		}
		public String getNombreTemplate() {
			return templateNotificacion;
		}
		
	}
	
	/**
	 * 
	 * @param usuarioInvita
	 * @param nombreRecorrido
	 * @param lugarInicio
	 * @param lugarFin
	 * @param descripcion
	 * @param horaFrecuente
	 * @param diaFrecuente
	 * @param emailsInvitados
	 */
	public void notificacionInvitacionRecorridoFrecuente(String usuarioInvita, String nombreRecorrido,
			String lugarInicio, String lugarFin, String descripcion, String horaFrecuente, String diaFrecuente,
			List<String> emailsInvitados);

	/**
	 * 
	 * @param usuarioInvita
	 * @param nombreRecorrido
	 * @param lugarInicio
	 * @param lugarFin
	 * @param descripcion
	 * @param horaRecreacion
	 * @param fechaInicio
	 * @param fechaFin
	 * @param emailsInvitados
	 */
	public void notificacionInvitacionRecorridoRecreacion(String usuarioInvita, String nombreRecorrido,
			String lugarInicio, String lugarFin, String descripcion, String horaRecreacion, String fechaInicio,
			String fechaFin, List<String> emailsInvitados);

	/**
	 * 
	 * @param estadoParticipacion
	 * @param emailUsuario
	 * @param nombreUsuario
	 * @param nombreRecorrido
	 * @param lugarInicio
	 * @param lugarFin
	 * @param descripcion
	 * @param horaFrecuente
	 * @param diaFrecuente
	 */
	public void notificacionCambioParticipacionRecorridoFrecuente(EstadoParticipacion estadoParticipacion, String emailUsuario, String nombreUsuario,
			String nombreRecorrido, String lugarInicio, String lugarFin, String descripcion, String horaFrecuente,
			String diaFrecuente);

	/**
	 * 
	 * @param estadoParticipacion
	 * @param emailUsuario
	 * @param nombreUsuario
	 * @param nombreRecorrido
	 * @param lugarInicio
	 * @param lugarFin
	 * @param descripcion
	 * @param horaRecreacion
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public void notificacionCambioParticipacionRecorridoRecreacion(EstadoParticipacion estadoParticipacion, String emailUsuario, String nombreUsuario,
			String nombreRecorrido, String lugarInicio, String lugarFin, String descripcion, String horaRecreacion,
			String fechaInicio, String fechaFin);

	/**
	 * 
	 * @param emailUsuario
	 * @param nombreUsuario
	 * @param nombreReto
	 * @param puntajeReto
	 * @param puntajeTotal
	 */
	public void notificacionRetoAlcanzado(String emailUsuario, String nombreUsuario, String nombreReto,
			String puntajeReto, String puntajeTotal);

	/**
	 * 
	 * @param emailUsuario
	 * @param nombreUsuario
	 * @param nombreRecompensa
	 * @param telContacto
	 * @param celContacto
	 * @param puntajeRecompensa
	 * @param puntajeTotal
	 */
	public void notificacionRecompensaReclamada(String emailUsuario, String nombreUsuario, String nombreRecompensa,
			String telContacto, String celContacto, String puntajeRecompensa, String puntajeTotal);
}
