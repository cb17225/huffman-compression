import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class GenWeights. This class provides a GUI front-end for the user to 
 * specify a file for generating a character-based frequency analysis, and a 
 * file to write the results for later consumption. This is self-contained class
 * in that it does not adhere to the MVC programming model; all work is done inside
 * this class
 */
public class GenWeights extends Application {

	/** The pane. */
	private BorderPane pane = new BorderPane();

	/** The gp. */
	private GridPane gp = new GridPane();

	/** The btn box. */
	private HBox btnBox = new HBox(15);

	/**  The file handles for input (inf) and output (outf). */
	private File inf,outf;

	/** The weights - the # of occurences of each character with index 0-127. */
	private int[] weights = new int[128]; 

	/** The boolean indicating if the weights array contains valid information. */
	private boolean weightsValid = false;

	/**
	 * Instantiates a new GenWeights object.
	 */
	public GenWeights() {
		initWeights();
	}

	/**
	 * Inits the weights array to 0; should be called in the constructor and
	 * as the first step when the Generate button is pressed.
	 */
	private void initWeights() {
		for (int i = 0; i < weights.length; i++) {
			weights[i] = 0;
		}
		weightsValid = false;
	}

	/**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 * @throws Exception the exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Label hdr = new Label("Generate Frequency Weights");
		hdr.setPrefHeight(40);
		Label lblInput = new Label("Input File: ");
		Label lblOutput = new Label("Output File: ");
		TextField inputFile = new TextField();
		inputFile.setPrefWidth(300);
		TextField outputFile = new TextField();
		outputFile.setPrefWidth(300);
		gp.setVgap(15);
		gp.setHgap(2);

		gp.add(lblInput, 0, 0);
		gp.add(inputFile, 1, 0);
		gp.add(lblOutput, 0, 1);
		gp.add(outputFile, 1, 1);
		pane.setTop(hdr);
		pane.setCenter(gp);
		Button genWt = new Button("Generate");
		Button save = new Button("Save To File");

		// TODO #1 create the setOnAction() events to handle button pushes.
		genWt.setOnAction(e -> generateWeights(inputFile.getText()));
		save.setOnAction(e -> saveWeightsToFile(outputFile.getText()));

		btnBox.setAlignment(Pos.CENTER);
		btnBox.getChildren().addAll(genWt,save);
		pane.setBottom(btnBox);

		Scene scene = new Scene(pane, 400,150);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * Generate character-based frequency weights. You will write this method,
	 * which must address the following cases:
	 * a) if the filename is empty, raise a WARNING alert with an appropriate message and
	 *    return.
	 * b) if the file does not exist or is empty, raise a WARNING alert with an appropriate 
	 *    message and return.
	 * c) if the file exists and is not empty, but is not readable, raise a WARNING alert with 
	 *    an appropriate message and return.
	 * 
	 * Each of those cases should be differentiated to the user
	 * 
	 * Assuming that the requirements of a, b and c have all been met successfully, 
	 * initialize the weights and read the input file character by character to update 
	 * the weights.. review the notes in Student Materials from today's class if you 
	 * have questions. Note that it might make your code cleaner if the actual reading of
	 * the file was done in a helper method. 
	 * 
	 * Once the input file has been fully processed, you should print the weights to the console.
	 *
	 * @param infName - the name of the file to read; should come from the appropriate
	 *                  text field
	 *              
	 */
	private void generateWeights(String infName) {
		// TODO #2: write this method and any helper methods

		File file = new File("/Users/cloudburst__/git/huffmancompression-aden-mvolkov-mvla/data/" + infName);

		if (checkForIssuesWithInputFile(infName, file)) return;

		weights = new int[128];
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while (br.ready()) {
				int charCode = br.read();

				if (charCode < 128) weights[charCode]++;
			}
		} catch (IOException e) {
			System.err.println("Error in reading file: "+infName);
			e.printStackTrace();
		}

		printWeights();

	}


	/**
	 * Prints the weights to the console. Non-printing characters (0-31, 127) 
	 * are indicated with [ ], printing characters are displayed to help with debug
	 * 
	 */
	private void printWeights() {
		for (int i = 0; i < weights.length; i++) {
			if ((i < 32) || (i == 127))  
				System.out.println("i:"+i+" [ ] = "+weights[i]);
			else 
				System.out.println("i:"+i+" ("+(char)i+") = "+weights[i]);
		}
	}

	/**
	 * Write the character-based frequency data to the specified file, one index per line.
	 * Use the following format:
	 *   print the index and the frequency count separated by a comma
	 *   
	 * Assuming that there are no errors, raise an INFORMATION alert with an appropriate message
	 * to indicate to the user that the output file was created successfully. Make sure to refer to 
	 * the notes from class today for any more details. Again, the actual writing of the file might be
	 * best done in a separate helper method.
	 *   
	 * Error Handling:
	 *   if outfName is blank, raise a WARNING alert with an appropriate message and return.
	 *   if the output file exists but is not writeable, raise a WARNING alear with an appropriate message
	 *   and return.
	 *   if the output file exists and is writeable, raise a CONFIRMATION alert with an appropriate
	 *   message to the user. if they cancel the operation, return; otherwise, continue.
	 *   
	 * @param outfName the outf name
	 */
	private void saveWeightsToFile(String outfName) {
		// TODO #3: write this method (and any helper methods)

		File file = new File("/Users/cloudburst__/git/huffmancompression-aden-mvolkov-mvla/output/" + outfName);

		if (checkForIssuesWithOutputFile(outfName, file) || raiseConfirmationAlert()) return;
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(file));
			for (int i = 0; i < weights.length; i++) {
				pw.println("" + i + "," + weights[i] + ",");
			}

			pw.flush();
			pw.close();

		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		raiseInformationAlert();
	}

	//TODO #4: I strongly recommend writing reuseable alerts for input errors, output
	//         errors, confirmation and information... You can supply the specific error
	//         message as a string that is passed in. Write these methods here....

	/**
	 * Check for issues with input file.
	 *
	 * @param name the name
	 * @param f the f
	 * @return true, if successful
	 */
	private boolean checkForIssuesWithInputFile(String name, File f) {
		if (name.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("File Name is Empty!");
			alert.setContentText("Please make sure to enter a file name before generating weights.");
			alert.showAndWait();
			return true;
		} else if (!f.isFile() || f.length() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("File is Empty or Does Not Exist!");
			alert.setContentText("Please make sure to enter a valid file name, and that the said file is not empty.");
			alert.showAndWait();
			return true;
		} else if (f.isFile() && !f.canRead()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Cannot Read File!");
			alert.setContentText("Please make sure to check file permissions before attempting to access said file.");
			alert.showAndWait();
			return true;
		}

		return false;
	}

	/**
	 * Check for issues with output file.
	 *
	 * @param name the name
	 * @param f the f
	 * @return true, if successful
	 */
	private boolean checkForIssuesWithOutputFile(String name, File f) {
		if (name.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("File Name is Empty!");
			alert.setContentText("Please make sure to enter a file name to write the weights to.");
			alert.showAndWait();
			return true;
		} else if (f.isFile() && !f.canWrite()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("File is Not Writeable!");
			alert.setContentText("Please make sure to check file permissions before attempting to create said file.");
			alert.showAndWait();
			return true;
		}

		return false;
	}

	/**
	 * Raise information alert.
	 */
	private void raiseInformationAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Operation Successful");
		alert.setContentText("The file was successfully created.");
		alert.showAndWait();
	}

	/**
	 * Raise confirmation alert.
	 *
	 * @return true, if successful
	 */
	private boolean raiseConfirmationAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Confirmation of Action");
		alert.setContentText("Do You Wish to Proceed?");

		Optional<ButtonType> result = alert.showAndWait();

		return (result.get() != ButtonType.OK);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

}
