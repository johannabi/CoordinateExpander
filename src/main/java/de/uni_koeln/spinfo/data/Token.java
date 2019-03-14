package de.uni_koeln.spinfo.data;

//TODO Klassenname?
public class Token {

	private String token, lemma, posTag;
	
	public Token(String token, String lemma, String posTag) {
		this.token = token;
		this.lemma = lemma;
		this.posTag = posTag;
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
	
	@Override
	public String toString() {
		return token;
	}
	
	
}
