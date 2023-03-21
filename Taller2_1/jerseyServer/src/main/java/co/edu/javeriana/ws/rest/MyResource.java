package co.edu.javeriana.ws.rest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
//import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    private List<Ride> rides = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

        

        public MyResource() {
            Gson gson = new Gson();
            try {
                BufferedReader br = new BufferedReader(new FileReader("rides.json"));
                JsonElement json = JsonParser.parseReader(br);
                JsonObject jsonObject = json.getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("rides");
                
                for (JsonElement element : jsonArray) {
                    Ride ride = gson.fromJson(element, Ride.class);
                    rides.add(ride);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        @GET
        @Path("ridesJSON")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getRides() {
            Gson gson = new Gson();
            String json = gson.toJson(rides);
            return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
            
        }

        @GET
        @Path("ridesXML")
        @Produces(MediaType.APPLICATION_XML)
        public Response getXMLRides() {
            RideList rideList = new RideList(rides);
            StringWriter writer = new StringWriter();
            
            try {
                JAXBContext context = JAXBContext.newInstance(RideList.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(rideList, writer);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            
            return Response.ok(writer.toString()).build();
        }


        @DELETE
        @Path("ride/{id}")
        @Produces(MediaType.TEXT_PLAIN)
        public Response deleteRide(@PathParam("id") int id) {
            for (Ride ride : rides) {
                if (ride.getId() == id) {
                    rides.remove(ride);
                    escribirEnArchivo();
                    return Response.ok("Paseo eliminado, restan "+rides.size()).build();
                }
            }
            return Response.status(Status.NOT_FOUND).entity("No se ha encontrado el paseo").build();
        }


        //http://localhost:8080/myresource/rides/id?name=string
        @PUT
        @Path("rides/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response updateRideName(@PathParam("id") int id, @QueryParam("name") String name) {
            // Buscar el paseo con el id especificado en la lista de paseos
            Ride rideToUpdate = null;
            for (Ride ride : rides) {
                if (ride.getId() == id) {
                    rideToUpdate = ride;
                    break;
                }
            }
            // Si no se encontró el paseo con el id especificado, retornar un error 404
            if (rideToUpdate == null) {
                return Response.status(Status.NOT_FOUND).build();
            }
            // Actualizar el nombre del paseo
            rideToUpdate.setName(name);
            escribirEnArchivo();
           
            // Retornar la respuesta con el paseo actualizado
            return Response.ok(rideToUpdate).build();
        }

        @POST
        @Path("rides")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response createRide(String requestBody) {
            Gson gson = new Gson();
            Ride newRide = gson.fromJson(requestBody, Ride.class);

            rides.add(newRide);

            escribirEnArchivo();

            return Response.ok(gson.toJson(newRide)).build();
        }

        //  http://localhost:8080/myresource/rides/{id}?origin={nuevo_origen}&destination={nuevo_destino}

        @PUT
        @Path("ride/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response updateRide(@PathParam("id") int id,
                                @QueryParam("origin") String origin,
                                @QueryParam("destination") String destination) {
            // Buscar el paseo con el id especificado en la lista de paseos
            Ride auxiliar=new Ride();
            for (Ride ride : rides) {
                if (ride.getId() == id) {
                    auxiliar.setId(id);
                    auxiliar.setName(ride.getName());
                    auxiliar.setDate(ride.getDate());
                    rides.remove(ride);
                    escribirEnArchivo();
                    auxiliar.setArrival(destination);
                    auxiliar.setDeparture(origin);
                    rides.add(auxiliar);
                    escribirEnArchivo();
                    return Response.ok(auxiliar).build();
                    
                }
            }
            // Si no se encontró el paseo con el id especificado, retornar un error 404
            return Response.status(Status.NOT_FOUND).build();           
        }








        public void escribirEnArchivo() {
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            
            for (Ride ride : rides) {
                JsonElement jsonElement = gson.toJsonTree(ride);
                jsonArray.add(jsonElement);
            }
            
            jsonObject.add("rides", jsonArray);
            String jsonString = gson.toJson(jsonObject);
            
            try (FileWriter fileWriter = new FileWriter("rides.json")) {
                fileWriter.write(jsonString);
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




	/**
     * A CONTINUACIÓN EL PRIMER EJERCICIO :
     */

    //1
    // http://localhost:8080/myresource/hello?name=Bob
    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello_QP(@QueryParam("name") String name){
        return "Hola " + name;
    }

    //2
    //http://localhost:8080/myresource/hellop/Bob
    @GET
    @Path("hellop/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello_PP(@PathParam("name") String name){
        return "Hola " + name;
    }

    //3
    // http://localhost:8080/myresource/user?name=Bob
    @GET
    @Path("user")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUser(@QueryParam("name") String name) {
         return "User: " + name;
    }

    //4
    //http://localhost:8080/myresource/userhtml?name=Bob
    @GET
    @Path("userhtml")
    @Produces(MediaType.TEXT_HTML)
    public String getUserHTML(@QueryParam("name") String name) {
        return "<html><head><title>Rest Server</title></head><body><h1>Bienvenido " + name + "</h1></body></html>";
    }

    //5
    //http://localhost:8080/myresource/multiplication/5?numberQuery=10

    @GET
    @Path("multiplication/{num}")
    @Produces(MediaType.TEXT_PLAIN)
    public String multiplication(@PathParam("num") int num, @QueryParam("numberQuery") int numberQuery) {
        return String.valueOf(num * numberQuery);
    }
///////////////////////////////////////////////////////////////////////////////////////////:)
    
        

        
}



        

    

