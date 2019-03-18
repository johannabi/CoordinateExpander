package de.uni_koeln.spinfo.demo;

import java.util.ArrayList;
import java.util.List;

import de.uni_koeln.spinfo.data.NewToken;
import de.uni_koeln.spinfo.preprocessing.MateTagger;
import de.uni_koeln.spinfo.workflow.CoordinateExpander;
import is2.lemmatizer.Lemmatizer;
import is2.tag.Tagger;
import is2.tools.Tool;

public class App {
	
	/**
	 * Contains demo application to show how the coordinate exapander
	 * is working
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<String> coordinates = new ArrayList<String>();
		coordinates.add("Bau- und Landmaschinen");
		coordinates.add("Deutsch-, Franz�sisch- und Englischkenntnisse");
		coordinates.add("gute Deutsch-, Franz�sisch- und sehr gute Englischkenntnisse");
		
		CoordinateExpander ce = new CoordinateExpander();
		Tool lemmatizer = new Lemmatizer(
				"src/main/resources/nlp/sentencedata_models/ger-tagger+lemmatizer+morphology+graph-based-3.6/lemma-ger-3.6.model",
				false);
		Tool tagger = new Tagger(
				"src/main/resources/nlp/sentencedata_models/ger-tagger+lemmatizer+morphology+graph-based-3.6/tag-ger-3.6.model");
		
		for(String s : coordinates) {
			
			List<NewToken> senTokens = MateTagger.setLexicalData(s, lemmatizer, null, tagger);
			
			List<List<NewToken>> result = ce.resolve(senTokens, lemmatizer);
			for(List<NewToken> r : result) {
				System.out.println(r);
			}
			System.out.println("--------------");
		}

	}

}
