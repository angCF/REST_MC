package javeriana.ms.restador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class MySubController {
    @Autowired
    Environment environment;

    @GetMapping("/resta")
    public String subtrac(@RequestParam int a, @RequestParam int b, @RequestParam String user) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("d-MM-yyyy, HH:mm:ss ");
        String date = dateFormat.format(new Date());

        String port = environment.getProperty("local.server.port");
        int result = a - b;

        Resta newResta = new Resta(port, a, b, result, user, date);
        this.guardarRestaArchivo(newResta);

        String response = "Resultado: " + result + " -> Microservicio resta corriendo en el puerto: " + port;
        return response;
    }

    @GetMapping("/resta/historial")
    public ArrayList<Resta> getJSON() throws IOException {
        return this.leerData();
    }

    public ArrayList<Resta> leerData() throws IOException {
        Gson gson = new Gson();
        String fichero = "";

        try (BufferedReader br = new BufferedReader(new FileReader("resta.json"))) {

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

        Resta[] restas = gson.fromJson(fichero, Resta[].class);
        ArrayList<Resta> restasList = new ArrayList<Resta>();
        for (int i = 0; i < restas.length; i++) {
            restasList.add(restas[i]);
        }
        return restasList;
    }

    public ArrayList<Resta> guardarRestaArchivo(Resta resta) throws IOException {
        ArrayList<Resta> data = this.leerData();

        Resta restaNew = new Resta(resta.getPort(), resta.getNum1(), resta.getNum2(), resta.getResultado(),
                resta.getUsuario(), resta.getFecha());
        data.add(restaNew);
        return this.persistirData(data);
    }

    public ArrayList<Resta> persistirData(ArrayList<Resta> data) throws IOException {
        String path = "resta.json";

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
