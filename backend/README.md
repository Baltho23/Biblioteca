# Biblioteca Backend – Prueba Técnica

Airton Jairo Sampayo Solano.

API REST desarrollada con **Spring Boot** para la gestión de una biblioteca.  
Permite administrar **libros**, **miembros** y **préstamos**.  
Utiliza una base de datos en memoria **H2**, ideal para pruebas rapidas.

Base de datos H2

La aplicación utiliza una base de datos en memoria H2 que se carga automáticamente al iniciar.
Puedes acceder a la consola desde:

URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:biblioteca

Usuario: sa

Contraseña: (vacía)

---

## Ejecución del proyecto

### Requisitos previos
- Tener instalado **Java 17** o superior.
- Tener configurado **gradle**.

### Pasos para ejecutar

```bash
# Clonar el repositorio
git clone https://github.com/Baltho23/Biblioteca.git

# Entrar al proyecto
cd backend

# Ejecutar la aplicación
Una vez iniciado, la API estará disponible en: http://localhost:8080


