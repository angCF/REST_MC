# Taller 02 - Microservicios, REST y Cloud
En este repositorio se encuentra la solucion al segundo taller de microservicios.

## Primera parte - Jersey

#### Para servidor y cliente - Limpiar e instalar.
mvn clean install

Posteriormente, compilar de la siguiente manera:

#### Compilar servidor.
mvn exec:java -Djaveriana.edu.ms.rest.Main

#### Compilar cliente.
mvn exec:java -Djaveriana.edu.ms.rest.client.RestClientMain

#### Observación.
En el cliente se encuentran las operaciones de POST, PUT y DELETE comentareadas. De entrada lo primero que hará el programa será un GET que muestra la información en formato JSON, sin embargo hay otro GET que muestra información en formato XML. Para probar la funcionalidad del POST: se crea un cliente y este se pasa como parámetro, para el PUT y DELETE se pasan parámetros con base a la existencia de los paseos que se encuentran en el archivo data.json del servidor.

## Segunda parte - Spring boot

#### Para servidores y clientes.
mvn clean install

Posteriormente, compilar de la siguiente manera:

#### Compilar Eureka.
mvn spring-boot:run

#### Compilar sumador. 
Instancia 1: SERVER_PORT=9991 mvn spring-boot:run
Instancia 2: SERVER_PORT=9992 mvn spring-boot:run

#### Compilar restador.
Instancia 1: SERVER_PORT=9981 mvn spring-boot:run
Instancia 2: SERVER_PORT=9982 mvn spring-boot:run

#### Compilar multiplicador.
Instancia 1: SERVER_PORT=9971 mvn spring-boot:run
Instancia 2: SERVER_PORT=9972 mvn spring-boot:run

#### Compilar divisor.
Instancia 1: SERVER_PORT=9961 mvn spring-boot:run
Instancia 2: SERVER_PORT=9962 mvn spring-boot:run

#### Compilar calculadora.
mvn spring-boot:run

### URLs para invocar servicios

#### Nota. El número de puerto puede cambiar, este es sólo un ejemplo.
