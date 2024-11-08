package practicaunidad4;


public class CCuenta {


    protected String nombre;
    private String cuenta;
    private double saldo;
    private double tipoInterés;

    
    public CCuenta ()
    {
    }
    
    
    public CCuenta (String nom, String cue, double sal, double tipo)
    {
        nombre =nom;
        cuenta=cue;
        saldo=sal;
        tipoInterés=tipo;
    }
   
    
    public void asignarNombre(String nom)
    {
        nombre=nom;
    }
    
    public String obtenerNombre()
    {
        return nombre;
    }

    
     public double estado ()
    {
        return saldo;
    }

    
    public void ingresar(double cantidad) throws Exception
    {
        if (cantidad<0){
            throw new Exception("No se puede ingresar una cantidad negativa");}
        setSaldo(saldo + cantidad);
    }



    public void retirar (double cantidad) throws Exception
    {
        if (cantidad < 0){
            throw new Exception ("No se puede retirar una cantidad negativa");}
        if (estado()< cantidad){
            throw new Exception ("No se hay suficiente saldo");}
        setSaldo(saldo - cantidad);
    }
    
    public String obtenerCuenta ()
    {
        return cuenta;
    }

 
  public void setCuenta(String cuenta) {
    this.cuenta = cuenta;
  }

 
  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  
  public double getTipoInterés() {
    return tipoInterés;
  }

  
  public void setTipoInterés(double tipoInterés) {
    this.tipoInterés = tipoInterés;
  }
}
