package com.sm.azblb.services;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;
import com.sm.azblb.models.Storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AzureStorageServiceImpl implements IAzureStorageService {
	
	@Value("${azure.storage.container.name}")
	private String azureContainerName;
	
	private final BlobServiceClient blobServiceServiceClient;
	
	private final BlobContainerClient blobContainerClient;
	

	@Override
	public String write(Storage storage) {
		log.info("inside write method");
		String filePath = getFilePath(storage);
		BlobClient client= blobContainerClient.getBlobClient(filePath);
		client.upload(storage.getInputStream(), false);
		log.info("file uploaded to blob");
		return filePath;
	}

	@Override
	public String update(Storage storage) {
		log.info("inside write method");
		String filePath = getFilePath(storage);
		BlobClient client= blobContainerClient.getBlobClient(filePath);
		client.upload(storage.getInputStream(), true);
		log.info("file updated to blob");
		return filePath;
	}

	@Override
	public byte[] read(Storage storage) {
		log.info("inside read method");
		String filePath = getFilePath(storage);
		BlobClient client= blobContainerClient.getBlobClient(filePath);
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		client.downloadStream(outputStream);
		byte[] bytes=outputStream.toByteArray();
		return bytes;
	}

	@Override
	public List<String> getAllFile(Storage storage) {
		PagedIterable<BlobItem> blobList= blobContainerClient.listBlobsByHierarchy(storage.getPath()+"/");
		List<String> blobNameList=new ArrayList<>();
		
		for(BlobItem blobItem:blobList) {
			blobNameList.add(blobItem.getName());
		}
		return blobNameList;
	}

	@Override
	public void delete(Storage storage) {
		log.info("inside delete");
		String filePath =getFilePath(storage);
		log.info("inside delete filePath "+filePath);
		BlobClient client=blobContainerClient.getBlobClient(filePath);
		client.delete();
		log.info("blob deleted");
	}

	@Override
	public void createContainer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteContainer() {
		// TODO Auto-generated method stub
		
	}

	private String getFilePath(Storage storage) {
		return storage.getPath()+"/"+ storage.getFileName();
	}
	

}
