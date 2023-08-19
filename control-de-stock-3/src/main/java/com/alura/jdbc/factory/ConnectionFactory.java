package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	private DataSource datasource;
	
	public ConnectionFactory() {
		var pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC\", \"root\", \"Hola1234.");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("Hola1234.");
		
		this.datasource = pooledDataSource; 
	}
	
	public Connection recuperaConexion() throws SQLException {
		return this.datasource.getConnection();
	}
}
