package com.latam.alura.latam.dao;

import javax.persistence.EntityManager;

import com.latam.alura.latam.tienda.modelo.Producto;

public class ProductoDAO {
	private EntityManager em;
	
	public void ProductoDao(EntityManager em) {
		this.em = em;		
	}
	
	public void guardar(Producto producto) {
		this.em.persist(producto);
	}
	
	
}
