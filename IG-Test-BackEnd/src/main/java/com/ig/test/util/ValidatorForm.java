package com.ig.test.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class ValidatorForm {
	
	private Pattern PATTERN_FORMATO_SOLO_LETRAS = Pattern.compile("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*");

	private String[] PALABRAS_RESERVADAS = { "script ", " text/javascript ", "select ", "from ", "' ",
			"= ", "& ", "\" ", "revoke ", "grant ", "set ", "cast ", "case ", "close ", "fetch ", "open ", "rollback ",
			"cursor ", "declare ", "allocate ", "drop ", "delete ", "insert ", "update ", "replace ", "truncate ",
			"alter ", "create ", "commit ", "describe ", };

	/**
	 * Metodo que validad si un campo es SOLO texto a-z
	 * 
	 * @param texto
	 * @param longitud
	 * @return
	 */
	public boolean esCampoTexto(String texto, int longitud) {
		texto = texto != null ? texto.trim() : "";
		if (PATTERN_FORMATO_SOLO_LETRAS.matcher(texto).matches() && StringUtils.isNotBlank(texto)
				&& texto.length() <= longitud && validarPalabrasReservadas(texto))
			return true;
		return false;
	}

	/**
	 * Metodo si validad si un campo es SOLO numerico 0-9
	 * 
	 * @param texto
	 * @param longitud
	 * @return
	 */
	public boolean esCampoNumerico(String texto, int longitud) {
		texto = texto != null ? texto.trim() : "";
		if (StringUtils.isNumeric(texto) && StringUtils.isNotBlank(texto) && texto.length() <= longitud
				&& validarPalabrasReservadas(texto))
			return true;
		return false;
	}
	public boolean esCampoNumerico1(String texto, int longitud) {
		texto = texto != null ? texto.trim() : "";
		if (StringUtils.isNumeric(texto) && StringUtils.isNotBlank(texto) && texto.length() <= longitud
				&& validarPalabrasReservadas(texto))
			return true;
		return false;
	}

	/**
	 * Metodo que validad la longitud de un campo
	 * 
	 * @param texto
	 * @param longitud
	 * @return
	 */
	public boolean longitudTextoVacio(String texto, int longitud) {
		texto = texto != null ? texto.trim() : "";
		if (texto.length() <= longitud && validarPalabrasReservadas(texto) && StringUtils.isNotBlank(texto))
			return true;
		return false;
	}

	/**
	 * Motodo que valida si un campo es alfanumerico, para las direcciones se
	 * permite el simbol "#".
	 * 
	 * @param texto
	 * @param longitud
	 * @return
	 */
	public boolean esCampoAlfanumerico(String texto, int longitud) {
		texto = texto != null ? texto.trim() : "";
		if (StringUtils.isNotBlank(texto) && StringUtils.isAlphanumericSpace(texto) && texto.length() <= longitud
				&& validarPalabrasReservadas(texto))
			return true;
		return false;
	}
	
	public boolean validaCampo(String texto, int longitud) {
		texto = texto != null ? texto.trim() : "";
		if (StringUtils.isNotBlank(texto) && validarPalabrasReservadas(texto) && texto.length() <= longitud)
			return true;
		return false;
	}

	/**
	 * Metodo que valida palabras prohibidas para hacer sql injection
	 * 
	 * @param cadena
	 * @return
	 */
	public boolean validarPalabrasReservadas(String cadena) {
		cadena = cadena + " ";
		boolean valido = true;
		for (String dat : PALABRAS_RESERVADAS) {
			if (cadena.toLowerCase().contains(dat)) {
				valido = false;
				break;
			}
		}
		return valido;
	}

	/**
	 * Metodo que validad la longitud de un campo
	 * 
	 * @param texto
	 * @param longitud
	 * @return
	 */
	public boolean longitudTexto(String texto, int longitud) {
		texto = texto != null ? texto.trim() : "";
		if (texto.length() <= longitud && validarPalabrasReservadas(texto))
			return true;
		return false;
	}
	
	public boolean validaRangoFechas(String  dateInitial, String dateFinal) throws ParseException {
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(dateInitial); 
		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(dateFinal); 
		if(date1.after(date2))
			 return false;
		if(date2.before(date1))
			 return false;
		
		return true;
	}
	
	public boolean validNull(String text) {
		if(null != text)
				return true;
		return false;
	}

}
