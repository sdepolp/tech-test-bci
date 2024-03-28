## Creación de usuario:

**POST** http://localhost:8080/users/create

`
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
}   `