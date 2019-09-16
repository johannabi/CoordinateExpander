package de.uni_koeln.spinfo.data;

import java.io.Serializable;

public class Token implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String token, lemma, posTag;
	
	protected boolean ieToken, modifierToken;
	
	public Token() {
		//default JPA Construtor
	}
	
	public Token(String token, String lemma, String posTag) {
		this.token = token;
		this.lemma = lemma;
		this.posTag = posTag;
	}

	public Token(String token, String lemma, String posTag, boolean isInformationEntity) {
		this.token = token;
		this.lemma = lemma;
		this.posTag = posTag;
		this.ieToken = isInformationEntity;
	}

	public String getToken() {
		return token;
	}

	public String getLemma() {
		return lemma;
	}

	public String getPosTag() {
		return posTag;
	}
	
	public boolean isModifier() {
		return modifierToken;
	}
	
	public void setModifier(boolean modifierToken) {
		this.modifierToken = modifierToken;	
	}
	
	public boolean isInformationEntity() {
		return ieToken;
	}
	
	public void setIEToken(boolean ieToken) {
		this.ieToken = ieToken;
	}
	
	@Override
	public String toString() {
		return token;
	}

	
	
	
}
