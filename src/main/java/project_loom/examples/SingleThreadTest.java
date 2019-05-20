package project_loom.examples;
public class SingleThreadTest {

	public static void main(String[] args) throws Exception {
		long t = System.currentTimeMillis();
		TestFunctions.singleThread();
		System.out.println("SingleThread:" + (System.currentTimeMillis() - t));
	}
}
