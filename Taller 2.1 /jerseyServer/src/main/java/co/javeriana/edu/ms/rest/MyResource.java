package co.javeriana.edu.ms.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it" ;
    }

    //myresource/hello?name=Mauren
    @GET()
    @Path("hello")   
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("name") String name) {
        return "Hello " + name;
    }

    @GET()
    @Path("hello-2/{nombre}")   
    @Produces(MediaType.TEXT_PLAIN)
    public String hello2(@PathParam("nombre") String nombre) {
        return "Hello 2 " + nombre;
    }

    //http://localhost:8080/myresource/user?name=Carlos
    @GET()
    @Path("user")   
    @Produces(MediaType.TEXT_PLAIN)
    public String hello3(@QueryParam("name") String name) {
        return "Hello " + name;
    }

    @GET()
    @Path("user")   
    @Produces(MediaType.TEXT_HTML)
    public String hello4(@QueryParam("name") String name) {
        return "<html> <title> Rest Server </title> <body> <h1> Bienvenido " + name + " </h1> </body> </html>";
    }

    @GET()
    @Path("multiplicar/{num1}")   
    @Produces(MediaType.TEXT_PLAIN)
    public String hello5(@PathParam("num1") String num1, @QueryParam("num2") int n2) {
        int n1 = Integer.parseInt(num1);
        return "El resultado de la multiplicación es: " + n1 * n2;
    }
}
