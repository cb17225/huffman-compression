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
class HF_GenWeightsTest {

	HuffCompTestLib hflib = new HuffCompTestLib();
    private static boolean ignoreChr13 = false; 
    
    private static String getOperatingSystem() {
    	String os = System.getProperty("os.name");
    	return os;
    }
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Running on: "+getOperatingSystem());
		ignoreChr13 = !(getOperatingSystem().contains("Win"));
		if (ignoreChr13) {
			System.out.println("Non-Windows platform detected; setting ignoreChr13="+ignoreChr13);			
		} else {
			System.out.println("Windows platform detected; setting ignoreChr13="+ignoreChr13);			
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
	void test_GenWeights_simple() {
		GenWeights gw = new GenWeights();
		
		int[] tstWeights = gw.readInputFileAndReturnWeights("data/simple.txt");
		boolean ignCHR13 = (tstWeights[13] == 0);
		assertTrue(hflib.checkWeights("simple.txt", ignCHR13, tstWeights));
	}
	
	@Test
	@Order(2)
	void test_GenWeights_GEAH() {
		GenWeights gw = new GenWeights();
		
		int[] tstWeights = gw.readInputFileAndReturnWeights("data/Green Eggs and Ham.txt");
		boolean ignCHR13 = (tstWeights[13] == 0);
		assertTrue(hflib.checkWeights("Green Eggs and Ham.txt", ignCHR13, tstWeights));
	}

	@Test
	@Order(3)
	void test_GenWeights_TCITH() {
		GenWeights gw = new GenWeights();
		
		int[] tstWeights = gw.readInputFileAndReturnWeights("data/The Cat in the Hat.txt");
		boolean ignCHR13 = (tstWeights[13] == 0);
		assertTrue(hflib.checkWeights("The Cat in the Hat.txt", ignCHR13, tstWeights));
	}

	@Test
	@Order(4)
	void test_GenWeights_HPATS() {
		GenWeights gw = new GenWeights();
		
		int[] tstWeights = gw.readInputFileAndReturnWeights("data/Harry Potter and the Sorcerer.txt");
		boolean ignCHR13 = (tstWeights[13] == 0);
		assertTrue(hflib.checkWeights("Harry Potter and the Sorcerer.txt", ignCHR13, tstWeights));
	}

	@Test
	@Order(5)
	void test_GenWeights_WAP() {
		GenWeights gw = new GenWeights();
		
		int[] tstWeights = gw.readInputFileAndReturnWeights("data/warAndPeace.txt");
		boolean ignCHR13 = (tstWeights[13] == 0);
		assertTrue(hflib.checkWeights("warAndPeace.txt", ignCHR13, tstWeights));
	}

}
