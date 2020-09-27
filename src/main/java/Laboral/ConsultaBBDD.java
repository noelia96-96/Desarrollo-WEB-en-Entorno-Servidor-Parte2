package Laboral;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 
 * @author Noelia
 * @version 1.1
 * @since 1.8
 *
 */
public class ConsultaBBDD {

	public static ArrayList<Empleado> consultarEmpleados() {

		/**
		 * Declarar una lista de empleados
		 */
		ArrayList<Empleado> listaEmpleados = new ArrayList<Empleado>();

		try {

			/**
			 * Conexion a BBDD
			 */
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/empleado", "root", "");
			System.out.println("Conexion a empleado realizada con éxito");

			/**
			 * Preparar consulta
			 */

			PreparedStatement consulta = conn.prepareStatement("Select * from empleados");

			/**
			 * Ejecutar la consulta
			 */

			ResultSet resultado = consulta.executeQuery();

			/**
			 * Recorrer las dos filas con los datos
			 */
			while (resultado.next()) {
				String nombre = resultado.getString("nombre");
				String dni = resultado.getString("dni");
				String sexo = resultado.getString("sexo");
				int categoria = resultado.getInt("categoria");
				int anyos = resultado.getInt("anyos");

				Empleado empleado = new Empleado(nombre, dni, sexo.charAt(0), categoria, anyos);

				/**
				 * Guardar el empleado en una lista
				 */

				listaEmpleados.add(empleado);

			}

			/**
			 * Actualizar la BBDD Incremento anyos trabajados segundo empleado
			 * Cambiar a categoria 9 el primer empleado
			 */

			actualizar(listaEmpleados);

		} catch (Exception e) {

		}
		return listaEmpleados;

	}

	/**
	 * Metodo actualizar categoria, anyos y sueldo
	 * 
	 * @param listaEmpleados
	 */
	public static void actualizar(ArrayList<Empleado> listaEmpleados) {
		try {

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/empleado", "root", "");
			System.out.println("Conexion a empleado realizada con éxito");

			PreparedStatement consulta = conn.prepareStatement("Select * from empleados");

			consulta.executeUpdate("UPDATE empleados SET categoria = " + listaEmpleados.get(0).getCategoria()
					+ "  WHERE dni = " + listaEmpleados.get(0).dni);
			consulta.executeUpdate("UPDATE empleados SET anyos = " + listaEmpleados.get(1).anyos + " WHERE dni = "
					+ listaEmpleados.get(1).dni);
			consulta.executeUpdate("UPDATE empleados set sueldo = " + Nomina.sueldo(listaEmpleados.get(0))
					+ " WHERE dni = " + listaEmpleados.get(0).dni);

		} catch (Exception e) {

		}

	}

	public static void insertarEmpleado(Empleado empleado) {
		try {

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/empleado", "root", "");
			System.out.println("Conexion a empleado realizada con éxito");

			PreparedStatement consulta = conn.prepareStatement("Select * from empleados");
			consulta.executeQuery("INSERT into empleados VALUES('" + empleado.nombre + "', '" + empleado.dni + "', '"
					+ empleado.sexo + "', '" + empleado.getCategoria() + "', '" + empleado.anyos + "', '" + Nomina.sueldo(empleado) +"')");

		} catch (Exception e) {

		}

	}

}