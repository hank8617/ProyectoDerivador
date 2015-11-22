package notificaciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feth.play.module.mail.Mailer;

public class CatalogoNotificaciones implements ICatalogoNotificaciones
{
	private static final String URL_IMAGEN_PARTICIPACION ="https://cdn4.iconfinder.com/data/icons/simplicio/128x128/user_manage.png";

	private static final String URL_IMAGEN_UNION = "https://cdn4.iconfinder.com/data/icons/simplicio/128x128/file_add.png";

	private static final String URL_IMAGEN_RETIRO = "https://cdn4.iconfinder.com/data/icons/simplicio/128x128/file_delete.png";

	/**
	 * Crea un catalogo de notificaciones disponibles para ser consumidas por los coantroladores.
	 * Se dejo privado para oblicar a los controladores a utilizar el metodo getInstance.
	 */
	private CatalogoNotificaciones() {
		super();
	}



	/**
	 * Implementa el catalogo de notificaciones utilizando notificaicones por correo. 	
	 * 
	 * @return {@link CatalogoNotificaciones}
	 */
	public static ICatalogoNotificaciones getICatalogoInstance()
	{
		ICatalogoNotificaciones cn = new CatalogoNotificaciones();
		return cn;
	}

	//----------------------------------------------------------------------------------------------
	//METODOS QUE ORGANIZAN LOS DATOS Y CONTRUYEN LAS NOTIFICACIONES DE LA INTERFAZ IMLEMENTADA
	//----------------------------------------------------------------------------------------------
	// Para pasar los datos a los templates se utiliza un formato clave/valor 
	// con una interfaz/objeto tipo Map<String,String>. 
	// Donde el template encuentre la clave pone el valor entregado.
	//
	// Tener en cuenta:
	//	  CLAVE= Computer Friendly - USAR FORMATO DE CONSTATE	
	//	  clave/nombre/identificador del campo para hacer el mapping con el templade de notificaciones
	//	 
	//	  VALOR = Informacion que se quiere dar al usuario. (un texto, el string de un entero, 
	//	  puede incluir incluso cosas con HTML)
	//---------------------------------------------------------------------------------------------- 


	@Override
	public void notificacionCambioParticipacionRecorridoFrecuente(EstadoParticipacion estadoParticipacion,
			String emailUsuario, String nombreUsuario, String nombreRecorrido, String lugarInicio, String lugarFin,
			String descripcion, String horaFrecuente, String diaFrecuente) {

		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add (emailUsuario);

		String nuevoEstado = " te has " + (estadoParticipacion==EstadoParticipacion.UNIDO_A_RECORRIDO?"unido al":"retirado del") + " recorrido frecuente";
		String urlImagen = estadoParticipacion==EstadoParticipacion.UNIDO_A_RECORRIDO?URL_IMAGEN_UNION:URL_IMAGEN_RETIRO;

		//Notificacion de cambio en participacion de un recorrido FRECUENTE
		Map<String, String> contenidos = new HashMap<String, String>(10);
		contenidos.put("NOMBRE_USUARIO" , nombreUsuario);
		contenidos.put("NUEVO_ESTADO", nuevoEstado);
		contenidos.put("URL_IMAGEN_ESTADO", urlImagen);
		contenidos.put("NOMBRE_RECORRIDO", nombreRecorrido);
		contenidos.put("LUGAR_DE_INICIO", lugarInicio);
		contenidos.put("LUGAR_FIN", lugarFin);
		contenidos.put("HORA_FRECUENTE", horaFrecuente);
		contenidos.put("DIA_FRECUENTE", diaFrecuente);
		contenidos.put("DESCRIPCION_RECORRIDO", descripcion);

		Mailer.Mail notificacion = Notificador.crearEmailHtml("Cambio en participacion de un recorrido frecuente", 
				TipoNotificacion.CAMBIO_PARTICIPACION_RECORRIDO_FRECUENTE, 
				contenidos, 
				destinatarios);

		Notificador.enviarEmail(notificacion);	

	}


	@Override
	public void notificacionCambioParticipacionRecorridoRecreacion(EstadoParticipacion estadoParticipacion,
			String emailUsuario, String nombreUsuario, String nombreRecorrido, String lugarInicio, String lugarFin,
			String descripcion, String horaRecreacion, String fechaInicio, String fechaFin) {

		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add (emailUsuario);

		String nuevoEstado = " te has " + (estadoParticipacion==EstadoParticipacion.UNIDO_A_RECORRIDO?"unido al":"retirado del") + " recorrido recreativo";
		String urlImagen = estadoParticipacion==EstadoParticipacion.UNIDO_A_RECORRIDO?URL_IMAGEN_UNION:URL_IMAGEN_RETIRO;

		//Notificacion de cambio en participacion de un recorrido RECREACION

		Map<String, String> contenidos =  new HashMap<String, String>();
		contenidos.put("NOMBRE_USUARIO" , nombreUsuario);
		contenidos.put("NUEVO_ESTADO", nuevoEstado);
		contenidos.put("URL_IMAGEN_ESTADO", urlImagen);
		contenidos.put("NOMBRE_RECORRIDO", nombreRecorrido);
		contenidos.put("LUGAR_DE_INICIO", lugarInicio);
		contenidos.put("LUGAR_FIN", lugarFin);
		contenidos.put("HORA_RECREACION", horaRecreacion);
		contenidos.put("FECHA_INICIO", fechaInicio);
		contenidos.put("FECHA_FIN", fechaFin);
		contenidos.put("DESCRIPCION_RECORRIDO", descripcion);

		Mailer.Mail notificacion = Notificador.crearEmailHtml("Cambio en participación de un recorrido recreativo", 
				TipoNotificacion.CAMBIO_PARTICIPACION_RECORRIDO_RECREACION, 
				contenidos, 
				destinatarios);

		Notificador.enviarEmail(notificacion);	

	}


	@Override
	public void notificacionInvitacionRecorridoFrecuente(String usuarioInvita, String nombreRecorrido,
			String lugarInicio, String lugarFin, String descripcion, String horaFrecuente, String diaFrecuente,
			List<String> emailsInvitados) {

		//Notificación de invitacion a un nuevo recorrido FRECUENTE
		Map<String, String> contenidos =  new HashMap<String, String>();
		contenidos.put("NOMBRE_INVITA", usuarioInvita);
		contenidos.put("NOMBRE_RECORRIDO", nombreRecorrido);
		contenidos.put("LUGAR_DE_INICIO", lugarInicio);
		contenidos.put("LUGAR_FIN", lugarFin);
		contenidos.put("HORA_FRECUENTE", horaFrecuente);
		contenidos.put("DIA_FRECUENTE", diaFrecuente);
		contenidos.put("DESCRIPCION_RECORRIDO", descripcion);

		Mailer.Mail notificacion = Notificador.crearEmailHtml("Invitación a un nuevo recorrido frecuente", 
				TipoNotificacion.INVITACION_A_UN_RECORRIDO_FRECUENTE, 
				contenidos, 
				emailsInvitados);

		Notificador.enviarEmail(notificacion);

	}

	@Override
	public void notificacionInvitacionRecorridoRecreacion(String usuarioInvita, String nombreRecorrido,
			String lugarInicio, String lugarFin, String descripcion, String horaRecreacion, String fechaInicio,
			String fechaFin, List<String> emailsInvitados) {

		//Notificación de invitacion a un nuevo recorrido RECREACION
		Map<String, String> contenidos =  new HashMap<String, String>();
		contenidos.put("NOMBRE_INVITA", usuarioInvita);
		contenidos.put("NOMBRE_RECORRIDO", nombreRecorrido);
		contenidos.put("LUGAR_DE_INICIO", lugarInicio);
		contenidos.put("LUGAR_FIN", lugarFin);
		contenidos.put("HORA_RECREACION", horaRecreacion);
		contenidos.put("FECHA_INICIO", fechaInicio);
		contenidos.put("FECHA_FIN", fechaFin);
		contenidos.put("DESCRIPCION_RECORRIDO", descripcion);

		Mailer.Mail notificacion = Notificador.crearEmailHtml("Invitación a un nuevo recorrido recreativo", 
				TipoNotificacion.INVITACION_A_UN_RECORRIDO_RECREATIVO, 
				contenidos, 
				emailsInvitados);

		Notificador.enviarEmail(notificacion);

	}



	@Override
	public void notificacionRetoAlcanzado(String emailUsuario, String nombreUsuario, String nombreReto,
			String puntajeReto, String puntajeTotal) {

		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add (emailUsuario);

		//Notificacion de reto alcanzado
		Map<String, String> contenidos = new HashMap<String, String>(10);
		contenidos.put("NOMBRE_USUARIO" , nombreUsuario);
		contenidos.put("NOMBRE_RETO", nombreReto);
		contenidos.put("PUNTOS_RETO", puntajeReto);
		contenidos.put("PUNTOS_TOTALES", puntajeTotal);
		
		Mailer.Mail notificacion = Notificador.crearEmailHtml("Felicitaciones! Haz alcanzado un nuevo reto", 
				TipoNotificacion.ALCANCE_DE_UN_NUEVO_RETO, 
				contenidos, 
				destinatarios);

		Notificador.enviarEmail(notificacion);


	}



	@Override
	public void notificacionRecompensaReclamada(String emailUsuario, String nombreUsuario, String nombreRecompensa,
			String telContacto, String celContacto, String puntajeRecompensa, String puntajeTotal) {
		
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add (emailUsuario);

		//Notificacion de reto alcanzado
		Map<String, String> contenidos = new HashMap<String, String>(10);
		contenidos.put("NOMBRE_USUARIO" , nombreUsuario);
		contenidos.put("NOMBRE_RECOMPENSA", nombreRecompensa);
		contenidos.put("TELEFONO_CONTACTO" , telContacto);
		contenidos.put("CELULAR_CONTACTO", celContacto);
		contenidos.put("PUNTOS_RECOMPENSA", puntajeRecompensa);
		contenidos.put("PUNTOS_TOTALES", puntajeTotal);
		
		Mailer.Mail notificacion = Notificador.crearEmailHtml("Recompensa reclamada, revisa como reclamarla y estado de puntos", 
				TipoNotificacion.RECLAMO_DE_RECOMPENSA, 
				contenidos, 
				destinatarios);

		Notificador.enviarEmail(notificacion);
		
	}








}
