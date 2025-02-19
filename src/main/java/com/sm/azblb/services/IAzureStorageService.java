package com.sm.azblb.services;

import java.util.List;

import com.sm.azblb.models.Storage;

public interface IAzureStorageService {

	public String write(Storage storage);
	
	public String update(Storage storage);
	
	public byte[] read(Storage storage);
	
	public List<String> getAllFile(Storage storage);
	
	public void delete(Storage storage);
	
	public void createContainer();
	
	public void deleteContainer();
}
