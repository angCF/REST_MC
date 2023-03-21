package javeriana.ms.sumador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class MySumController {
    // Spring genera el objeto
    @Autowired
    Environment environment;

    @GetMapping("/suma")
    public String sum(@RequestParam int a, @RequestParam int b, @RequestParam String user) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("d-MM-yyyy, HH:mm:ss ");
        String date = dateFormat.format(new Date());

        String port = environment.getProperty("local.server.port");
        int result = a + b;

        Suma newSuma = new Suma(port, a, b, result, user, date);
        this.guardarSumaArchivo(newSuma);

        String response = "Resultado: " + result + " -> Microservicio suma corriendo en el puerto: " + port;
        return response;
    }

    @GetMapping("/suma/historial")
    public ArrayList<Suma> getJSON() throws IOException {
        return this.leerData();
    }

    public ArrayList<Suma> leerData() throws IOException {
        Gson gson = new Gson();
        String fichero = "";

        try (BufferedReader br = new BufferedReader(new FileReader("suma.json"))) {

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

        Suma[] sumas = gson.fromJson(fichero, Suma[].class);
        ArrayList<Suma> sumasList = new ArrayList<Suma>();
        for (int i = 0; i < sumas.length; i++) {
            sumasList.add(sumas[i]);
        }
        return sumasList;
    }

    public ArrayList<Suma> guardarSumaArchivo(Suma suma) throws IOException {
        ArrayList<Suma> data = this.leerData();

        Suma sumaNew = new Suma(suma.getPort(), suma.getNum1(), suma.getNum2(), suma.getResultado(), suma.getUsuario(),
                suma.getFecha());
        data.add(sumaNew);
        return this.persistirData(data);
    }

    public ArrayList<Suma> persistirData(ArrayList<Suma> data) throws IOException {
        String path = "suma.json";

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(data);
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.leerData();
    }
}
