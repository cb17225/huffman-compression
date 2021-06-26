import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class HF_BinaryIO_OutputTest {

	private BufferedOutputStream bOutS;
	private BufferedInputStream bInS;
	private File binInf;
	private File binOutf;
	private BinaryIO binUtils;
	private String[] bin2nib = {"0000","0001","0010","0011","0100","0101","0110","0111",
            "1000","1001","1010","1011","1100","1101","1110","1111",};
	
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
	void test_bstrNotByte() {
		System.out.println("Test 1: Checking that convStrToBin does not write < 8 bits to a file");
		// create new binary output file
		binOutf = new File("output/test_bstrNotByte.out");
		// create a test string of 7 bits
		String bits = "0101100";
	
		openFile(binOutf,false);
		
		// check that binStr is ""
		assertTrue("".equals(binUtils.getBinStr()));
		// add all bits, one at a time
		for (int i = 0; i < bits.length(); i++) {
			convStrToBin(bits.substring(i,i+1));
			assertTrue(bits.substring(0,i+1).equals(binUtils.getBinStr()));
		}
		bOutS = binUtils.getBinOutput();
		closeOutStream(bOutS);
		assertTrue(binOutf.length() == 0);
		binOutf.delete();
		
	}
	
	@Test
	@Order(2)
	void test_bstrWriteOneByte() {
		System.out.println("Test 2: Checking that convStrToBin can write individual bytes to the file");
		byte[] b = new byte[4];
		// test array of 4 bytes
		b[0] = 0x60;
		b[1] = 0x0d;
		b[2] = (byte) 0xac;
		b[3] = (byte) 0xed;
		
		// create new binary output file
		File fh = new File("output/test_bstrOneByte.out");
		openFile(fh,false);
		
		// check that binStr is ""
		assertTrue("".equals(binUtils.getBinStr()));
		
		for (int bCnt = 0; bCnt < b.length; bCnt++ ) {
			int bin1 = (b[bCnt] >>> 4)&0xf;
			int bin2 = b[bCnt] & 0xf;
			convStrToBin(bin2nib[bin1] +bin2nib[bin2]);
			assertTrue("".equals(binUtils.getBinStr()));
		}
		
		bOutS = binUtils.getBinOutput();
		closeOutStream(bOutS);
		assertTrue(fh.length() == 4);
		
		openFile(fh,true);
		bInS = binUtils.getBinInput();
		for (int bCnt = 0; bCnt < b.length; bCnt++) {
			assertTrue(b[bCnt] == readByteFromInStream(bInS));
			System.out.println("   Byte "+bCnt+" matched expected value of "+(b[bCnt] & 0x0ff));
		}
		fh.delete();
	}
	
	@Test
	@Order(3)
	void test_bstrWriteTwoBytes() {
		System.out.println("Test 3: Checking that convStrToBin can write multiple bytes to the file");
		byte[] b = new byte[4];
		// test array of 4 bytes
		b[0] = 0x60;
		b[1] = 0x0d;
		b[2] = (byte) 0xac;
		b[3] = (byte) 0xed;
		
		// create new binary output file
		File fh = new File("output/test_bstrTwoByte.out");
		openFile(fh,false);
		
		// check that binStr is ""
		assertTrue("".equals(binUtils.getBinStr()));
		
		for (int bCnt = 0; bCnt < b.length; bCnt+=2 ) {
			int bin1 = (b[bCnt] >>> 4)&0xf;
			int bin2 = b[bCnt] & 0xf;
			int bin3 = (b[bCnt+1] >>> 4)&0xf;
			int bin4 = b[bCnt+1] & 0xf;
			convStrToBin(bin2nib[bin1] +bin2nib[bin2]+bin2nib[bin3] +bin2nib[bin4]);
			assertTrue("".equals(binUtils.getBinStr()));
		}
		
		bOutS = binUtils.getBinOutput();
		closeOutStream(bOutS);
		assertTrue(fh.length() == 4);
		
		openFile(fh,true);
		bInS = binUtils.getBinInput();
		for (int bCnt = 0; bCnt < b.length; bCnt++) {
			assertTrue(b[bCnt] == readByteFromInStream(bInS));
			System.out.println("   Byte "+bCnt+" matched expected value of "+(b[bCnt] & 0x0ff));
		}
		fh.delete();
	}

	@Test
	@Order(4)
	void test_bstrWriteOneByteWithRemainder() {
		System.out.println("Test 4: Checking that convStrToBin can write a single byte but perserve the remainder to the file");
		byte[] b = new byte[4];
		b[0] = 0X58;
		b[1] = 0x59;
		b[2] = 0x5a;
		b[3] = 0x5b;
		
		String bits = generateBitString(b);
		// create new binary output file
		File fh = new File("output/test_bstrOneByteRemainder.out");
		openFile(fh,false);
		
		// check that binStr is ""
		assertTrue("".equals(binUtils.getBinStr()));
		convStrToBin(bits.substring(0,6));  // should not write anything
		System.out.println("BinStr = "+binUtils.getBinStr());
		assertTrue(bits.substring(0,6).equals(binUtils.getBinStr()));    //binStr should match what was sent in!
		convStrToBin(bits.substring(6,11));  // should write one byte
		System.out.println("BinStr = "+binUtils.getBinStr());
		assertTrue(bits.substring(8,11).equals(binUtils.getBinStr()));  
		convStrToBin(bits.substring(11,26));    // should write two bytes
		System.out.println("BinStr = "+binUtils.getBinStr());
		assertTrue(bits.substring(24,26).equals(binUtils.getBinStr()));
		convStrToBin(bits.substring(26,32));    // should write one byte, no remainder
		System.out.println("BinStr = "+binUtils.getBinStr());
		assertTrue("".equals(binUtils.getBinStr()));		
		
		bOutS = binUtils.getBinOutput();
		closeOutStream(bOutS);
		assertTrue(fh.length() == 4);
		
		openFile(fh,true);
		bInS = binUtils.getBinInput();
		for (int bCnt = 0; bCnt < b.length; bCnt++) {
			assertTrue(b[bCnt] == readByteFromInStream(bInS));
			System.out.println("   Byte "+bCnt+" matched expected value of "+(b[bCnt] & 0x0ff));
		}
		fh.delete();
	}
	
	@Test
	@Order(5)
	void test_random1024Bytes() {
		System.out.println("Test 5: Checking 1024 randomly generated bytes for randomly generated accesses");
		byte[] b = random1KBytes();
		String bits = generateBitString(b);
		// create new binary output file
		File fh = new File("output/test_randBytes.out");
		openFile(fh,false);
		
		Random rnd = new Random();
		// check that binStr is ""
		assertTrue("".equals(binUtils.getBinStr()));
		
		int base = 0; 
		int rndLen = rnd.nextInt(16)+3;  // append random # of bits between 3-18
		int bitsLen = bits.length();
		int i = 0;
		while ((base+rndLen) < bitsLen) {
			i++;
			System.out.println("   Conversion #"+i+":  "+rndLen+" bits: "+base+" to "+(base+rndLen)+ ".  bit string="+bits.substring(base,(base+rndLen)));
			convStrToBin(bits.substring(base,base+rndLen));
			int byteBase = (base+rndLen) - ((base+rndLen)%8);
			System.out.println("   BinStr = "+binUtils.getBinStr());
			if (byteBase != (base+rndLen))  // there should be a remainder
				assertTrue(bits.substring(byteBase,(base+rndLen)).equals(binUtils.getBinStr()));    //binStr should match what was sent in!
			else   // no remainder - binStr should be ""
				assertTrue("".equals(binUtils.getBinStr()));					
			base = base+rndLen;
			rndLen = rnd.nextInt(16)+3;  // append random # of bits between 3-18
		}

		if (base != bitsLen) 
			convStrToBin(bits.substring(base));    // should write final byte, no remainder
		System.out.println("BinStr = "+binUtils.getBinStr());
		assertTrue("".equals(binUtils.getBinStr()));		
		
		bOutS = binUtils.getBinOutput();
		closeOutStream(bOutS);
		assertTrue(fh.length() == 1024);
		
		openFile(fh,true);
		bInS = binUtils.getBinInput();
		for (int bCnt = 0; bCnt < b.length; bCnt++) {
			assertTrue(b[bCnt] == readByteFromInStream(bInS));
			System.out.println("   Byte "+bCnt+" matched expected value of "+(b[bCnt] & 0x0ff));
		}
		fh.delete();
	}
	
	@Test
	@Order(6)
	void test_writeEOFPadding() {
		System.out.println("Test 6: Checking Write EOF and padding");
		byte[] b = new byte[2];
		String frag = "";
		String EOF = "10110";
		int data = 0x0B000;
		
		File fh = new File("output/test_writeEOFandPadding.out");
		
		for (int i = 0; i < 8; i++ ) {
			b[0] = (byte) ((data >>>8) &0xff);
			b[1] = (byte) (data & 0xff);
			System.out.println("Checking writeEOF("+EOF+"): Initial Fragment=["+frag+"]   1st Byte="+  ((int) (b[0] & 0x0ff))+((b[1] != 0)?"   2nd Byte ="+((int) (b[1]&0x0ff))+".":"."));
			openFile(fh,false);
			// check that binStr is ""
			assertTrue("".equals(binUtils.getBinStr()));
			if (!("".equals(frag))) {
				convStrToBin(frag);
				assertTrue(frag.equals(binUtils.getBinStr()));
			}
			try {
				binUtils.writeEOF(EOF);	
			} catch (IOException e) {
				System.out.println("Caught Exception while writing the EOF");
				e.printStackTrace();
			}
			assertTrue("".equals(binUtils.getBinStr()));
			openFile(fh,true);
			bInS = binUtils.getBinInput();
			int rd0 = readByteFromInStream(bInS);
			assertTrue(b[0] == rd0);
			System.out.println("   Byte 0 matched expected value of "+(b[0] & 0x0ff));
			if (b[1] != 0)  {
				assertTrue(b[1] == readByteFromInStream(bInS));
				System.out.println("   Byte 1 matched expected value of "+(b[1] & 0x0ff));
			}	
			fh.delete();
			frag = frag.concat("1");
			data = ((data >>> 1)&0x0FFFF)|0x8000;
		}
	}

	@Test
	@Order(7)
	void test_convBinToStr() {
		System.out.println("Test 7: Checking Checking Binary to Strings");
				
		for (int i = 0; i < 256; i++ ) {
			int nhi = (i >> 4) & 0x0F;
			int nlo = (i & 0xF);
			String expect = bin2nib[nhi]+bin2nib[nlo];
			System.out.println("Checking Conversion of "+i+" to binary String "+expect+".");
			String convStr = binUtils.convBinToStr((byte) ( i & 0xff));
			assertTrue(expect.equals(convStr));
		}
	}

	private byte[] random1KBytes( ) {
		byte[] rndBytes = new byte[1024];
		Random random = new Random();
		for (int i = 0; i < rndBytes.length; i++ ) {
			rndBytes[i] = (byte) (random.nextInt(256));
			System.out.println("    Generated byte "+i+" value = "+rndBytes[i]);
		}
		return rndBytes;
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
