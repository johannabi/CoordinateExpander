package de.uni_koeln.spinfo.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.uni_koeln.spinfo.data.Token;
import de.uni_koeln.spinfo.preprocessing.MateTagger;
import de.uni_koeln.spinfo.util.Util;
import de.uni_koeln.spinfo.workflow.CoordinateExpander;
import is2.lemmatizer.Lemmatizer;
import is2.tag.Tagger;
import is2.tools.Tool;

public class App {
	
	private static File newCoordinatesFile = new File("src/main/resources/compounds/possibleCompounds.txt");
	
	/**
	 * Contains demo application to show how the coordinate exapander
	 * is working
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<String> coordinates = new ArrayList<String>();
		coordinates.add("Alten- und Krankenpflegefachkraft");
		coordinates.add("Deutsch-, Französisch- und Englischkenntnisse");
		coordinates.add("Gute Deutsch-, Französisch- sowie sehr gute Englischkenntnisse sind von Vorteil.");
		
		CoordinateExpander ce = new CoordinateExpander(newCoordinatesFile);
		Tool lemmatizer = new Lemmatizer(
				"src/main/resources/nlp/sentencedata_models/ger-tagger+lemmatizer+morphology+graph-based-3.6/lemma-ger-3.6.model",
				false);
		Tool tagger = new Tagger(
				"src/main/resources/nlp/sentencedata_models/ger-tagger+lemmatizer+morphology+graph-based-3.6/tag-ger-3.6.model");
		
		for(String s : coordinates) {
			
			List<Token> senTokens = MateTagger.setLexicalData(s, lemmatizer, null, tagger);
			
			List<List<Token>> result = ce.resolve(senTokens, lemmatizer);
			for(List<Token> r : result) {
				System.out.println(r);
			}
			System.out.println("--------------");
		}
		
		Util.writeNewCoordinations(ce.getPossResolvations(), newCoordinatesFile);

	}

}
