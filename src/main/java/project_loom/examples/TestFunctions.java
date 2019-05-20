package project_loom.examples;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import project_loom.examples.io.DynamoDBIO;

public class TestFunctions {

	public static final int NUM_OF_PROCESS = 100000;

	public static void fiber() throws Exception {
		final List<Fiber<?>> fiberList = new ArrayList<>();
		IntStream.range(0, NUM_OF_PROCESS).forEach((num) -> {
			fiberList.add(Fiber.schedule(() -> process(num)));
		});
		fiberList.forEach(f -> f.join());
	}

	public static void singleThread() throws Exception {
		IntStream.range(0, NUM_OF_PROCESS).forEach((num) -> {
			process(num);
		});
	}

	public static void threadPool(int threadPoolNum) throws Exception {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(threadPoolNum);
		IntStream.range(0, NUM_OF_PROCESS).forEach((num) -> {
			newFixedThreadPool.submit(() -> process(num));
		});
		newFixedThreadPool.shutdown();
		newFixedThreadPool.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
	}

	private static void process(int num) {
		DynamoDBIO.putItem(String.valueOf(num), UUID.randomUUID().toString());
		printTotalMemory(num);
	}

	private static void printTotalMemory(int num) {
		if (num % 10000 == 0) {
			System.out.println(num + " totalMemory:" + Runtime.getRuntime().totalMemory() + " freeMemory:"
					+ Runtime.getRuntime().freeMemory());
		}
	}

}
