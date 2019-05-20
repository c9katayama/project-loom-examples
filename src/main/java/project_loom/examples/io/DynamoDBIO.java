package project_loom.examples.io;
import java.time.Duration;
import java.util.HashMap;

import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

public class DynamoDBIO {

	private static final DynamoDbAsyncClient client = DynamoDbAsyncClient.builder()
			.httpClientBuilder(//
					NettyNioAsyncHttpClient.builder()//
					.maxConcurrency(3000)//
					.maxPendingConnectionAcquires(100_000)//
					.connectionAcquisitionTimeout(Duration.ofMinutes(5)))//
			.region(Region.US_EAST_1).build();
	private static final String TABLE_NAME = "LoomTest";
	private static final String HASH_KEY_NAME = "name";
	private static final String ATTRIBUTE_VALUE_NAME = "value";

	public static void createTable() {
		CreateTableRequest req = CreateTableRequest.builder().tableName(TABLE_NAME)
				.attributeDefinitions(AttributeDefinition.builder().attributeName(HASH_KEY_NAME)
						.attributeType(ScalarAttributeType.S).build())
				.keySchema(KeySchemaElement.builder().attributeName(HASH_KEY_NAME).keyType(KeyType.HASH).build())
				.provisionedThroughput(
						ProvisionedThroughput.builder().writeCapacityUnits(1000L).readCapacityUnits(1000L).build())
				.build();

		client.createTable(req).join();
	}

	public static void putItem(String name, String value) {
		try {
			HashMap<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();
			itemValues.put(HASH_KEY_NAME, AttributeValue.builder().s(name).build());
			itemValues.put(ATTRIBUTE_VALUE_NAME, AttributeValue.builder().s(value).build());
			PutItemRequest request = PutItemRequest.builder().tableName(TABLE_NAME).item(itemValues).build();
			client.putItem(request).join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
