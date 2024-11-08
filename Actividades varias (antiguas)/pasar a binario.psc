Algoritmo pasarabiario
	
	definir binario como cadena
	binario= ""
	definir num Como Entero
	
	escribir "ponga aqui su numero"
	leer num
	
	repetir 
		si num mod 2=0
			binario=concatenar("0",binario)
			num=num/2
		sino
			binario=concatenar("1",binario)
			num=(num-1)/2
		FinSi
		
	Hasta Que num=1 o num=0
	
	si num=1
		binario=concatenar("1",binario)
	SiNo
		binario=concatenar("0",binario)
	FinSi
	
	escribir binario
	
FinAlgoritmo
