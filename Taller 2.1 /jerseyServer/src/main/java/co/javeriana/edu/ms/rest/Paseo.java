package co.javeriana.edu.ms.rest;

import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Paseo {
    private Integer identificador;
    private String nombre;
    private String lugarSalida;
    private String lugarLlegada;
    private String fecha;    

    public Paseo() {
    }

    public Paseo(Integer identificador, String nombre, String lugarSalida, String lugarLlegada, String fecha) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.lugarSalida = lugarSalida;
        this.lugarLlegada = lugarLlegada;
        this.fecha = fecha;
    }

    public Paseo(String fecha, Integer identificador, String lugarLlegada,String nombre, String lugarSalida) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.lugarSalida = lugarSalida;
        this.lugarLlegada = lugarLlegada;
        this.fecha = fecha;
    }

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugarSalida() {
        return lugarSalida;
    }

    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    public String getLugarLlegada() {
        return lugarLlegada;
    }

    public void setLugarLlegada(String lugarLlegada) {
        this.lugarLlegada = lugarLlegada;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
