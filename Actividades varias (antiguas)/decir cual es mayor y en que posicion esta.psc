Algoritmo numeromayoryposicion
	
	definir num Como Entero
	num=0
	definir num1 Como Entero
	num1=0
	definir num3 Como Entero
	num3=0
	definir num4 Como Entero
	num4=0
	definir num5 Como Entero
	num5=0
	
	escribir"cuantos numeros quieres introducier"
	leer num
	
	Repetir
		escribir "escriba un numero"
		leer num1
		si num1>=num4 entonces
			num4=num1
			num5=num3+1
		FinSi
		num3=num3+1
	Hasta Que num3=num
	
	escribir"el numero mayor es " num4 " y esta en la posicion " num5
	
FinAlgoritmo
