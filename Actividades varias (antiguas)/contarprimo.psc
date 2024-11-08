Algoritmo sin_titulo
	
	definir contar,i,j,num3,a,b como entero
	definir num,num2,numescrito,escrito,nume Como cadena
	definir primo como logico
	i=0
	b=2
	j=1
	num=""
	num2=""
	escrito=""
	contar=0
	nume=""
	
	escribir "ponga aqui su numero"
	leer num
	
	primo=falso
	nume=convertiratexto(num)
	repetir
		num2=Subcadena(num,i,j)
		num3=convertiranumero(num2)
			repetir
			si num3 mod b=0 entonces
				primo=verdadero
			FinSi
			b=b+1
			hasta que b=(num3-1)
		si primo=falso
			contar=contar+1
			numescrito=convertiratexto(num3)
			escrito=concatenar(escrito,numescrito)	
		FinSi
		i=i+1
		j=j+1
	Hasta Que j>=longitud(nume)
	
	escribir "hay " contar " numeros primos, que son:" escrito	
	
FinAlgoritmo
