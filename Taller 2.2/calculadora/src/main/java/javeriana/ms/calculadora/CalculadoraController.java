package javeriana.ms.calculadora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CalculadoraController {
    @Autowired
    RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/calculadora/suma")
    public String calculadoraSuma(@RequestParam int a, @RequestParam int b, @RequestParam String user) {
        String response = restTemplate.getForObject("http://sumador/suma?a={a}&b={b}&user={user}", String.class, a, b,
                user);
        return response;
    }

    @GetMapping("/calculadora/resta")
    public String calculadorResta(@RequestParam int a, @RequestParam int b, @RequestParam String user) {
        String response = restTemplate.getForObject("http://restador/resta?a={a}&b={b}&user={user}", String.class, a, b,
                user);
        return response;
    }

    @GetMapping("/calculadora/multip")
    public String calculadoraMultiplicacion(@RequestParam int a, @RequestParam int b, @RequestParam String user) {
        String response = restTemplate.getForObject("http://multiplicador/multiplicacion?a={a}&b={b}&user={user}",
                String.class, a, b, user);
        return response;
    }

    @GetMapping("/calculadora/div")
    public String calculadoraDivision(@RequestParam int a, @RequestParam int b, @RequestParam String user) {
        String response = restTemplate.getForObject("http://divisor/division?a={a}&b={b}&user={user}", String.class, a,
                b, user);
        return response;
    }

    @GetMapping("/calculadora/historial")
    public Operacion[] historialOperacion(@RequestParam String operacion) {
        Operacion[] response = null;

        if (operacion.equals("suma")) {
            return response = restTemplate.getForObject("http://sumador/{operacion}/historial", Operacion[].class,
                    operacion);
        }

        if (operacion.equals("resta")) {
            return response = restTemplate.getForObject("http://restador/{operacion}/historial", Operacion[].class,
                    operacion);
        }

        if (operacion.equals("multip")) {
            return response = restTemplate.getForObject("http://multiplicador/{operacion}/historial", Operacion[].class,
                    operacion);
        }

        if (operacion.equals("div")) {
            return response = restTemplate.getForObject("http://divisor/{operacion}/historial", Operacion[].class,
                    operacion);
        }

        return response;
    }
}
