import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class HF_EncodeDecode_Test {

	EncodeDecode enc_dec;
	HuffmanCompressionUtilities huffUtil;
	GenWeights gw;
	EncodeDecodeGUI gui; 
    private static boolean noCR = false; 
    int[] weights = new int[128];
    
	private BufferedOutputStream bOutS;
	private BufferedInputStream bInS;
	private File binInf;
	private File binOutf;
	private BinaryIO binUtils;
	private String[] bin2nib = {"0000","0001","0010","0011","0100","0101","0110","0111",
            "1000","1001","1010","1011","1100","1101","1110","1111",};
	
    private static String getOperatingSystem() {
    	String os = System.getProperty("os.name");
    	return os;
    }

    @BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		binUtils = new BinaryIO();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	@Order(1)
	void test_encode_simple_optimize() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "simple";
		
		checkWeightsFile(base);
		removeBinaryFile(base);
		enc_dec.encode(base+".txt", base+".bin",base+".csv",true);
		// recreate file handle to test existence of binary file
		binOutf = new File("output/"+base+".bin");
		assertTrue(checkBinaryOutput(binOutf, 17));
		
	}
	
	@Test
	@Order(2)
	void test_encode_simple_full() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "simple";
		
		checkWeightsFile(base);
		removeBinaryFile(base+"_full");
		enc_dec.encode(base+".txt", base+"_full.bin",base+".csv",false);
		// recreate file handle to test existence of binary file
		binOutf = new File("output/"+base+"_full.bin");
		assertTrue(checkBinaryOutput(binOutf, 17));
	}
	
	@Test
	@Order(3)
	void test_encode_GEAH_optimize() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "Green Eggs and Ham";
		
		checkWeightsFile(base);
		removeBinaryFile(base);
		enc_dec.encode(base+".txt", base+".bin",base+".csv",true);
		// recreate file handle to test existence of binary file
		binOutf = new File("output/"+base+".bin");
		assertTrue(checkBinaryOutput(binOutf, 2047));
		
	}
	
	@Test
	@Order(4)
	void test_encode_TCITH_optimize() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "The Cat in the Hat";
		
		checkWeightsFile(base);
		removeBinaryFile(base);
		enc_dec.encode(base+".txt", base+".bin",base+".csv",true);
		// recreate file handle to test existence of binary file
		binOutf = new File("output/"+base+".bin");
		assertTrue(checkBinaryOutput(binOutf, 4297));
		
	}
	
	@Test
	@Order(5)
	void test_encode_HPATS_optimize() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "Harry Potter and the Sorcerer";
		
		checkWeightsFile(base);
		removeBinaryFile(base);
		enc_dec.encode(base+".txt", base+".bin",base+".csv",true);
		// recreate file handle to test existence of binary file
		binOutf = new File("output/"+base+".bin");
		assertTrue(checkBinaryOutput(binOutf, 259670));
		
	}
	
	@Test
	@Order(6)
	void test_encode_WAP_optimize() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "warAndPeace";
		
		checkWeightsFile(base);
		removeBinaryFile(base);
		enc_dec.encode(base+".txt", base+".bin",base+".csv",true);
		// recreate file handle to test existence of binary file
		binOutf = new File("output/"+base+".bin");
		assertTrue(checkBinaryOutput(binOutf, 1875089));
		
	}
	
	@Test
	@Order(7)
	void test_encode_WAP_full() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "warAndPeace";
		
		checkWeightsFile(base);
		removeBinaryFile(base+"_full");
		enc_dec.encode(base+".txt", base+"_full.bin",base+".csv",false);
		// recreate file handle to test existence of binary file
		binOutf = new File("output/"+base+"_full.bin");
		assertTrue(checkBinaryOutput(binOutf, 1875089));
		
	}
	
	@Test
	@Order(10)
	void test_decode_simple_optimize() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "simple";
		
		checkWeightsFile(base);
		checkBinaryFile(base);
		removeOutputTextFile(base);
		enc_dec.decode(base+".bin", base+".txt",base+".csv",true);
		// recreate file handle to test existence of binary file
		File outFh = new File("output/"+base+".txt");
		assertTrue(outFh.exists());
		File origFh = new File("data/"+base+".txt");
		assertTrue(compareFiles(origFh, outFh));	
		outFh = new File("output/"+base+".txt");
		removeOutputTextFile(base);
		//removeBinaryFile(base);
	}

	@Test
	@Order(11)
	void test_decode_simple_full() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "simple";
		
		checkWeightsFile(base);
		checkBinaryFile(base+"_full");
		removeOutputTextFile(base+"_full");
		enc_dec.decode(base+"_full.bin", base+"_full.txt",base+".csv",false);
		// recreate file handle to test existence of binary file
		File outFh = new File("output/"+base+"_full.txt");
		assertTrue(outFh.exists());
		File origFh = new File("data/"+base+".txt");
		assertTrue(compareFiles(origFh, outFh));	
		removeOutputTextFile(base+"_full");
//		removeBinaryFile(base+"_full");
	}
	
	@Test
	@Order(12)
	void test_decode_GEAH_optimize() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "Green Eggs and Ham";
		
		checkWeightsFile(base);
		checkBinaryFile(base);
		removeOutputTextFile(base);
		enc_dec.decode(base+".bin", base+".txt",base+".csv",true);
		// recreate file handle to test existence of binary file
		File outFh = new File("output/"+base+".txt");
		assertTrue(outFh.exists());
		File origFh = new File("data/"+base+".txt");
		assertTrue(compareFiles(origFh, outFh));	
		removeOutputTextFile(base);
//		removeBinaryFile(base);
	}
	
	@Test
	@Order(13)
	void test_decode_TCITH_optimize() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "The Cat in the Hat";
		
		checkWeightsFile(base);
		checkBinaryFile(base);
		removeOutputTextFile(base);
		enc_dec.decode(base+".bin", base+".txt",base+".csv",true);
		// recreate file handle to test existence of binary file
		File outFh = new File("output/"+base+".txt");
		assertTrue(outFh.exists());
		File origFh = new File("data/"+base+".txt");
		assertTrue(compareFiles(origFh, outFh));	
		removeOutputTextFile(base);
//		removeBinaryFile(base);
	}
	
	@Test
	@Order(14)
	void test_decode_HPATS_optimize() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "Harry Potter and the Sorcerer";
		
		checkWeightsFile(base);
		checkBinaryFile(base);
		removeOutputTextFile(base);
		enc_dec.decode(base+".bin", base+".txt",base+".csv",true);
		// recreate file handle to test existence of binary file
		File outFh = new File("output/"+base+".txt");
		assertTrue(outFh.exists());
		File origFh = new File("data/"+base+".txt");
		assertTrue(compareFiles(origFh, outFh));	
		removeOutputTextFile(base);
//		removeBinaryFile(base);
	}
	
	@Test
	@Order(15)
	void test_decode_WAP_optimize() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "warAndPeace";
		
		checkWeightsFile(base);
		checkBinaryFile(base);
		removeOutputTextFile(base);
		enc_dec.decode(base+".bin", base+".txt",base+".csv",true);
		// recreate file handle to test existence of binary file
		File outFh = new File("output/"+base+".txt");
		assertTrue(outFh.exists());
		File origFh = new File("data/"+base+".txt");
		assertTrue(compareFiles(origFh, outFh));	
		removeOutputTextFile(base);
//		removeBinaryFile(base);
	}
	
	@Test
	@Order(16)
	void test_decode_WAP_full() {
		gui = new EncodeDecodeGUI();
		enc_dec = new EncodeDecode(gui);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String base = "warAndPeace";
		
		checkWeightsFile(base);
		checkBinaryFile(base+"_full");
		removeOutputTextFile(base+"_full");
		enc_dec.decode(base+"_full.bin", base+"_full.txt",base+".csv",false);
		// recreate file handle to test existence of binary file
		File outFh = new File("output/"+base+"_full.txt");
		assertTrue(outFh.exists());
		File origFh = new File("data/"+base+".txt");
		assertTrue(compareFiles(origFh, outFh));	
		removeOutputTextFile(base+"_full");
//		removeBinaryFile(base+"_full");
	}

	private boolean checkBinaryOutput(File binOutf, int expectedSize) {
		if (!binOutf.exists()) {
			System.out.println("   Encode did not create file "+binOutf.getPath());
			return false;
		}
		else if (binOutf.length() == 0) {
			System.out.println("   Encode created empty file "+binOutf.getPath());
			return false;			
		} else if ((binOutf.length() < (0.9*expectedSize)) || (binOutf.length() > (1.1*expectedSize))) {
			System.out.println("   Encode created file "+binOutf.getPath()+", but file size is not within 10% of expectations");
			return false;			
		}
		return true;
	}
	
	private boolean compareFiles(File fh1, File fh2) {
		BufferedInputStream inFh1 = null;
		BufferedInputStream inFh2 = null;
		int byte_cnt = 0;
		System.out.println("Comparing files "+fh1.getPath()+" and "+fh2.getPath());
	
		try {
			inFh1 = new BufferedInputStream(new FileInputStream(fh1));
			inFh2 = new BufferedInputStream(new FileInputStream(fh2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		boolean EOF = false;
		boolean match = true;
		
		while (!EOF) {
			int byte_fh1 = readByteFromInStream(inFh1);
			int byte_fh2 = readByteFromInStream(inFh2);
			byte_cnt++;
			EOF = byte_fh1 == -1;		
			match = byte_fh1 == byte_fh2;
			if (EOF && !match) {
				if (byte_fh2 == 13)   // is there a CR? if so, must be followed by a \n
				   byte_fh2 = readByteFromInStream(inFh2);
				if (byte_fh2 == 10)   // is there a \n?
					byte_fh2 = readByteFromInStream(inFh2); // should be EOF now
				match = byte_fh1 == byte_fh2;
			}
			if (!match) { 
				System.out.println("Mismatch detected between file "+fh1.getPath()+" and file "+fh2.getPath()+" at byte "+byte_cnt);
			    System.out.println("   Expected byte value = "+byte_fh1+"("+((char) byte_fh1)+")");
			    System.out.println("   Actual byte value   = "+byte_fh2+"("+((char) byte_fh2)+")");
			    closeInStream(inFh1);
			    closeInStream(inFh2);
				return false;
			}

		}	
	    closeInStream(inFh1);
	    closeInStream(inFh2);
		
		return true;
	}

	private void closeInStream(BufferedInputStream inS) {
		try {
			inS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private boolean checkWeightsFile(String base) {
		File wtsFh = new File("output/"+base+".csv");
		System.out.println("Generating weights file: "+ base+".csv");

		System.out.println("Checking if file exists: "+wtsFh.getPath());
		if (wtsFh.exists()) 
			wtsFh.delete();
		
		weights = gw.readInputFileAndReturnWeights("data/" + base+".txt");
		writeWeightsFile(wtsFh);
		return  (wtsFh.exists()); 
	}	
	
	private void writeWeightsFile(File outf) {
		String line;
		try {
			DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outf)));
			for (int i = 0; i < weights.length; i++ ) {
					line = i+","+weights[i]+",\n";
				output.writeBytes(line);
			}
			output.flush();
			output.close();
		} catch (IOException e) {
			System.err.println("Error in writing file: "+outf.getName());
			e.printStackTrace();
		}
	}
	
	private boolean removeBinaryFile(String base) {
		binOutf = new File("output/"+base+".bin");

		System.out.println("Checking if file exists: "+binOutf.getPath());
		if (binOutf.exists()) {
			if (binOutf.delete()) {
				System.out.println("Deleted file: "+binOutf.getPath());
				return true;
			} else {
				return false;
			}
		}
		return true;
	}	
	
	private boolean removeOutputTextFile(String base) {
		binOutf = new File("output/"+base+".txt");

		System.out.println("Checking if file exists: "+binOutf.getPath());
		if (binOutf.exists()) {
			if (binOutf.delete()) {
				System.out.println("Deleted file: "+binOutf.getPath());
				return true;
			} else {
				return false;
			}
		}
		return true;
	}	
	
	private boolean checkBinaryFile(String base) {
		binInf = new File("output/"+base+".bin");

		System.out.println("Checking if file exists: "+binInf.getPath());
		return binInf.exists();
	}	
	

	private String generateBitString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			int nhi = ((bytes[i]>>>4)&0x0f);
			int nlo = ((bytes[i])&0x0f);
			sb.append(bin2nib[nhi]);
			sb.append(bin2nib[nlo]);
		}
		System.out.println("Generated Binary String: "+sb.toString());
		return sb.toString();

	}
	
	private byte readByteFromInStream(BufferedInputStream bInS) {
		byte readByte=0;
		try {
			 readByte = (byte) bInS.read();
		} catch (IOException e) {
			System.out.println("Caught Exception while trying to read a byte");
			e.printStackTrace();
		}
		return readByte;
	}
	
	private void convStrToBin(String bits) {
		System.out.println("   ConvStrToBin bits="+bits);
		try {
			binUtils.convStrToBin(bits);
		} catch (IOException e) {
			System.out.println("Exception while trying to write bits to file");
	        e.printStackTrace();
		}
		
	}

	private void closeOutStream(BufferedOutputStream bOutS) {
		try {
			bOutS.flush();
			bOutS.close();
		} catch (IOException e) {
			System.out.println("Exception while trying to close binary output stream");
		}		
	}
	
	private void openFile(File fh, boolean input) {
		try {
			if (input)
				binUtils.openInputFile(fh);
			else
				binUtils.openOutputFile(fh);
		} catch (IOException e) {
			System.out.println("Exception wil trying to open file "+fh.getName()+" for "+ ((input)?"read.":"write."));
			e.printStackTrace();
		}
	}
	
}
