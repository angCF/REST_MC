package javeriana.ms.divisor;

public class Division {
    private String port;
    private double num1;
    private double num2;
    private double resultado;
    private String usuario;
    private String fecha;

    public Division() {
    }

    public Division(String port, double num1, double num2, double resultado, String usuario, String fecha) {
        this.port = port;
        this.num1 = num1;
        this.num2 = num2;
        this.resultado = resultado;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public double getNum1() {
        return num1;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public double getNum2() {
        return num2;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
