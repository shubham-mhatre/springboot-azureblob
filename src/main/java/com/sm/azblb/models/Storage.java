package com.sm.azblb.models;

import java.io.InputStream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Storage {

	private String path;
	private String fileName;
	private InputStream inputStream;
}
