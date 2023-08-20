package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {
	private Connection con;

	public ProductoDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Producto producto) {
    	String nombre = producto.getNombre();
    	String descripcion = producto.getDescripcion();
    	Integer cantidad = producto.getCantidad();
    	Integer maximaCantidad = 50;
    	
    	try(Connection con = new ConnectionFactory().recuperaConexion();) {
    		try(PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO(nombre, descripcion, cantidad)" + "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);) {
        		do {
        			int cantidadParaGuardad = Math.min(cantidad, maximaCantidad);
        			ejecutaRegistro(producto, statement);	
        			cantidad -= maximaCantidad;
        		} while(cantidad > 0);	
        	}
    	}  catch(SQLException e) {
    		throw new RuntimeException(e);
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

	public List<Producto> listar() {
		
		try(Connection con = new ConnectionFactory().recuperaConexion();) {

			try(PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");) {

				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				List<Producto> resultado = new ArrayList<>();
				
				while(resultSet.next()) {
					Producto fila = new Producto(resultSet.getInt("ID"), resultSet.getString("NOMBRE"), resultSet.getString("DESCRIPCION"), resultSet.getInt("CANTIDAD"));
					
					resultado.add(fila);
				}
				return resultado;
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
