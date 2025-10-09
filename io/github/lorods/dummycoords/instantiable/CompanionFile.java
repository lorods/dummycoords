package io.github.lorods.dummycoords.instantiable;

import java.io.IOException;

import io.github.lorods.dummycoords.interf.PairHandler;

public class CompanionFile {
	String wrcoords;
	String wrmdata;

	public String getWrmdata() {
		return wrmdata;
	}

	public void setWrmdata(String wrmdata) {
		this.wrmdata = wrmdata;
	}

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
			PairHandler.prepareWrite(this.wrcoords);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeMeta(String wrmdata, Boolean ismeta) {
		this.wrmdata = wrmdata;
		try {
			PairHandler.prepareWrite(this.wrmdata, ismeta);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
