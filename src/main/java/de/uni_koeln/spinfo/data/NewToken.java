package de.uni_koeln.spinfo.data;

//TODO Klassenname?
public class NewToken {

	protected String token, lemma, posTag;
	
	protected boolean ieToken, modifierToken;
	
	public NewToken(String token, String lemma, String posTag) {
		this.token = token;
		this.lemma = lemma;
		this.posTag = posTag;
	}

	public NewToken(String string, String lemma2, String posTag2, boolean isInformationEntity) {
		// TODO Auto-generated constructor stub
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
