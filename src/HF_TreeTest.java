import static org.junit.jupiter.api.Assertions.assertTrue;

import org.aden.hf_lib.HuffCompTestLib;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class HF_TreeTest {
	HuffCompTestLib hflib = new HuffCompTestLib();
	
	EncodeDecode enc_dec;
	HuffmanCompressionUtilities huffUtil;
	GenWeights gw;
    private static boolean noCR = false; 
    
    private static String getOperatingSystem() {
    	String os = System.getProperty("os.name");
    	return os;
    }
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Running on: "+getOperatingSystem());
		noCR = !(getOperatingSystem().contains("Win"));
		if (noCR) {
			System.out.println("Non-Windows platform detected; setting noCR="+noCR);			
		} else {
			System.out.println("Windows platform detected; setting noCR="+noCR);			
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@Order(1)
	void test_tree_simple_optimize() {
		enc_dec = new EncodeDecode(null);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String fname = "simple.txt";

		int[] weights = gw.readInputFileAndReturnWeights("data/"+fname);
		noCR = (weights[13] == 0);
		
		huffUtil.setWeights(weights);
		huffUtil.buildHuffmanTree(true);
		huffUtil.createHuffmanCodes(huffUtil.getTreeRoot(), "", 0);
		huffUtil.printHuffmanTree(huffUtil.getTreeRoot(),0);
		assertTrue(hflib.checkHuffmanTree(fname, noCR, true, huffUtil.toString()));
	}
	
	@Test
	@Order(2)
	void test_tree_simple_full() {
		enc_dec = new EncodeDecode(null);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String fname = "simple.txt";

		int[] weights = gw.readInputFileAndReturnWeights("data/"+fname);
		noCR = (weights[13] == 0);
		huffUtil.setWeights(weights);
		huffUtil.buildHuffmanTree(false);
		huffUtil.createHuffmanCodes(huffUtil.getTreeRoot(), "", 0);
		huffUtil.printHuffmanTree(huffUtil.getTreeRoot(),0);
		assertTrue(hflib.checkHuffmanTree(fname, noCR, false, huffUtil.toString()));
	}
	
	@Test
	@Order(3)
	void test_tree_GEAH_optimize() {
		enc_dec = new EncodeDecode(null);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String fname = "Green Eggs and Ham.txt";
		
		int[] weights = gw.readInputFileAndReturnWeights("data/"+fname);
		noCR = (weights[13] == 0);
		huffUtil.setWeights(weights);
		huffUtil.buildHuffmanTree(true);
		huffUtil.createHuffmanCodes(huffUtil.getTreeRoot(), "", 0);
		huffUtil.printHuffmanTree(huffUtil.getTreeRoot(),0);
		assertTrue(hflib.checkHuffmanTree(fname, noCR, true, huffUtil.toString()));
	}

	@Test
	@Order(4)
	void test_encodeMap_simple_optimize() {
		enc_dec = new EncodeDecode(null);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String fname = "simple.txt";

		int[] weights = gw.readInputFileAndReturnWeights("data/"+fname);
		noCR = (weights[13] == 0);
		huffUtil.setWeights(weights);
		huffUtil.buildHuffmanTree(true);
		huffUtil.createHuffmanCodes(huffUtil.getTreeRoot(), "", 0);
		assertTrue(hflib.checkEncodeMap(fname, noCR, true, huffUtil.getEncodeMap()));
	}
	
	@Test
	@Order(5)
	void test_encodeMap_simple_full() {
		enc_dec = new EncodeDecode(null);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String fname = "simple.txt";

		int[] weights = gw.readInputFileAndReturnWeights("data/"+fname);
		noCR = (weights[13] == 0);
		huffUtil.setWeights(weights);
		huffUtil.buildHuffmanTree(false);
		huffUtil.createHuffmanCodes(huffUtil.getTreeRoot(), "", 0);
		assertTrue(hflib.checkEncodeMap(fname, noCR, false, huffUtil.getEncodeMap()));
	}
	
	@Test
	@Order(6)
	void test_encodeMap_GEAH_optimize() {
		enc_dec = new EncodeDecode(null);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String fname = "Green Eggs and Ham.txt";
		
		int[] weights = gw.readInputFileAndReturnWeights("data/"+fname);
		noCR = (weights[13] == 0);
		huffUtil.setWeights(weights);
		huffUtil.buildHuffmanTree(true);
		huffUtil.createHuffmanCodes(huffUtil.getTreeRoot(), "", 0);
		assertTrue(hflib.checkEncodeMap(fname, noCR, true, huffUtil.getEncodeMap()));
	}
	
	@Test
	@Order(7)
	void test_encodeMap_TCITH_optimize() {
		enc_dec = new EncodeDecode(null);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String fname = "The Cat in the Hat.txt";
		
		int[] weights = gw.readInputFileAndReturnWeights("data/"+fname);
		noCR = (weights[13] == 0);
		huffUtil.setWeights(weights);
		huffUtil.buildHuffmanTree(true);
		huffUtil.createHuffmanCodes(huffUtil.getTreeRoot(), "", 0);
		assertTrue(hflib.checkEncodeMap(fname, noCR, true, huffUtil.getEncodeMap()));
	}
	
	@Test
	@Order(8)
	void test_encodeMap_HPATS_optimize() {
		enc_dec = new EncodeDecode(null);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String fname = "Harry Potter and the Sorcerer.txt";
		
		int[] weights = gw.readInputFileAndReturnWeights("data/"+fname);
		noCR = (weights[13] == 0);
		huffUtil.setWeights(weights);
		huffUtil.buildHuffmanTree(true);
		huffUtil.createHuffmanCodes(huffUtil.getTreeRoot(), "", 0);
		assertTrue(hflib.checkEncodeMap(fname, noCR, true, huffUtil.getEncodeMap()));
	}
	
	@Test
	@Order(9)
	void test_encodeMap_WAP_optimize() {
		enc_dec = new EncodeDecode(null);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String fname = "warAndPeace.txt";
		
		int[] weights = gw.readInputFileAndReturnWeights("data/"+fname);
		noCR = (weights[13] == 0);
		huffUtil.setWeights(weights);
		huffUtil.buildHuffmanTree(true);
		huffUtil.createHuffmanCodes(huffUtil.getTreeRoot(), "", 0);
		assertTrue(hflib.checkEncodeMap(fname, noCR, true, huffUtil.getEncodeMap()));
	}
	
	@Test
	@Order(10)
	void test_encodeMap_HPATS_full() {
		enc_dec = new EncodeDecode(null);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String fname = "Harry Potter and the Sorcerer.txt";
		
		int[] weights = gw.readInputFileAndReturnWeights("data/"+fname);
		huffUtil.setWeights(weights);
		noCR = (weights[13] == 0);
		huffUtil.buildHuffmanTree(false);
		huffUtil.createHuffmanCodes(huffUtil.getTreeRoot(), "", 0);
		assertTrue(hflib.checkEncodeMap(fname, noCR, false, huffUtil.getEncodeMap()));
	}
	
	@Test
	@Order(11)
	void test_encodeMap_WAP_full() {
		enc_dec = new EncodeDecode(null);
		huffUtil = new HuffmanCompressionUtilities();
		gw = new GenWeights();
		String fname = "warAndPeace.txt";
		
		int[] weights = gw.readInputFileAndReturnWeights("data/"+fname);
		noCR = (weights[13] == 0);
		huffUtil.setWeights(weights);
		huffUtil.buildHuffmanTree(false);
		huffUtil.createHuffmanCodes(huffUtil.getTreeRoot(), "", 0);
		assertTrue(hflib.checkEncodeMap(fname, noCR, false, huffUtil.getEncodeMap()));
	}

}
