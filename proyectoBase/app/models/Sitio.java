package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.avaje.ebean.Model;

@Entity
@Table(name="sitio")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Sitio extends Model{
	
	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue
	public Long id;
	
	@Column(name="latitud",nullable=false)
	public double latitud;
	
	@Column(name="longitud",nullable=false)
	public double longitud;
	
	@Column(name="nombre",nullable=false)
	public String nombre;
}
