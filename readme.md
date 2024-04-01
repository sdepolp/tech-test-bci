#API de Creación de Usuario
Esta API proporciona un único endpoint para la creación de usuarios.
## Endpoint
### Creación de usuario:

* **URL**

`/users/create`

* **Método HTTP**

`POST`

* **Parámetros de la solicitud**

| **Nombre**	 | **Tipo**	 | **Descripción**                                                   |
|-------------|-----------|-------------------------------------------------------------------|
| email	      | String	   | Correo electrónico del usuario (obligatorio)                      |
| password    | 	String	  | Contraseña del usuario (obligatorio)                              |
| phones      | Array	    | Lista de objetos que representan teléfonos del usuario (opcional) |
**Estructura de phones**

| **Nombre**	  | **Tipo**	 | **Descripción**                       |
|--------------|-----------|---------------------------------------|
| number	      | String	   | Número de teléfono       | 
| cityCode	    | String	   | Código de ciudad del teléfono |
| countryCode	 | String	   | Código de país del teléfono |


* **Ejemplo de cuerpo de la solicitud**
```
{
    "email": "sdepolp@gmail.com",
    "password": "Sdp1234",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        }
    ]
}
```

* **Respuestas**
  * **Códigos de estado HTTP**

    * **201 Created**: El usuario se ha creado exitosamente.
    * **400 Bad Request**: La solicitud es incorrecta o incompleta. Se proporciona información adicional sobre el error en el cuerpo de la respuesta.
  * **Cuerpo de respuesta en caso de éxito (201 Created)** 
```
{
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "created": "2024-03-30T12:00:00Z",
    "modified": "2024-03-30T12:00:00Z",
    "lastLogin": "2024-03-30T12:00:00Z",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "active": true
}
```
* **Cuerpo de respuesta en caso de error (400 Bad Request)**
```
{
    "mensaje": "Correo electrónico no válido."
}
```

* **Ejemplo de Uso**
```
curl -X POST http://localhost:8080/users/create \
-H "Content-Type: application/json" \
-d '{
        "email": "sdepolp@gmail.com",
        "password": "Sdp1234",
        "phones": [
            {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
            }
        ]
    }'
```