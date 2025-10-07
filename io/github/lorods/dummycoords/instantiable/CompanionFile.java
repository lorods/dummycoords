package io.github.lorods.dummycoords.instantiable;

import java.io.IOException;

import io.github.lorods.dummycoords.interf.PairHandler;

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
