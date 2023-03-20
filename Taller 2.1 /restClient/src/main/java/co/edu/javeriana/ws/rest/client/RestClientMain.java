package co.edu.javeriana.ws.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestClientMain {
    public static final String MY_SERVER_URL = "http://localhost:8080/resource-travel/";
    // public static final String
    // RESTCOUNTRIES_SERVER_URL="http://restcountries.eu/rest/v2/";

    public static void main(String args[]) {
        Paseo paseo = new Paseo();
        paseo.setNombre("Ivis - Ing. Contreras");
        paseo.setIdentificador(6);
        paseo.setLugarSalida("Bogotá");
        paseo.setLugarLlegada("Medellin");
        paseo.setFecha("19/02/22");

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(MY_SERVER_URL);
        // WebTarget dataPaseos = webTarget.path("myresource").queryParam("fields",
        // "name", "capital");

        WebTarget dataPaseosJSON = webTarget.path("data/json");
        // Concatena servidor y el path al recurso
        Invocation.Builder invocationBuilder = dataPaseosJSON.request(MediaType.APPLICATION_JSON);
        Response responseJSON = invocationBuilder.get();
        System.out.println("RESPONSE FROM SERVER code: " + responseJSON.getStatus());
        System.out.println("Media type: " + responseJSON.getMediaType().toString());
        String respuestaTextoJSON = responseJSON.readEntity(String.class);
        System.out.println("Content: " + respuestaTextoJSON);

        // WebTarget dataPaseosXML = webTarget.path("data/xml");
        // //Concatena servidor y el path al recurso
        // Invocation.Builder invocationBuilderXML =
        // dataPaseosXML.request(MediaType.TEXT_XML);
        // Response responseXML = invocationBuilderXML.get();
        // System.out.println("RESPONSE FROM SERVER code: "+responseXML.getStatus());
        // System.out.println("Media type: "+responseXML.getMediaType().toString());
        // String respuestaTextoXML = responseXML.readEntity(String.class);
        // System.out.println("Content: "+respuestaTextoXML);

        // WebTarget createPaseo = webTarget.path("paseo");
        // Invocation.Builder invocationBuilderPOST =
        // createPaseo.request(MediaType.APPLICATION_JSON);
        // Response responsePOST = invocationBuilderPOST.post(Entity.entity(paseo,
        // MediaType.APPLICATION_JSON));
        // String respuestaTextoPOST = responsePOST.readEntity(String.class);
        // System.out.println("RESPONSE FROM SERVER code: "+responsePOST.getStatus());
        // System.out.println("Media type: "+responsePOST.getMediaType().toString());
        // System.out.println("Content: "+respuestaTextoPOST);

        // WebTarget updatePaseo = webTarget.path("paseo").queryParam("id",
        // "1").queryParam("origen", "Peru").queryParam("destino", "Canada");
        // Invocation.Builder invocationBuilderPUT =
        // updatePaseo.request(MediaType.APPLICATION_JSON);
        // Response responsePUT = invocationBuilderPUT.put(Entity.entity(paseo,
        // MediaType.APPLICATION_JSON));
        // String respuestaTextoPUT = responsePUT.readEntity(String.class);
        // System.out.println("RESPONSE FROM SERVER code: "+responsePUT.getStatus());
        // System.out.println("Media type: "+responsePUT.getMediaType().toString());
        // System.out.println("Content: "+respuestaTextoPUT);

        // WebTarget deletePaseo = webTarget.path("paseo/1");
        // Invocation.Builder invocationBuilderDELETE =
        // deletePaseo.request(MediaType.APPLICATION_JSON);
        // Response responseDELETE = invocationBuilderDELETE.delete();
        // String respuestaTextoDELETE = responseDELETE.readEntity(String.class);
        // System.out.println("RESPONSE FROM SERVER code: "+responseDELETE.getStatus());
        // System.out.println("Media type: "+responseDELETE.getMediaType().toString());
        // System.out.println("Content: "+respuestaTextoDELETE);
    }
}