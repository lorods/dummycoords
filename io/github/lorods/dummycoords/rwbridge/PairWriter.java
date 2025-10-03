package io.github.lorods.dummycoords.rwbridge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.lorods.dummycoords.interf.PairHandler;

public class PairWriter implements PairHandler {
	public static void writeInstance(String fcoords, Path companionpath, BufferedWriter bw) {
		ExecutorService execr = Executors.newSingleThreadExecutor();
		execr.submit(() -> {
			try {
				System.out.printf("%s", companionpath.toString());
				bw.write(fcoords);
				bw.newLine();
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		execr.shutdown();
	}
}
