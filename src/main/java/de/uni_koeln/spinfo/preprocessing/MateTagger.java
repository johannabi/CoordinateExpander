package de.uni_koeln.spinfo.preprocessing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.uni_koeln.spinfo.data.Token;
import is2.data.SentenceData09;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class MateTagger {
	
	private static TokenizerModel tokenizeModel = null;
	
	/**
	 * adds lexical data to the given ExtractionUnits (pos-tags, morph-tags, lemma)
	 * 
	 * @param extractionUnits
	 * @throws IOException
	 */
	public static List<List<Token>> setLexicalData(List<String> sentencesToTag, is2.tools.Tool lemmatizer,
			is2.tools.Tool morphTagger, is2.tools.Tool tagger) throws IOException {
		if (tokenizeModel == null)
			initialize();
		//IETokenizer tokenizer = new IETokenizer();
		boolean lexicalDataIsStoredInDB;
		SentenceData09 sd = null;
		List<List<Token>> toReturn = new ArrayList<List<Token>>();
		for (String sentence : sentencesToTag) {

			lexicalDataIsStoredInDB = true;
			sd = new SentenceData09();
//			if (extractionUnit.getTokens() == null) {
				lexicalDataIsStoredInDB = false;
				sd.init(tokenizeSentence("<root> " + sentence));
//			} else {
//
//				sd.init(extractionUnit.getTokens());
//			}
			if (lemmatizer != null) {
				//if (extractionUnit.getLemmata() == null) {
					lexicalDataIsStoredInDB = false;
					lemmatizer.apply(sd);
				//}
			}
			if (morphTagger != null) {
				morphTagger.apply(sd);
			}
			if (tagger != null) {
//				if (extractionUnit.getPosTags() == null) {
					lexicalDataIsStoredInDB = false;
					tagger.apply(sd);
//				} else {
//					sd.setPPos(extractionUnit.getPosTags());
//					sd.setLemmas(extractionUnit.getLemmata());
//				}

			}
			List<Token> senTokens = new ArrayList<Token>();
			for (int i = 0; i < sd.forms.length; i++) {
				senTokens.add(new Token(sd.forms[i], sd.plemmas[i], sd.ppos[i]));
			}
			toReturn.add(senTokens);
			
		}
		return toReturn;
	}

	public static List<Token> setLexicalData(String sentence, is2.tools.Tool lemmatizer, is2.tools.Tool morphTagger,
			is2.tools.Tool tagger) {
		if (tokenizeModel == null)
				initialize();
//		IETokenizer tokenizer = new IETokenizer();
		SentenceData09 sd = new SentenceData09();

		sd.init(tokenizeSentence("<root> " + sentence));

		if (lemmatizer != null)
			lemmatizer.apply(sd);

		if (morphTagger != null)
			morphTagger.apply(sd);

		if (tagger != null)
			tagger.apply(sd);
		

//		eu.setTokens(sd.forms);
//		eu.setLemmata(sd.plemmas);
//		eu.setPosTags(sd.ppos);	
		
		List<Token> toReturn = new ArrayList<Token>();
		for (int i = 0; i < sd.forms.length; i++) {
			toReturn.add(new Token(sd.forms[i], sd.plemmas[i], sd.ppos[i]));
		}
		
		return toReturn;

	}

	public static String[] getLemmata(String[] tokens, is2.tools.Tool lemmatizer) {
		SentenceData09 sd = new SentenceData09();
		sd.init(tokens);
		
		if (lemmatizer != null) 
			lemmatizer.apply(sd);
		
		
		return sd.plemmas;
	}
	
	private static String[] tokenizeSentence(String sentence) {
		String tokens[] = null;
		Tokenizer tokenizer = new TokenizerME(tokenizeModel);
		tokens = tokenizer.tokenize(sentence);
		return tokens;
	}
	
	private static void initialize() {
		InputStream modelIn = null;
		try {
			modelIn = new FileInputStream("src/main/resources/nlp/openNLPmodels/de-token.bin");
			tokenizeModel = new TokenizerModel(modelIn);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
