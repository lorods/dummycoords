package io.github.lorods.dummycoords.interf;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.lorods.dummycoords.rwbridge.PairWriter;

public interface PairHandler {

	final Path companionpath = Paths.get("companion/export.dat").toAbsolutePath();
	File accompfile = companionpath.toFile(); 
	
	public static void prepareWrite(String fcoords) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(accompfile, Charset.forName("UTF-8"), true));
		PairWriter.writeInstance(fcoords, companionpath, bw);
	};
	
	public static void prepareWrite(String mdata, boolean ismeta) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(accompfile, Charset.forName("UTF-8"), true));
		PairWriter.writeInstance(ismeta, mdata, companionpath, bw);
	}

	public static void openCompanionInst() throws IOException {
		Desktop.getDesktop().open(accompfile);
	}
	
	public static void deleteExport() throws IOException {
		Files.deleteIfExists(companionpath);
	}
}
