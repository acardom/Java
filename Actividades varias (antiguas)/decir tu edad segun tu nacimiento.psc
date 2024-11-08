Algoritmo calcularedad
	definir dia Como Entero
	definir año como entero 
	definir mes como cadena
	definir edad como entero
	edad=0
	definir valor1 como entero
	
	escribir "ponga aqui su dia de nacimiento"
	leer dia
	escribir "ponga aqui su mes de nacimiento"
	leer mes
	escribir "ponga aqui su año de nacimiento"
	leer año
	
	segun mes
		"enero":valor1=1
		"febrero":valor1=2
		"marzo":valor1=3
		"abril":valor1=4
		"mayo":valor1=5
		"junio":valor1=6
		"julio":valor1=7
		"agosto":valor1=8
		"septiembre":valor1=9
		"octubre":valor1=10
		"noviembre":valor1=11
		"diciembre":valor1=12
	FinSegun
	
	repetir
		si (año<2021) entonces
			edad=edad+1
			
		si (año<=2021) y (valor1<=10) y (dia<=6) entonces
			edad=edad+1
		FinSi
		FinSi
		año=año+1
	Hasta Que año>2021
	
	escribir "tienes" edad "años"
	
FinAlgoritmo
