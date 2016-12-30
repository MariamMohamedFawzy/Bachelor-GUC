package sentiment;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class POSTaggerStemmerWithSentiment {

	private static SentiWordNetDemoCode sw;
	private static POSTaggerME tagger;
	private static InputStream modelIn = null;
	private static Properties props; 
    private static StanfordCoreNLP pipeline;

	public static void init() {
		try {
			String path = "home/swn/www/admin/dump/SentiWordNet_3.0.0_20130122.txt";
			sw = new SentiWordNetDemoCode(path);
			modelIn = new FileInputStream("en-pos-maxent.bin");
			POSModel model = new POSModel(modelIn);
			tagger = new POSTaggerME(model);
			
			props = new Properties();
			props.put("annotators", "tokenize, ssplit, pos, lemma"); 
			pipeline = new StanfordCoreNLP(props, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Object[] getSentimentOfSentences(String sentences) {
//		String[] sent = WhitespaceTokenizer.INSTANCE.tokenize(sentences);
		
		ArrayList<String> lemmas = new ArrayList<String>();
		ArrayList<String> words = new ArrayList<String>();
		
		Annotation document = pipeline.process(sentences);  

        for(CoreMap sentence: document.get(SentencesAnnotation.class))
        {    
            for(CoreLabel token: sentence.get(TokensAnnotation.class))
            {       
                String word = token.get(TextAnnotation.class);      
                String lemma = token.get(LemmaAnnotation.class); 
//                System.out.println("lemmatized version :" + lemma);
                lemmas.add(lemma);
                words.add(word);
            }
        }
		String[] lemmasArr = new String[lemmas.size()];
		String[] wordsArr = new String[words.size()];
		lemmasArr = lemmas.toArray(lemmasArr);
		wordsArr = words.toArray(wordsArr);
		
        String tags[] = tagger.tag(lemmasArr);
//		for (String tag : tags) {
//			System.out.println(tag);
//		}
		double[] sentimentPos = new double[tags.length];
		double[] sentimentNeg = new double[tags.length];
		for(int i = 0; i < tags.length; i++) {
			String tag = tags[i];
			Double pos = null;
			Double neg = null;
			if (tag.startsWith("NN")) {
				pos = sw.getPosDictionary().get(lemmas.get(i) + "#" + "n");
				neg = sw.getNegDictionary().get(lemmas.get(i) + "#" + "n");
			} else if (tag.startsWith("VB")) {
				pos = sw.getPosDictionary().get(lemmas.get(i) + "#" + "v");
				neg = sw.getNegDictionary().get(lemmas.get(i) + "#" + "v");
			} else if (tag.startsWith("JJ")) {
				pos = sw.getPosDictionary().get(lemmas.get(i) + "#" + "a");
				neg = sw.getNegDictionary().get(lemmas.get(i) + "#" + "a");
			} else if (tag.startsWith("RB")) {
				pos = sw.getPosDictionary().get(lemmas.get(i) + "#" + "r");
				neg = sw.getNegDictionary().get(lemmas.get(i) + "#" + "r");
			} else {
				pos = null;
				neg = null;
			}
			if (pos == null || neg == null) {
				sentimentPos[i] = 0;
				sentimentNeg[i] = 0;
			} else {
				sentimentPos[i] = pos;
				sentimentNeg[i] = neg;
			}
		}
		return new Object[] {sentimentPos, sentimentNeg, wordsArr, lemmasArr};
	}

	public static void close() {
		if (modelIn != null) {
			try {
				modelIn.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static void printResult(Object[] obj) {
		double[] sentimentPos = (double[]) obj[0];
		double[] sentimentNeg = (double[]) obj[1];
		String[] lemmas = (String[]) obj[3];
		for(int i = 0; i < sentimentPos.length; i++) {
			System.out.println(lemmas[i] + " : +" + sentimentPos[i] + " -" + sentimentNeg[i]);
		}
	}
	
//	public static void main(String[] args) {
//		init();
//		Object[] obj = getSentimentOfSentences("The best nordie bar to snack on. Grab a few lattes and good to go. Hot drink and cold ones too. Yummy!");
//		close();
//		printResult(obj);
//	}
}
