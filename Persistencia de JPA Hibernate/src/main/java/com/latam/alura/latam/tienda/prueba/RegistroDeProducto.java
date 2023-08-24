package com.latam.alura.latam.tienda.prueba;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.latam.alura.latam.dao.ProductoDAO;
import com.latam.alura.latam.tienda.modelo.Categoria;
import com.latam.alura.latam.tienda.modelo.Producto;
import com.latam.alura.latam.utils.JPAUtils;

public class RegistroDeProducto {

	public static void main(String[] args) {
		Categoria celulares = new Categoria("CELULARES");
		
		Producto celular = new Producto("Samsung", "telefono usado", new BigDecimal("1000"), celulares);
		
		EntityManager em = JPAUtils.getEntityManager();
		
		ProductoDAO productoDao = new ProductoDAO(em);
		
		productoDao.guardar(celular);
		
		em.flush();
		em.clear();
		em.merge(celulares);
		em.close();
	} 
}
