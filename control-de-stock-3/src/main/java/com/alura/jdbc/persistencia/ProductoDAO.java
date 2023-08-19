package com.alura.jdbc.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {
	private Connection con;

	public ProductoDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Producto producto) throws SQLException {
    	String nombre = producto.getNombre();
    	String descripcion = producto.getDescripcion();
    	Integer cantidad = producto.getCantidad();
    	Integer maximaCantidad = 50;
    	
    	try(Connection con = new ConnectionFactory().recuperaConexion();) {
 
    		con.setAutoCommit(false);
    		try(PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO(nombre, descripcion, cantidad)" + "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);) {
        		do {
        			int cantidadParaGuardad = Math.min(cantidad, maximaCantidad);
        			ejecutaRegistro(producto, statement);	
        			cantidad -= maximaCantidad;
        		} while(cantidad > 0);	
        		con.commit();
        	} catch(Exception e) {
        		con.rollback();
    		}
    	}
	}
	
	private void ejecutaRegistro(Producto producto, PreparedStatement statement)
			throws SQLException {
		statement.setString(1, producto.getNombre());
		statement.setString(2, producto.getDescripcion());
		statement.setInt(3, producto.getCantidad());
						
		statement.execute();
		
		try(ResultSet resultSet = statement.getGeneratedKeys();) {
			while(resultSet.next()) {
				producto.setId(resultSet.getInt(1));
				System.out.println(String.format("Fue insertado el producto ", producto));
			}
		}
	}
}
