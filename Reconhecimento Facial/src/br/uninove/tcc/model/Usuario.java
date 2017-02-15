package br.uninove.tcc.model;

import java.io.Serializable;

import android.content.ContentValues;

public class Usuario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -218740659971769700L;

	private String nome = "";
	
	private String caracteristicas = "";
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	public Usuario(String paramNome, String paramCaracteristicas) {
		nome = paramNome;
		caracteristicas = paramCaracteristicas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	
	public ContentValues getValues () {
		ContentValues values = new ContentValues();
		values.put("nome", getNome());
		values.put("caracteristicas", getCaracteristicas());
		return values;
	}
	
	@Override
	public String toString() {
		return caracteristicas;
	}
	

}
