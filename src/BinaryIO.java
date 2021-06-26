import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Class BinaryIO.
 */
public class BinaryIO {

	/** The bin str. */
	private String binStr;

	/** The bin output. */
	private BufferedOutputStream binOutput;

	/** The bin input. */
	private BufferedInputStream binInput;

	/**
	 * Instantiates a new binary IO.
	 */
	public BinaryIO() {
		binStr = "";
	}

	/**
	 * Gets the binary String.
	 *
	 * @return the binStr
	 */
	public String getBinStr() {
		return binStr;
	}

	/**
	 * Gets the binary output Stream - for testing purposes ONLY.
	 *
	 * @return the binOutput
	 */
	BufferedOutputStream getBinOutput() {
		return binOutput;
	}

	/**
	 * Gets the binary input stream - for testing purposes ONLY.
	 *
	 * @return the binInput
	 */
	BufferedInputStream getBinInput() {
		return binInput;
	}

	/**
	 * Converts a string of 1's and 0's to one or more bytes. 
	 * 1) Appends the string encoding of the current character to the current string. 
	 * 2) while the string length is >= 8
	 *    convert the first 8 characters to the equivalent byte value
	 *    write the byte to a file
	 *    remove the first 8 characters
	 *    repeat as necessary
	 *
	 * @param inBinStr the incoming binary string for the current character
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void convStrToBin(String inBinStr) throws IOException {
		binStr += inBinStr;

		while (binStr.length() >= 8) {
			binOutput.write(convertStrIntoByte(binStr));
			binStr = binStr.substring(8);
		}
	}

	/**
	 * Convert a byte value into a string of 1's and 0's (MSB to LSB).
	 *
	 * @param aByte the a byte
	 * @return the string
	 */
	String convBinToStr(byte aByte) {
		String str = "";
		int mask = 0x80;
		for (int i = 0; i < 8; i++) {
			str += ((mask & aByte)>0)?"1":"0";
			mask = mask >>> 1;
		}
		return str;
	}

	/**
	 * Write EOF. 
	 * 1) Append the EOF character to the stream of 1's and 0;s
	 * 2) While the (length of the stream)%8 != 0, append a "0"
	 * 3) Convert the string to 1 or more bytes (until consumed)
	 * 4) flush and close the file;
	 * 5) make sure to clear binStr (in case converting another file later)
	 *
	 * @param EOF_binStr the EO F bin str
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void writeEOF(String EOF_binStr) throws IOException {
		binStr += EOF_binStr;

		while (binStr.length() % 8 != 0) {
			binStr += "0";
		}

		while (binStr.length() > 0) {
			binOutput.write(convertStrIntoByte(binStr));
			binStr = binStr.substring(8);
		}

		binOutput.flush();
		binOutput.close();

		binStr = "";
	}

	/**
	 * Open binary output file.
	 *
	 * @param binFile the bin file
	 * @return the buffered output stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	BufferedOutputStream openOutputFile(File binFile) throws IOException {
		binOutput = new BufferedOutputStream(new FileOutputStream(binFile));
		return binOutput;
	}

	/**
	 * Open binary input file.
	 *
	 * @param binFile the bin file
	 * @return the buffered input stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	BufferedInputStream openInputFile(File binFile) throws IOException {
		binInput = new BufferedInputStream(new FileInputStream(binFile));
		return binInput;
	}

	/**
	 * Convert str into byte.
	 *
	 * @return the byte
	 */
	public byte convertStrIntoByte(String inputString) {
		byte dec = 0;

		for (int i = 0; i < 8; i++) {
			dec *= 2;
			if (inputString.charAt(i) == '1') {
				dec++;
			}
		}
		
		return dec;
	}
}

