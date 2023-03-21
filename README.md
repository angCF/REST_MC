# Taller 02 - Microservicios y Cloud
En este repositorio se encuentra la solucion al segundo taller de microservicios.

# Integrantes
Mauren Rivera Bautista,
Katherine Castro Flórez,
Jesús Traslaviña Fuentes

# Primera parte - Jersey

## Para servidor y cliente - Limpiar e instalar.
```console
mvn clean install
```

_Posteriormente, compilar de la siguiente manera:_

## Compilar servidor.
```console
mvn exec:java -Djaveriana.edu.ms.rest.Main
```

## Compilar cliente.
```console
mvn exec:java -Djaveriana.edu.ms.rest.client.RestClientMain
```
## URLs para invocar 
### Listado de paseos en archivo JSON
http://localhost:8080/resource-travel/data/json

### Listado de paseos en archivo XML
http://localhost:8080/resource-travel/data/xml

### Crear un paseo.
Para probar la funcionalidad del POST: se crea un paseo desde el lado del cliente, y éste se pasa como parámetro en el método POST. De momento, esto no se debería modificar, pues basta con sólo cambiar la información de los campos que se encuentran inicialmente.

http://localhost:8080/resource-travel/crear/paseo

### Actualizar un paseo.
http://localhost:8080/resource-travel/actualizar/paseo?id=1&origen=Peru&destino=Canada

### Eliminar un paseo.
http://localhost:8080/resource-travel/eliminar/paseo/1


## Observación.
En el cliente se encuentran las operaciones de POST, PUT y DELETE comentareadas. De entrada lo primero que hará el programa será un GET que muestra la información en formato JSON, sin embargo hay otro GET que muestra información en formato XML. , para el PUT y DELETE se pasan parámetros con base a la existencia de los paseos que se encuentran en el archivo data.json del servidor.

# Segunda parte - Spring boot

## Para servidores y clientes - Limpiar e instalar.
```console
mvn clean install
```

_Posteriormente, compilar de la siguiente manera:_

## Compilar Eureka.
```console
mvn spring-boot:run
```

## Compilar sumador. 
### Instancia 1 
```console
SERVER_PORT=9991 mvn spring-boot:run  
```

### Instancia 2 
```console
SERVER_PORT=9992 mvn spring-boot:run
```

## Compilar restador.
### Instancia 1 
```console
SERVER_PORT=9981 mvn spring-boot:run 
```

### Instancia 2 
```console
SERVER_PORT=9982 mvn spring-boot:run
```

## Compilar multiplicador.
### Instancia 1 
```console
SERVER_PORT=9971 mvn spring-boot:run
```

### Instancia 2 
```console
SERVER_PORT=9972 mvn spring-boot:run
```

## Compilar divisor.
### Instancia 1 
```console
SERVER_PORT=9961 mvn spring-boot:run  
```

### Instancia 2 
```console
SERVER_PORT=9962 mvn spring-boot:run
```

## Compilar calculadora.
```console
mvn spring-boot:run
```

## URLs para invocar servicios
### Invocando desde << sumador >>
http://localhost:9991/suma?a=20&b=10&user=carlosp  
http://localhost:9991/suma/historial

### Invocando desde << restador >>
http://localhost:9991/resta?a=20&b=10&user=carlosp  
http://localhost:9991/resta/historial

### Invocando desde << multiplicador >>
http://localhost:9991/multiplicacion?a=20&b=10&user=carlosp  
http://localhost:9991/multip/historial

### Invocando desde << divisor >>
http://localhost:9991/division?a=20&b=10&user=carlosp  
http://localhost:9991/div/historial

### Invocando desde << calculadora >> - Cada operación
http://localhost:8888/calculadora/suma?a=20&b=10&user=carlosp  
http://localhost:8888/calculadora/resta?a=20&b=10&user=carlosp  
http://localhost:8888/calculadora/multiplicacion?a=20&b=10&user=carlosp  
http://localhost:8888/calculadora/division?a=20&b=10&user=carlosp  

### Invocando desde << calculadora >> - Historial
http://localhost:8888/calculadora/historial?operacion=suma  
http://localhost:8888/calculadora/historial?operacion=resta  
http://localhost:8888/calculadora/historial?operacion=multip  
http://localhost:8888/calculadora/historial?operacion=div  

#### Nota. El número de puerto puede cambiar en las instancias de sumador, restador, multiplicador y divisor.
