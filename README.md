Este proyecto es una aplicación de consola desarrollada en Java que simula la gestión básica de datos de odontólogos utilizando una Base de Datos en Memoria H2. Incluye el uso de JDBC para la persistencia de datos y Log4j2 para el registro de eventos de la aplicación.

Tecnologías Utilizadas
Lenguaje: Java (JDK 25 o superior)

Persistencia: JDBC (Java Database Connectivity)

Base de Datos: H2 (Base de Datos en Memoria)

Nota: La base de datos se recrea en cada ejecución.

Logging: Log4j2 (para manejar registros de información, advertencias y errores).

Configuración del Proyecto
Dependencias
Para ejecutar este proyecto, necesitas las siguientes librerías (archivos JAR) en tu Classpath:

Driver H2:

h2-2.4.240.jar (o la versión más reciente)

Librerías de Log4j2:

log4j-api-2.25.2.jar

log4j-core-2.25.2.jar

Configuración de Logs
El proyecto utiliza un archivo de configuración llamado log4j2.properties (ubicado en src/ o src/main/resources) para gestionar los logs.

Los mensajes de log (INFO, WARN, ERROR) se guardan en el archivo: logs/clinica.log.

La configuración utiliza un RollingFileAppender para rotar el archivo cuando alcanza un tamaño máximo.

Ejecución
Para ejecutar la aplicación:

Todas las librerías mencionadas están incluidas en el Classpath de tu IDE (IntelliJ IDEA) o en la línea de comandos.

Ejecutar la clase principal: Clinica.java.

Flujo de la Aplicación
Al ejecutarse, la clase Clinica.java realiza el siguiente flujo de base de datos:

Se establece la conexión a la base de datos H2.

Se crea la tabla ODONTOLOGO.

Se insertan dos registros de odontólogos.

Se consulta y se imprime la tabla completa.

Se ejecuta una Transacción (JDBC) para:

Actualizar el nombre de un odontólogo.

Eliminar el registro de otro odontólogo.

Se consultan y se imprimen los datos actualizados.

📄 Archivo de Logs
Todos los eventos de la aplicación (inserciones, actualizaciones, eliminaciones y errores) se registran en:

logs/clinica.log
