# Java8StarwarsAPI

Prueba Técnica Conexa

## Descripción

Esta aplicación es una API RESTful desarrollada en Java utilizando Spring Boot. Proporciona información sobre personas, películas, naves espaciales y vehículos del universo de Star Wars.

## Requisitos

- Java 8
- Postman O alguna alternativa para hacer peticiones a una API

## Instalación
1. Clona el repositorio:
    
   ```sh
   git clone https://github.com/AgsOtero/SWAPI-ConexaChallenge.git
   cd java8StarwarsAPI
   ```
   
3. Compila y empaqueta la aplicación:

```sh
mvn clean package
```

3. Ejecuta la aplicación:
```
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Uso

La API expone varios endpoints para interactuar con los datos del universo de Star Wars.

La documentación de la API se genera automáticamente con Swagger. Para acceder a la documentación interactiva de Swagger, inicia la aplicación y navega a [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

La seguridad esta desactivada para poder ver la documentacion de Swagger sin autenticacion.

![image](https://github.com/AgsOtero/SWAPI-ConexaChallenge/assets/42652311/9e6fa058-6ee1-448c-9787-bcff28587cd7)


### Autenticacion
Para autenticarse, basta con hacer una peticion post al endpoint http://localhost:8080/user, agregando el Header User y Password. A terminos practicos, y como esta app es un ejercicio, User puede ser cualquier cosa y la contraseña no es necesaria, pero esto puede ser modificado sin problemas


![image](https://github.com/AgsOtero/SWAPI-ConexaChallenge/assets/42652311/8f1c27f5-7ad7-4816-b2c3-7d62d696c129)


Esto devuelve un Bearer token

![image](https://github.com/AgsOtero/SWAPI-ConexaChallenge/assets/42652311/cfdd1aba-9baf-4ecb-b786-d47bfa1325e9)

Que debemos copiar y (Incluyendo la palabra Bearer) y pegarlo en la pestaña Authorization, eligiendo en el dropdown Type, Bearer Token.

![image](https://github.com/AgsOtero/SWAPI-ConexaChallenge/assets/42652311/50ef53b6-f275-4f0a-974f-df783cf42c46)

Y con eso ya podemos usar los endpoints de la App

## Endpoints

#### Films

- **GET** `/api/v1/film`
  - **Descripción**: Obtiene una lista de todas las películas.
  - **Respuesta**: `200 OK` con una lista de películas.

- **GET** `/api/v1/film/id/{id}`
  - **Descripción**: Obtiene una película por su ID.
  - **Parámetros**:
    - `id` (int): ID de la película.
  - **Respuesta**: `200 OK` con la película solicitada o `404 Not Found` si no se encuentra.

- **GET** `/api/v1/film/title/{title}`
  - **Descripción**: Obtiene una película por su título.
  - **Parámetros**:
    - `title` (String): Título de la película.
  - **Respuesta**: `200 OK` con la película solicitada o `404 Not Found` si no se encuentra.

#### People

- **GET** `/api/v1/people`
  - **Descripción**: Obtiene una lista de todas las personas.
  - **Parámetros**:
    - `page` (int, opcional): Número de página para la paginación (default: 1).
    - `limit` (int, opcional): Cantidad de registros por página (default: 10).
  - **Respuesta**: `200 OK` con una lista de personas.

- **GET** `/api/v1/people/id/{id}`
  - **Descripción**: Obtiene una persona por su ID.
  - **Parámetros**:
    - `id` (int): ID de la persona.
  - **Respuesta**: `200 OK` con la persona solicitada o `404 Not Found` si no se encuentra.

- **GET** `/api/v1/people/name/{name}`
  - **Descripción**: Obtiene una persona por su nombre.
  - **Parámetros**:
    - `name` (String): Nombre de la persona.
  - **Respuesta**: `200 OK` con la persona solicitada o `404 Not Found` si no se encuentra.

#### Starships

- **GET** `/api/v1/starship`
  - **Descripción**: Obtiene una lista de todas las naves espaciales.
  - **Parámetros**:
    - `page` (int, opcional): Número de página para la paginación (default: 1).
    - `limit` (int, opcional): Cantidad de registros por página (default: 10).
  - **Respuesta**: `200 OK` con una lista de naves espaciales.

- **GET** `/api/v1/starship/id/{id}`
  - **Descripción**: Obtiene una nave espacial por su ID.
  - **Parámetros**:
    - `id` (int): ID de la nave espacial.
  - **Respuesta**: `200 OK` con la nave espacial solicitada o `404 Not Found` si no se encuentra.

- **GET** `/api/v1/starship/name/{name}`
  - **Descripción**: Obtiene una nave espacial por su nombre.
  - **Parámetros**:
    - `name` (String): Nombre de la nave espacial.
  - **Respuesta**: `200 OK` con la nave espacial solicitada o `404 Not Found` si no se encuentra.

- **GET** `/api/v1/starship/model/{model}`
  - **Descripción**: Obtiene una nave espacial por su modelo.
  - **Parámetros**:
    - `model` (String): Modelo de la nave espacial.
  - **Respuesta**: `200 OK` con la nave espacial solicitada o `404 Not Found` si no se encuentra.

#### Vehicles

- **GET** `/api/v1/vehicle`
  - **Descripción**: Obtiene una lista de todos los vehículos.
  - **Parámetros**:
    - `page` (int, opcional): Número de página para la paginación (default: 1).
    - `limit` (int, opcional): Cantidad de registros por página (default: 10).
  - **Respuesta**: `200 OK` con una lista de vehículos.

- **GET** `/api/v1/vehicle/id/{id}`
  - **Descripción**: Obtiene un vehículo por su ID.
  - **Parámetros**:
    - `id` (int): ID del vehículo.
  - **Respuesta**: `200 OK` con el vehículo solicitado o `404 Not Found` si no se encuentra.

- **GET** `/api/v1/vehicle/name/{name}`
  - **Descripción**: Obtiene un vehículo por su nombre.
  - **Parámetros**:
    - `name` (String): Nombre del vehículo.
  - **Respuesta**: `200 OK` con el vehículo solicitado o `404 Not Found` si no se encuentra.

- **GET** `/api/v1/vehicle/model/{model}`
  - **Descripción**: Obtiene un vehículo por su modelo.
  - **Parámetros**:
    - `model` (String): Modelo del vehículo.
  - **Respuesta**: `200 OK` con el vehículo solicitado o `404 Not Found` si no se encuentra.

#### Usuarios

- **POST** `/user`
  - **Descripción**: Autentica a un usuario y devuelve un token JWT.
  - **Parámetros**:
    - `user` (String): Nombre de usuario.
    - `password` (String): Contraseña.
  - **Respuesta**: `200 OK` con los detalles del usuario y el token JWT.

