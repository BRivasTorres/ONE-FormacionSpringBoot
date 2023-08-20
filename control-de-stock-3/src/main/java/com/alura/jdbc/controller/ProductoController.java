package com.alura.jdbc.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.dao.PersistenciaProducto;
import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {

	private ProductoDAO productoDAO;
	public ProductoController() {
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}
	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		try(Connection con = factory.recuperaConexion();) {
			try(PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET" 
					+ "NOMBRE = ?"
					+ ", DESCRIPCION = ?"
					+ ", CANTIDAD = ?"
					+ ", WHERE ID = ?");) {
			
				statement.setString(1, nombre);
				statement.setString(2, descripcion);
				statement.setInt(3, cantidad);
				statement.setInt(4, id);
				
				statement.execute();
				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		}
	}

	public int eliminar(Integer id) throws SQLException {
		try(Connection con = new ConnectionFactory().recuperaConexion();) {
			try(PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");) {
				statement.setInt(1, id);
				statement.execute();
				return  statement.getUpdateCount(); 
			}
		}
	}

	public List<Producto> listar() {
		return productoDAO.listar();
	}

    public void guardar(Producto producto) {
    	productoDAO.guardar(producto);
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
