package cooex.preprocessing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cooex.data.Token;
import is2.data.SentenceData09;
import is2.tools.Tool;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class MateTagger {

	private static TokenizerModel tokenizeModel = null;
	
	
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
	
	
	

	/**
	 * adds lexical data to the given ExtractionUnits (pos-tags, morph-tags, lemma)
	 * 
	 * @param extractionUnits
	 * @throws IOException
	 */
	public static List<List<Token>> setLexicalData(List<String> sentencesToTag, Tool lemmatizer,
			Tool morphTagger, Tool tagger) throws IOException {
		if (tokenizeModel == null)
			initialize();

		SentenceData09 sd = null;
		List<List<Token>> toReturn = new ArrayList<List<Token>>();
		for (String sentence : sentencesToTag) {

			sd = new SentenceData09();

			sd.init(tokenizeSentence("<root> " + sentence));

			if (lemmatizer != null) {

				lemmatizer.apply(sd);

			}
			if (morphTagger != null) {
				morphTagger.apply(sd);
			}
			if (tagger != null) {

				tagger.apply(sd);

			}
			List<Token> senTokens = new ArrayList<Token>();
			for (int i = 0; i < sd.forms.length; i++) {
				senTokens.add(new Token(sd.forms[i], sd.plemmas[i], sd.ppos[i]));
			}
			toReturn.add(senTokens);

		}
		return toReturn;
	}

	public static List<Token> setLexicalData(String sentence, Tool lemmatizer, Tool morphTagger,
			Tool tagger) {
		if (tokenizeModel == null)
			initialize();

		SentenceData09 sd = new SentenceData09();

		sd.init(tokenizeSentence("<root> " + sentence));

		if (lemmatizer != null)
			lemmatizer.apply(sd);

		if (morphTagger != null)
			morphTagger.apply(sd);

		if (tagger != null)
			tagger.apply(sd);

		List<Token> toReturn = new ArrayList<Token>();
		for (int i = 0; i < sd.forms.length; i++) {
			toReturn.add(new Token(sd.forms[i], sd.plemmas[i], sd.ppos[i]));
		}

		return toReturn;

	}

	public static String[] getLemmata(String[] tokens, Tool lemmatizer) {
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

	

}
