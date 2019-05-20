package project_loom.examples;
public class MultiThread10Test {

	public static void main(String[] args) throws Exception {
		long t = System.currentTimeMillis();
		TestFunctions.threadPool(10);
		System.out.println("MultiThread10:" + (System.currentTimeMillis() - t));
	}
}
