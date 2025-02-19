package com.sm.azblb.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

@Configuration
public class AzureBlobStorageConfiguration {

	@Value("${azure.storage.container.name}")
	private String azureContainerName;
	
	@Value("${azure.storage.connection.string}")
	private String azureConnectionString;
	
	//configure connection string
	@Bean
	BlobServiceClient getServiceClient() {
		return new BlobServiceClientBuilder()
				.connectionString(azureConnectionString).buildClient();
	}

    //configure container
    @Bean
    BlobContainerClient getContainerClient() {
		return getServiceClient().getBlobContainerClient(azureContainerName);
	}
}
