package io.github.lorods.dummycoords.instantiable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import io.github.lorods.dummycoords.interf.PairHandler;
import io.github.lorods.dummycoords.rwbridge.PairWriter;

public class CompanionFile {
	String wrcoords;

	public String getWrcoords() {
		return wrcoords;
	}

	public void setWrcoords(String fcoords) {
		this.wrcoords = fcoords;
	}

	public CompanionFile() {

	}

	public CompanionFile(String wrcoords) {
		super();
		this.wrcoords = wrcoords;
		try {
			PairHandler.prepareWrite(wrcoords);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
