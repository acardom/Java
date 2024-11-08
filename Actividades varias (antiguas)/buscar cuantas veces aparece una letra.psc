Algoritmo cuantasvecesaparece
	
	definir cad como cadena
	definir letra como cadena
	definir i como entero
	i=0
	definir j como entero
	j=0
	
	escribir "ponga aqui una palabra"
	leer cad
	
	escribir "ponga aqui una letra"
	leer letra
	
	Repetir
		si longitud(letra)=1
		SiNo
			escribir "solo se pide una letra"
			leer letra
		FinSi
	Hasta Que longitud(letra)=1
	
	Repetir
		si letra=subcadena(cad,i,i)
			j=j+1
		FinSi
			i=i+1
	Hasta Que i=longitud(cad)
	
	escribir"la letra aparece:" j "veces"
	
FinAlgoritmo
