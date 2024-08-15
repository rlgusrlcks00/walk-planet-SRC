package com.cero.cm.config.config;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	private final String refreshtoken;

	public JwtResponse(String jwttoken, String refreshtoken) {
		this.jwttoken = jwttoken;
		this.refreshtoken = refreshtoken;
	}


	public String getToken() {
		return this.jwttoken;
	}
}
