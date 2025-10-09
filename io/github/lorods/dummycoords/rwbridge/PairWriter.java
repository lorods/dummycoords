package io.github.lorods.dummycoords.rwbridge;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.lorods.dummycoords.interf.PairHandler;

public class PairWriter implements PairHandler {
	public static void writeInstance(Boolean ismeta, String fields, Path companionpath, BufferedWriter bw) {
		ExecutorService execr = Executors.newSingleThreadExecutor();
		execr.submit(() -> {
			try {
				bw.write(fields);
				bw.newLine();
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		execr.shutdown();
	}

	public static void writeInstance(String fields, Path companionpath, BufferedWriter bw) {
		ExecutorService execr = Executors.newSingleThreadExecutor();
		execr.submit(() -> {
			try {
				System.out.printf("%s\n", companionpath.toString());
				bw.write(fields + " | ");
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		execr.shutdown();
	}
}