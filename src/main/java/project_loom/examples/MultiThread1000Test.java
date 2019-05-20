package project_loom.examples;
public class MultiThread1000Test extends TestFunctions {

	public static void main(String[] args) throws Exception {
		long t = System.currentTimeMillis();
		TestFunctions.threadPool(1000);
		System.out.println("MultiThread1000:" + (System.currentTimeMillis() - t));
	}
}
