## Levantar el proyecto:

1. Descargar e instalar java jdk 21 [desde aqui](https://www.oracle.com/java/technologies/downloads/#jdk21-windows)
2. Configurar vscode con java [ejemplo](https://www.youtube.com/watch?v=BiIrBwPQVJM)
2. Configurar tu vscode con spring [ejemplo](https://www.youtube.com/watch?v=T6xS9t2LZl8)
4. Bajar las dependencias del archivo pom.xml
    4.1. en caso de no tener descarga mvn o mave sigue el video [ejemplo](https://youtu.be/rl5-yyrmp-0?si=OJSuUQHu0m1YcaG3)
    4.2 ejecuta este comando para poder instalar las despentencias del proyecto
        mvn dependency:resolve
5. Cambiar o renombra el archivo application-example.properties por application.properties y cambia los datos:
   * spring.datasource.url=jdbc:mariadb://localhost:tu_puerto_de_mysql/tu_bd 
   * spring.datasource.username=tu_usuario
   * spring.datasource.password=tu_contraseña
4. ejecuta el archivo que se encuentra en \src\main\java\com\softhugordc\taskmanagerappv1\TaskManagerAppV1Application.java

## Endpoints pa probar el proyecto:

* /api/tasks/get-by-id:
  * Recibe un parámetro id en la url:
  ````
  /api/tasks/get-by-id?id=asdfghjkjjhgfd
  ````
* /api/tasks/get-all-tasks:
  * No recibe parametros
* /api/tasks/create:
  * Recibe un json:
  ````
  {
  "title": "algo",
  "description": "algo"
  }
  ````
* /api/tasks/delete-by-id
  * Recibe un parámetro id en la url:
  ````
  /api/tasks/delete-by-id?id=asdfghjkjjhgfd
  ````