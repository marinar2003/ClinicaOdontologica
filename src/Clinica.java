import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class Clinica {

    private static final String SQL_CREATE_TABLE = "DROP TABLE IF EXISTS ODONTOLOGO;" +
            "CREATE TABLE ODONTOLOGO" +
            "(" +
            "ID INT PRIMARY KEY, " +
            "MATRICULA INT NOT NULL, " +
            "NOMBRE varchar(100) NOT NULL, " +
            "APELLIDO varchar(100) NOT NULL" +
            ")";

    private static final String SQL_INSERT = "INSERT INTO ODONTOLOGO (ID, MATRICULA, NOMBRE, APELLIDO)" +
            "VALUES (?, ?, ?, ?)";

    private static final String SQL_SELECT = "SELECT * FROM ODONTOLOGO";

    private static final String SQL_UPDATE = "UPDATE ODONTOLOGO SET NOMBRE=? WHERE ID=?";

    private static final String SQL_SELECT_ID = "SELECT * FROM ODONTOLOGO WHERE ID=?";

    private static final String SQL_DELETE_ID = "DELETE FROM ODONTOLOGO WHERE ID=?";

    //private static final Logger LOGGER = Logger.getLogger(Clinica.class);
    private static final Logger LOGGER = LogManager.getLogger(Clinica.class);

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Odontologo odonto1 = new Odontologo(1, 1234, "Marina", "Revol");
        Odontologo odonto2 = new Odontologo(2, 5678, "Paula", "Carballo");

        Connection connection = null;

        try {
            connection = getConnection();

            //CREAR TABLA Y DEFINIR LAS COLUMNAS EN LA BD
            Statement statement = getConnection().createStatement();
            statement.execute(SQL_CREATE_TABLE);


            //INSERTAR VALORES EN LA BD
            PreparedStatement psInsert = connection.prepareStatement(SQL_INSERT);
            psInsert.setInt(1, odonto1.getId());
            psInsert.setInt(2, odonto1.getMatricula());
            psInsert.setString(3, odonto1.getNombre());
            psInsert.setString(4, odonto1.getApellido());
            psInsert.execute();

            psInsert.setInt(1, odonto2.getId());
            psInsert.setInt(2, odonto2.getMatricula());
            psInsert.setString(3, odonto2.getNombre());
            psInsert.setString(4, odonto2.getApellido());
            psInsert.execute();

            //CONSULTAR LOS DATOS CARGADOS EN LA TABLA
            ResultSet rs = statement.executeQuery(SQL_SELECT);
            int n = 1;
            while (rs.next()) {
                System.out.println("Los valores insertados en la tabla, fila " + n + " son:\n-ID: " + rs.getInt(1) + " -Matricula: " + rs.getInt(2) +
                        " -Nombre: " + rs.getString(3) + " -Apellido: " + rs.getString(4));
                LOGGER.info("Los valores insertados en la tabla, fila " + n + " son:\n-ID: " + rs.getInt(1) + " -Matricula: " + rs.getInt(2) +
                        " -Nombre: " + rs.getString(3) + " -Apellido: " + rs.getString(4));
                n += 1;
            }

            //ACTUALIZAT ALGUNO DE LOS DATOS DE LAS  FILAS
            //TRTANSACCION

            connection.setAutoCommit(false);

            PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE);
            String nvoNombre = "Noelia";
            psUpdate.setString(1,nvoNombre);
            psUpdate.setInt(2, odonto1.getId());
            psUpdate.execute();

            connection.commit();
            LOGGER.warn("Se actualizó el regsitro correspodneinte al ID = " + odonto1.getId());
            connection.setAutoCommit(true);

            //BORRAR UNA FILA DE LA TABLA
            //TRANSACCION

            connection.setAutoCommit(false);

            PreparedStatement psDelete = connection.prepareStatement(SQL_DELETE_ID);
            psDelete.setInt(1, odonto2.getId());
            psDelete.execute();

            connection.commit();
            LOGGER.warn("Se elimino el resgistro con ID 2");
            connection.setAutoCommit(true);

        } catch (Exception e){
            connection.rollback();
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            connection.close();
        }

        //IMPRIMIR POR CONSOLA EL VALOR MODIFICIADO
        try{
            connection = getConnection();

            int id=1;
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println("Los datos actualizados\n-ID: " + rs.getInt(1) + " -Matricula: " + rs.getInt(2) +
                        " -Nombre: " + rs.getString(3) + " -Apellido: " + rs.getString(4));
                LOGGER.info("Los datos actualizados\n-ID: " + rs.getInt(1) + " -Matricula: " + rs.getInt(2) +
                        " -Nombre: " + rs.getString(3) + " -Apellido: " + rs.getString(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            connection.close();
        }

        //IMPRIMIR/CHEQUEAR QUE SE BORRO EL REGISTRO DE ID=2

        try{
            connection = getConnection();

            Statement s =connection.createStatement();
            ResultSet rs = s.executeQuery(SQL_SELECT);
            while (rs.next()){
                System.out.println("La consulta final luego de borrar el registro con ID = 1 es:\n" +
                        " -ID: " + rs.getInt(1) + " -Matricula: " + rs.getInt(2) +
                " -Nombre: " + rs.getString(3) + " -Apellido: " + rs.getString(4));;
                LOGGER.info("La consulta final luego de borrar el registro con ID = 1 es:\n" +
                        " -ID: " + rs.getInt(1) + " -Matricula: " + rs.getInt(2) +
                        " -Nombre: " + rs.getString(3) + " -Apellido: " + rs.getString(4));
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            connection.close();
        }
    }

    //CONEXION A BASE DE DATOS
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/ClinicaOdontologica", "sa", "sa");
    }

}

