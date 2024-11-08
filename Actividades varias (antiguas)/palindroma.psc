Algoritmo palindromo
	
	Definir cad como cadena
	definir num como entero
	num=0
	definir palindroma como logico 
	
	escribir "escriba una palabra"
	leer cad
	
	repetir
	 
	si (subcadena(cad,num,num)=subcadena(cad,longitud(cad)-num-1,longitud(cad)-num-1)) entonces
		palindroma=verdadero
		num=num+1
	SiNo
		escribir "no es palindroma"
		palindroma=falso
		num=longitud(cad)
	FinSi
	
	Hasta Que num=longitud(cad)

	si palindroma=verdadero
		escribir "es palindroma"
	FinSi

FinAlgoritmo
