
public class Principal {

	public static void main(String[] args) {
	var principal=new PrincipalMySql();
	
	//consultas(principal);
	//actualizarSinTransaccion(principal);
	actualizarConTransaccion(principal);
	}

	private static void actualizarConTransaccion(PrincipalMySql principal) {
		//principal.actualizaLimiteCredito2vecesPreparedStatment("GoldFish Garden"); //Con error, sin transacción
		principal.actualizaLimiteCredito2vecesPreparedStatmentTransaction("GoldFish Garden");
	}

	private static void actualizarSinTransaccion(PrincipalMySql principal) {
		System.out.println("Actualiza limitre credito Nombre Cliente Prepared Statment");
		principal.actualizaLimiteCreditoPreparedStatment("GoldFish Garden");
		System.out.println();
	}

	private static void consultas(PrincipalMySql principal) {
		System.out.println("Nombre columna");
		principal.consultaPorNombreColumna();
		System.out.println();
		
		System.out.println("Index columna");
		principal.consultaPorIndexColumna();
		System.out.println();
		
		System.out.println("Parametro ID cliente");
		principal.consultaParamPorIdCliente(4);
		System.out.println();
		
		System.out.println("Parametro Nombre Cliente");
		principal.consultaParamPorNombreCliente("GoldFish Garden");
		System.out.println();
		
		System.out.println("Parametro Nombre Cliente SQL inyection");
		principal.consultaParamPorNombreCliente("GoldFish Garden' OR '1'='1");
		System.out.println();
		
		
		System.out.println("Parametro Nombre Cliente Prepared Statment");
		principal.consultaParamPorNombreClientePreparedStatment("GoldFish Garden");
		System.out.println();
		
		System.out.println("Parametro Nombre Cliente Prepared Statment SQL inyection");
		principal.consultaParamPorNombreClientePreparedStatment("GoldFish Garden OR '1'='1'");
		System.out.println();

	}

}
