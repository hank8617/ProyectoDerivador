package models;

public enum Horas {

	HORA0("00:00"),
	HORA1("01:00"),
	HORA2("02:00"),
	HORA3("03:00"),
	HORA4("04:00"),
	HORA5("05:00"),
	HORA6("06:00"),
	HORA7("07:00"),
	HORA8("08:00"),
	HORA9("09:00"),
	HORA10("10:00"),
	HORA11("11:00"),
	HORA12("12:00"),
	HORA13("13:00"),
	HORA14("14:00"),
	HORA15("15:00"),
	HORA16("16:00"),
	HORA17("17:00"),
	HORA18("18:00"),
	HORA19("19:00"),
	HORA20("20:00"),
	HORA21("21:00"),
	HORA22("22:00"),
	HORA23("23:00");
	
	private String hora;
	
	private Horas(String hora){
		this.hora = hora;
	}
	
	public String getHora(){
		return hora;
	}
}
