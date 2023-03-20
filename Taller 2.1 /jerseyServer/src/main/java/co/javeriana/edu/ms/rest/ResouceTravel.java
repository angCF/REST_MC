package co.javeriana.edu.ms.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("resource-travel")
public class ResouceTravel {

    @GET
    @Path("/data/json")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Paseo> getJSON() throws IOException {
        return this.leerData();
    }

    @GET
    @Path("/data/xml")
    @Produces(MediaType.TEXT_XML)
    public ArrayList<Paseo> getXML() throws IOException {
        return this.leerData();
    }

    @POST
    @Path("paseo")
    @Consumes("application/json")
    @Produces("application/json")
    public ArrayList<Paseo> guardarPaseo(Paseo paseo) throws Exception {
        paseo.setIdentificador(paseo.getIdentificador());
        paseo.setNombre(paseo.getNombre() + " with a little help from Jersey and Maven");
        paseo.setLugarSalida(paseo.getLugarSalida());
        paseo.setLugarLlegada(paseo.getLugarLlegada());
        paseo.setFecha(paseo.getFecha());
        return this.guardarPaseoArchivo(paseo);
    }

    @PUT
    @Path("paseo")
    @Consumes("application/json")
    @Produces("application/json")
    public ArrayList<Paseo> actualizarPaseo(@QueryParam("id") Integer id, @QueryParam("origen") String origen,
            @QueryParam("destino") String destino) throws Exception {
        return this.actualizarPaseoData(id, origen, destino);
    }

    @DELETE
    @Path("paseo/{id}")
    @Produces("application/json")
    public ArrayList<Paseo> eliminarPaseo(@PathParam("id") String id) throws IOException {
        Integer idDelete = Integer.parseInt(id);
        this.deletePaseo(idDelete);
        return this.leerData();
    }

    public ArrayList<Paseo> leerData() throws IOException {
        Gson gson = new Gson();
        String fichero = "";

        try (BufferedReader br = new BufferedReader(new FileReader("data.json"))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                fichero += linea;
            }
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        Paseo[] paseo2 = gson.fromJson(fichero, Paseo[].class);
        ArrayList<Paseo> paseoList = new ArrayList<Paseo>();
        for (int i = 0; i < paseo2.length; i++) {
            paseoList.add(paseo2[i]);
        }
        return paseoList;
    }

    public ArrayList<Paseo> guardarPaseoArchivo(Paseo paseo) throws Exception {
        ArrayList<Paseo> data = this.leerData();

        for (Paseo element : data) {
            if (element.getIdentificador().equals(paseo.getIdentificador())) {
                throw new Exception("El paseo con ID " + paseo.getIdentificador() + " ya se encuentra registrado.");
            }
        }

        Paseo paseoNew = new Paseo(paseo.getIdentificador(), paseo.getNombre(), paseo.getLugarSalida(),
                paseo.getLugarLlegada(), paseo.getFecha());
        data.add(paseoNew);
        return this.persistirData(data);
    }

    public ArrayList<Paseo> persistirData(ArrayList<Paseo> data) throws IOException {
        String path = "data.json";

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(data);
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.leerData();
    }

    public ArrayList<Paseo> actualizarPaseoData(Integer id, String origen, String destino) throws Exception {
        ArrayList<Paseo> data = this.leerData();

        for (Paseo element : data) {
            if (element.getIdentificador().equals(id)) {
                element.setLugarLlegada(destino);
                element.setLugarSalida(origen);
                return this.persistirData(data);
            }
        }

        throw new Exception("El paseo con ID " + id + " no se encuentra registrado.");
    }

    public void deletePaseo(Integer id) throws IOException {
        ArrayList<Paseo> data = this.leerData();

        for (Iterator<Paseo> iter = data.iterator(); iter.hasNext();) {
            Paseo paseo = iter.next();
            if (paseo.getIdentificador().equals(id)) {
                iter.remove();
            }
        }
        this.persistirData(data);
    }

}
