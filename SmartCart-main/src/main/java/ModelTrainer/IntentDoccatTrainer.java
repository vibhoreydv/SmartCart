package ModelTrainer;

import opennlp.tools.doccat.*;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * ============================================================
 * IntentModelTrainer
 * ============================================================
 *
 * Responsibility:
 *  - Reads labeled intent training data
 *  - Trains Apache OpenNLP Document Categorizer model
 *  - Serializes trained model to a .bin file
 *
 * This class is executed ONLY:
 *  - during development
 *  - or offline retraining
 *
 * NEVER run this at application runtime.
 */
public class IntentDoccatTrainer {

	/**
	 * Trains an OpenNLP {@link DoccatModel} for intent classification using a
	 * plain-text training file and saves the trained model to disk.
	 *
	 * <p>
	 * The training file must follow the OpenNLP Document Categorizer format: one
	 * training example per line, where each line starts with an intent label
	 * followed by the training sentence.
	 * </p>
	 *
	 * <pre>
	 * intent-name training sentence goes here
	 * </pre>
	 *
	 * <p>
	 * This method performs the following steps:
	 * </p>
	 * <ol>
	 * <li>Loads the training data from a <code>.train</code> file</li>
	 * <li>Converts each line into {@link DocumentSample} objects</li>
	 * <li>Trains a document categorization model using default feature
	 * generators</li>
	 * <li>Serializes and saves the trained model as a <code>.bin</code> file</li>
	 * </ol>
	 *
	 * <p>
	 * ⚠️ Note: This method is intended for development or offline training. It
	 * should not be executed on every application startup in production.
	 * </p>
	 *
	 * @param args command-line arguments (not used)
	 * @throws Exception if an error occurs while reading training data, training
	 *                   the model, or writing the model file
	 */
	public static void main(String[] args) throws Exception {
		// 1. Where is the training file?
		File trainingFile = new File("src/main/resources/nlp/intent-doccat.train");

		/*
		 * Read training data as plain text lines.
		 * Each line follows the format:
		 *   <intent_label> <sentence>
		 */
		InputStreamFactory dataIn = new MarkableFileInputStreamFactory(trainingFile);

		try (ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, StandardCharsets.UTF_8);
				ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream)) {

			// 2. Training parameters
			/*
			 * Training parameters:
			 *
			 * ITERATIONS:
			 *   Number of training cycles over data.
			 *   Higher = better learning but slower training.
			 *
			 * CUTOFF:
			 *   Minimum frequency for a feature to be considered.
			 *   1 = include everything (good for small datasets).
			 */
			TrainingParameters params = new TrainingParameters();
			params.put(TrainingParameters.ITERATIONS_PARAM, "100");
			params.put(TrainingParameters.CUTOFF_PARAM, "0");

			DoccatFactory factory = new DoccatFactory();

			// 3. Train the model
			/*
			 * Train the Document Categorization model.
			 *
			 * Language code ("en") does not affect logic;
			 * it is metadata for the model.
			 */
			DoccatModel model = DocumentCategorizerME.train("en", // language
					sampleStream, params, factory
			);

			// 4. Save model to file
			/*
			 * Persist trained model as a binary file.
			 * This file will be loaded at runtime.
			 */
			File modelOutFile = new File("src/main/resources/nlp/intent-doccat.bin");
			try (FileOutputStream modelOut = new FileOutputStream(modelOutFile)) {
				model.serialize(modelOut);
			}

			System.out.println("Model trained and saved to: " + modelOutFile.getAbsolutePath());
		}
	}
}
