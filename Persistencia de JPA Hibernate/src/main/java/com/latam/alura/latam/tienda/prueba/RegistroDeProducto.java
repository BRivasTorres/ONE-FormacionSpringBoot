package com.latam.alura.latam.tienda.prueba;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.latam.alura.latam.dao.ProductoDAO;
import com.latam.alura.latam.tienda.modelo.Producto;
import com.latam.alura.latam.utils.JPAUtils;

public class RegistroDeProducto {

	public static void main(String[] args) {
		Producto celular = new Producto();
		celular.setNombre("Samsung");
		celular.setDescription("telefono usado");
		celular.setPrecio(new BigDecimal("1000"));

		
		EntityManager em = JPAUtils.getEntityManager();
		
		ProductoDAO productoDao = new ProductoDAO(em);
		
		productoDao.guardar(celular);
		
		em.getTransaction().begin();
		
		em.getTransaction().commit();
		em.close();
	}
}
