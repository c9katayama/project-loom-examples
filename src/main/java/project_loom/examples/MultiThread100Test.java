package project_loom.examples;
public class MultiThread100Test extends TestFunctions {

	public static void main(String[] args) throws Exception {
		long t = System.currentTimeMillis();
		TestFunctions.threadPool(100);
		System.out.println("MultiThread100:" + (System.currentTimeMillis() - t));
	}
}
