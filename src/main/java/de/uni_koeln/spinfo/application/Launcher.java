package de.uni_koeln.spinfo.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import de.uni_koeln.spinfo.data.Token;
import de.uni_koeln.spinfo.preprocessing.MateTagger;
import de.uni_koeln.spinfo.util.Util;
import de.uni_koeln.spinfo.workflow.CoordinateExpander;
import is2.lemmatizer.Lemmatizer;
import is2.tag.Tagger;
import is2.tools.Tool;

public class Launcher {
	
	private Logger log = LogManager.getLogger();

	private String inputFile;
	private String outputFile;

	private File possibleCompoundsFile; // = new File("src/main/resources/compounds/possibleCompounds.txt");
	private File splittedCompoundsFile; // = new File("src/main/resources/compounds/splittedCompounds.txt");

	private String lemmatizerPath;// = "src/main/resources/nlp/sentencedata_models/ger-tagger+lemmatizer+morphology+graph-based-3.6/lemma-ger-3.6.model";
	private String posTaggerPath;// = "src/main/resources/nlp/sentencedata_models/ger-tagger+lemmatizer+morphology+graph-based-3.6/tag-ger-3.6.model";
	
	public static String folder = null;

	public Launcher(String[] args) {
		
		
		
		
		this.possibleCompoundsFile = new File(folder + "/compounds/possibleCompounds.txt");
		this.splittedCompoundsFile = new File(folder + "/compounds/splittedCompounds.txt");

		this.inputFile = folder + "/input.csv";
		this.outputFile = folder + "/output.csv";
		
		this.lemmatizerPath = folder + "/nlp/sentencedata_models/ger-tagger+lemmatizer+morphology+graph-based-3.6/lemma-ger-3.6.model";
		this.posTaggerPath = folder + "/nlp/sentencedata_models/ger-tagger+lemmatizer+morphology+graph-based-3.6/tag-ger-3.6.model";
	}

	public static void main(String[] args) {
		
		Launcher.folder = args[0];
		

		Launcher launcher = new Launcher(args);
		try {
			launcher.startApp();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvValidationException e) {
			e.printStackTrace();
		}

	}

	public void startApp() throws IOException, CsvValidationException {

		// load Coordinate Expander
		CoordinateExpander ce = new CoordinateExpander(possibleCompoundsFile, splittedCompoundsFile);
		Tool lemmatizer = new Lemmatizer(lemmatizerPath, false);
		Tool tagger = new Tagger(posTaggerPath);

		// load input file
		BufferedReader br = new BufferedReader(new FileReader(inputFile));

		CSVParser parser = new CSVParserBuilder().withSeparator('\t').build();
		CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).build();
		
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		CSVWriter writer = new CSVWriter(bw, '\t', CSVWriter.DEFAULT_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

		

		// process each line in input file
		String[] next;
		String sentence;
		while ((next = reader.readNext()) != null) {
			sentence = next[0];

			List<Token> senTokens = MateTagger.setLexicalData(sentence, lemmatizer, null, tagger);

			List<List<Token>> result = ce.resolve(senTokens, lemmatizer);
			String[] out;
			
			for(List<Token> r : result) {
				
				out = new String[3];
				out[0] = sentence;
				out[1] = next[1];
				out[2] = r.toString();
				
				writer.writeNext(out);
				
			}						

		}

		reader.close();
		writer.close();
		log.info("Wrote expanded sentences to: " + outputFile);
		
		Util.writeNewCoordinations(ce.getPossResolvations(), possibleCompoundsFile);
	}

}
