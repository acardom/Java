Algoritmo contarpalabras
	
	definir cad como cadena
	definir i Como Real
	i=0
	definir j Como Real
	j=1
	
	escribir "escriba su frase"
	leer cad
	
	Repetir
		si subcadena(cad,i,i)=" " Entonces
			j=j+1
		FinSi
		i=i+1
	Hasta Que i=longitud(cad)
	
	escribir "la frase tiene " j " palabras" 
	
FinAlgoritmo
