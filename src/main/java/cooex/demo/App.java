package cooex.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cooex.data.Token;
import cooex.preprocessing.MateTagger;
import cooex.util.Util;
import cooex.workflow.CoordinateExpander;
import is2.lemmatizer.Lemmatizer;
import is2.tag.Tagger;
import is2.tools.Tool;

public class App {
	
	private static File possibleCompoundsFile = new File("src/main/resources/compounds/possibleCompounds.txt");
	private static File splittedCompoundsFile = new File("src/main/resources/compounds/splittedCompounds.txt");
	
	private static String lemmatizerPath = "src/main/resources/nlp/sentencedata_models/ger-tagger+lemmatizer+morphology+graph-based-3.6/lemma-ger-3.6.model";
	private static String posTaggerPath = "src/main/resources/nlp/sentencedata_models/ger-tagger+lemmatizer+morphology+graph-based-3.6/tag-ger-3.6.model";
	
	
	private static Logger log = Logger.getLogger(App.class);
	
	/**
	 * Contains demo application to show how the coordinate exapander
	 * is working
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<String> coordinates = new ArrayList<String>();
		coordinates.add("Alten- und Krankenpflegefachkraft");
		coordinates.add("Eine hohe Motivation- und Einsatzbereitschaft");
		coordinates.add("Deutsch-, Französisch- und Englischkenntnisse");
		coordinates.add("Gute Deutsch-, Französisch- sowie sehr gute Englischkenntnisse sind von Vorteil.");
		
		CoordinateExpander ce = new CoordinateExpander(possibleCompoundsFile, splittedCompoundsFile);
		Tool lemmatizer = new Lemmatizer(lemmatizerPath, false);
		Tool tagger = new Tagger(posTaggerPath);
		
		for(String s : coordinates) {
			
			List<Token> senTokens = MateTagger.setLexicalData(s, lemmatizer, null, tagger);
			
			List<List<Token>> result = ce.resolve(senTokens, lemmatizer);
			for(List<Token> r : result) {
				log.info(r.toString());
			}
			log.info("--------------");
		}
		
		log.info("Neue Komposita:");
		for (Map.Entry<String, String> e : ce.getPossResolvations().entrySet()) {
			log.info(e);
		}
		
		Util.writeNewCoordinations(ce.getPossResolvations(), possibleCompoundsFile);

	}

}
