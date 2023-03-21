package co.edu.javeriana.ws.rest.client;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONObject;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class RestClientMain {
	public static final String MY_SERVER_URL="http://localhost:8080/myresource/";
	public static void main(String args[]){
		
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(MY_SERVER_URL);
		WebTarget helloWebTarget = webTarget.path(""); 
			//Concatena servidor y el path al recurso
		
		Invocation.Builder invocationBuilder = helloWebTarget.request(MediaType.TEXT_PLAIN);
		Response response = invocationBuilder.get(); 
		System.out.println("\n\n\n");
		System.out.println("RESPONSE FROM SERVER code: "+response.getStatus());
		System.out.println("Media type: "+response.getMediaType().toString());
		String respuestaTexto = response.readEntity(String.class);
		System.out.println("Content: "+respuestaTexto);
		System.out.println("\n\n\n");

		Scanner reader = new Scanner(System.in);
		
		int op;

		do{
			System.out.println("MENU DE CLIENTE");
			System.out.println("1.Listar paseos");
			System.out.println("2.Eliminar Paseo");
			System.out.println("3.Modificar origen y destino");
			System.out.println("4.Construir Paseo");
			System.out.println("Digite opción: ");
			op = reader.nextInt();
		//TALLER: Parte 1.
			
			switch(op){
				case 1:
				//#1
					System.out.println("Consumo la lista de paseos disponibles en el lado del servidor\n");
					getRidesList();
				break;
				case 2:
					//#2
					System.out.println("Eliminado de un Paseo con identificador de la lista de paseos disponibles en el lado del servidor\n");
					Scanner reader_ = new Scanner(System.in);
					int id_;
					System.out.println("Digite el identificador del Paseo a eliminar: ");
					id_ = reader_.nextInt();
					deleteOneRide(id_);
				break;
				case 3:
					System.out.println("Administre los nuevos datos...\n");
					Scanner reader_m = new Scanner(System.in);
					int idm;
					System.out.println("Digite el identificador del Paseo a Modificar:");
					idm = reader_m.nextInt();
					
					Scanner readers= new Scanner(System.in);
					String origen, destino;

					System.out.println("Digite el nuevo Origen: ");
					origen = readers.nextLine();
					System.out.println("Digite el nuevo Destino: ");
					destino  = readers.nextLine();

					modifyRide(idm, destino, origen);
				break;
				case 4:

					Scanner readern= new Scanner(System.in);
					String originn, destinn, namen;

					Random random = new Random();
					int idn = random.nextInt(100);



					System.out.println("Digite el Nombre del Paseo: ");
					namen = readern.nextLine();
					System.out.println("Digite el Origen: ");
					originn = readern.nextLine();
					System.out.println("Digite el Destino: ");
					destinn  = readern.nextLine();

					Date daten = new Date(); // Crea un objeto Date con la fecha actual

					createRide(idn, namen, originn, destinn, daten);

				break;
				default:
				break;
			}

		}while(op>=1 && op<=4);

		
		
		
		
	}

	public static void createRide(int id, String name, String origin, String destination, Date date) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(MY_SERVER_URL);
		WebTarget helloWebTarget = webTarget.path("rides");
	
		JSONObject rideJson = new JSONObject();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy, hh:mm:ss a");
		String date_ = dateFormat.format(date);


		rideJson.put("id", id);
		rideJson.put("name", name);
		rideJson.put("departure", origin);
		rideJson.put("arrival", destination);
		rideJson.put("date", date_);

		//"date":"Jun 12, 2022, 5:00:00 AM"
		

	
		Invocation.Builder invocationBuilder = helloWebTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(rideJson.toString(), MediaType.APPLICATION_JSON));
		String respuestaTexto = response.readEntity(String.class);
	
		System.out.println("RESPONSE FROM SERVER code: " + response.getStatus());
		System.out.println("Content: " + respuestaTexto);
	}
	
	

	private static void modifyRide(int id, String destination, String origin) {
		
		///

		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(MY_SERVER_URL);
        WebTarget rideWebTarget = webTarget.path("ride").path(String.valueOf(id))
                .queryParam("origin", origin)
                .queryParam("destination", destination);
        Invocation.Builder invocationBuilder = rideWebTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.put(Entity.entity("", MediaType.APPLICATION_JSON));
        System.out.println("Response status: " + response.getStatus());
        System.out.println("Response content: " + response.readEntity(String.class));
        client.close();
	}

	public static void getRidesList() {
		try{
			Client client = ClientBuilder.newClient();
			WebTarget webTarget = client.target(MY_SERVER_URL);
			WebTarget ridesWebTarget = webTarget.path("ridesJSON");
			Invocation.Builder invocationBuilder = ridesWebTarget.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.get(); 

			System.out.println("\n\n\n");

			
			String respuestaTexto = response.readEntity(String.class);
			System.out.println("Content in JSON: "+respuestaTexto);
			System.out.println("\n\n\n");
			
/*
			List<Map<String, Object>> rides = response.readEntity(new GenericType<List<Map<String, Object>>>(){});
		
			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				System.out.println("Rides available:");
				for (Map<String, Object> ride : rides) {
					System.out.println("\n------------------\n");

					System.out.println(
						"ID: " + ride.get("id")
					+ ", Name: " + ride.get("name")
					+ ", Departure: " + ride.get("departure")
					+ ", Arrival: " + ride.get("arrival")
					+ ", Date: " + ride.get("date")
					);
				}
			} else {
				System.out.println("Error getting rides list: " + response.readEntity(String.class));
			}
			
			System.out.println("\n\n\n");

			*/

		}catch(Exception e){}
	}

	private static void deleteOneRide(int i) {
		try{
			Client client = ClientBuilder.newClient();
			WebTarget webTarget = client.target(MY_SERVER_URL);
			WebTarget rideWebTarget = webTarget.path("ride/"+i);

			Invocation.Builder invocationBuilder = rideWebTarget.request(MediaType.TEXT_PLAIN);
			Response response = invocationBuilder.delete();

			System.out.println("RESPONSE FROM SERVER code: "+response.getStatus());
			System.out.println("Content: "+response.readEntity(String.class));
		}catch(Exception e){}
	}
	

	
		

}


