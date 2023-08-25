package com.latam.alura.latam.dao;

import javax.persistence.EntityManager;

import com.latam.alura.latam.tienda.modelo.Categoria;

public class CategoriaDAO {
	private EntityManager em;
	
	public void CategoriaDao(EntityManager em) {
		this.em = em;
	}
	
	public void guardar(Categoria categoria) {
		this.em.persist(Categoria);
	}
}
