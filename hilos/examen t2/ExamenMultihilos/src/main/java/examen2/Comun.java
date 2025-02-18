package examen2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comun {
	public enum Tipo {
		Samurai, Campesino
	}
	
	public static String FechaActual() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		var resultado=dtf.format(now);
		return resultado;
	}
}
