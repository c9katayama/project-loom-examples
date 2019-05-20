package project_loom.examples;
public class FiberTest {

	public static void main(String[] args) throws Exception {
		long t = System.currentTimeMillis();
		TestFunctions.fiber();
		System.out.println("fiber:" + (System.currentTimeMillis() - t));
	}

}
