package notificaciones;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.mail.EmailAttachment;
import com.feth.play.module.mail.Mailer;

import notificaciones.ICatalogoNotificaciones.TipoNotificacion;
import play.Logger;
import play.Play;

public class Notificador
{
	
	/**
	 * Notificador por defecto decorado. 
	 */
	protected static Mailer mailer = Mailer.getDefaultMailer();
	
	
	public static Mailer.Mail crearEmailHtml(String subject, TipoNotificacion  tipoNotificacion ,  Map<String, String> contenidos, List<String> destinatarios)
	{
		/*NOTA: La lista de "contenidos" pueden ser nulos si el template contiene toda la informacion estatica,
		y/o Si el correo viene con attachment por ejemplo. */
		if (destinatarios==null || destinatarios.isEmpty())
		{
			Logger.warn("Problema en Notificaciones: '"
					+ "La lista de destinatarios no puede ser nula ni vacia"
					+ "destinatarios= "+destinatarios);
			
		}
		
		// Busca el template, lo renderiza y crea un objeto de tipo Body
		Mailer.Mail.Body  b = crearHtmlBody(tipoNotificacion.getNombreTemplate(), contenidos);		
		Logger.info("Template Renderizado correctamente = "+ b.getHtml());
		
		// Creacion del email 
        final Mailer.Mail customMail = new Mailer.Mail(subject, b, destinatarios);
		
        return customMail;
	}
	/**
	 * Metodo para agregar un adjunto a una notiicacion por email.
	 * <pre>Se requeire haber creado un email usando un metodo crearEmail</pre>
	 * @param customMail Mail retornado por el metodo <code>crearEmailHtml</code> 
	 * @param nombreArchivo nombre para ponerle al archivo en el email.<br>
	 * Por ejemplo: attachment.pdf
	 * @param rutaArchivo ruta desde la raiz hacia el archivo incluyendo el nombre que tenga el archivo en memoria.<br>
	 * Por ejemplo conf/sample.pdf
	 * @return
	 */
	public static Mailer.Mail agregarAdjuntoDesdeArchivo(Mailer.Mail customMail, String nombreArchivo, String rutaArchivo )
	{
	   //Significado
	   //addAttachment(String name, File file) 
	   //Por ejemplo:
       //customMail.addAttachment("attachment.pdf", Play.application().getFile("conf/sample.pdf"));
		
	   customMail.addAttachment(nombreArchivo, Play.application().getFile(rutaArchivo)) ;
       return customMail;
	}
	
	/**
	 * Metodo capaz de adjuntar textos en memoria como txt en un correo.
	 * @param customMail el Mail creado hasta ahora 
	 * @param texto que se quiere adjuntar como txt
	 * @return el customMail con el texto adjuntado como un archivo  .txt
	 */
	public static Mailer.Mail agregarAdjuntoDesdeTextoEnMemoria(Mailer.Mail customMail, String nombreSinExtencion, String descripcion, String texto)
	{
		 byte[] data = texto.getBytes();
		
		 //Significado
	     //addAttachment(String name, byte[] data, String mimeType, String description, String disposition)
		 //Por ejemplo:
	     //customMail.addAttachment("data.txt", data, "text/plain", "A simple file", EmailAttachment.INLINE);
	     
		 customMail.addAttachment(nombreSinExtencion+".txt", data, "text/plain", descripcion, EmailAttachment.INLINE);
	     
	     return customMail;
	}
	
	
	/**
	 * Envia una notificacion de mail creada
	 * @param customMail el Mail creado
	 */
	public static void enviarEmail (Mailer.Mail customMail)
	{
		try {
			mailer.sendMail(customMail);
			Logger.info("NOTIFICAICON enviada exitosamente !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 *  Metodo encargado de crear un objeto Body para el contenido de una notificacion por correo. 
	 * @param template Nombre del template a renderizar.
	 * @param contenidos Contenidos que se quieren agregar dinamicamente al template
	 * @return Body con los contenidos puestos en el template.
	 */
	protected static Mailer.Mail.Body crearHtmlBody(String template, Map<String, String> contenidos )
	{	
		String htmlMensaje = getTemplateNotificacionCorreo(template, contenidos);
		
		Mailer.Mail.Body body = new Mailer.Mail.HtmlBody(htmlMensaje);
		
		return body;
	}
	
	/**
	 * Metodo encado de ubicar el template y rederizarlo con los contenidos dinamicos a un String. 
	 * @param template  Nombre del template a renderizar.
	 * @param contenidos Contenidos que se quieren agregar dinamicamente al template
	 * @return String del HTMl del teplate con los contenido dinamicos completados.
	 */
	protected static String getTemplateNotificacionCorreo(final String template , final Map<String, String> contenidos ) {
		Class<?> cls = null;
		String ret = null;
		try {
			cls = Class.forName(template);
		} catch (ClassNotFoundException e) {
			Logger.warn("TEMPLATE: '"
					+ template
					+ "' NO ENCONTRADA!"
					+ "(revise que el nombre y paquete esten en el formato correcto)");
		}
		
		if (cls != null) {
			Method htmlRender = null;
			try {
				htmlRender = cls.getMethod("render", Map.class);
				ret = htmlRender.invoke(null, contenidos)
						.toString();

			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	
}
