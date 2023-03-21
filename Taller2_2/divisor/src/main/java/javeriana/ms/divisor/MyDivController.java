package javeriana.ms.divisor;

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
public class MyDivController {
    @Autowired
    Environment environment;

    @GetMapping("/division")
    public String div(@RequestParam int a, @RequestParam int b, @RequestParam String user) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("d-MM-yyyy, HH:mm:ss ");
        String date = dateFormat.format(new Date());

        String port = environment.getProperty("local.server.port");

        if (b == 0) {
            throw new Exception("No es posible dividir entre 0, cambie el valor de << b >>.");
        }

        int result = a / b;

        Division newDivision = new Division(port, a, b, result, user, date);
        this.guardarDivisionArchivo(newDivision);

        String response = "Resultado: " + result + " - Microservicio división corriendo en el puerto: " + port;
        return response;
    }

    @GetMapping("/div/historial")
    public ArrayList<Division> getJSON() throws IOException {
        return this.leerData();
    }

    public ArrayList<Division> leerData() throws IOException {
        Gson gson = new Gson();
        String fichero = "";

        try (BufferedReader br = new BufferedReader(new FileReader("division.json"))) {

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

        Division[] divisions = gson.fromJson(fichero, Division[].class);
        ArrayList<Division> divisionsList = new ArrayList<Division>();
        for (int i = 0; i < divisions.length; i++) {
            divisionsList.add(divisions[i]);
        }
        return divisionsList;
    }

    public ArrayList<Division> guardarDivisionArchivo(Division division) throws IOException {
        ArrayList<Division> data = this.leerData();

        Division divisionNew = new Division(division.getPort(), division.getNum1(), division.getNum2(),
                division.getResultado(), division.getUsuario(), division.getFecha());
        data.add(divisionNew);
        return this.persistirData(data);
    }

    public ArrayList<Division> persistirData(ArrayList<Division> data) throws IOException {
        String path = "division.json";

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
