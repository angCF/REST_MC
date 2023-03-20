package javeriana.ms.multiplicador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class MyMulController {
    @Autowired
    Environment environment;

    @GetMapping("/multiplicacion")
    public String multip(@RequestParam int a, @RequestParam int b, @RequestParam String user) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("d-MM-yyyy, HH:mm:ss ");
        String date = dateFormat.format(new Date());

        String port = environment.getProperty("local.server.port");
        int result = a * b;

        Multiplicacion newMultiplicacion = new Multiplicacion(port, a, b, result, user, date);
        this.guardarMultiplicacionArchivo(newMultiplicacion);

        String response = "Resultado: " + result + " - Microservicio multiplicación corriendo en el puerto: " + port;
        return response;
    }

    @GetMapping("/multip/historial")
    public ArrayList<Multiplicacion> getJSON() throws IOException {
        return this.leerData();
    }

    public ArrayList<Multiplicacion> leerData() throws IOException {
        Gson gson = new Gson();
        String fichero = "";

        try (BufferedReader br = new BufferedReader(new FileReader("multiplicacion.json"))) {

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

        Multiplicacion[] multiplicacions = gson.fromJson(fichero, Multiplicacion[].class);
        ArrayList<Multiplicacion> multiplicacionsList = new ArrayList<Multiplicacion>();
        for (int i = 0; i < multiplicacions.length; i++) {
            multiplicacionsList.add(multiplicacions[i]);
        }
        return multiplicacionsList;
    }

    public ArrayList<Multiplicacion> guardarMultiplicacionArchivo(Multiplicacion multiplicacion) throws IOException {
        ArrayList<Multiplicacion> data = this.leerData();

        Multiplicacion multiplicacionNew = new Multiplicacion(multiplicacion.getPort(), multiplicacion.getNum1(),
                multiplicacion.getNum2(), multiplicacion.getResultado(), multiplicacion.getUsuario(),
                multiplicacion.getFecha());
        data.add(multiplicacionNew);
        return this.persistirData(data);
    }

    public ArrayList<Multiplicacion> persistirData(ArrayList<Multiplicacion> data) throws IOException {
        String path = "multiplicacion.json";

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
