package io.github.lorods.dummycoords;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Main {

	public static void createInterf(String mcoords) {
		Display display = new Display();
		Shell shell = new Shell(display);
		Label label = new Label(shell, SWT.CENTER);
		FillLayout flay = new FillLayout();
		flay.marginHeight = 10;
		shell.setLayout(flay);
		label.setText("Your new mock coordinates: " + mcoords);
		label.pack();
		shell.pack();
		int dimensX = (int) Math.round(label.getSize().x * 1.25);
		shell.setSize(dimensX, label.getSize().y * 2);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public static void main(String[] args) {
		/*
		 * Random gen = new Random(); double latitude, longitude; int len; latitude =
		 * gen.nextDouble() * 180 - 90; longitude = gen.nextDouble() * 360 - 180;
		 */
		Pair coords = new Pair();
		double curlat, curlong;
		curlat = coords.getLat();
		curlong = coords.getLongit();
		String fcoords = String.format("%.5f, %.5f", curlat, curlong);
		Clipboard cpcoords = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection injec = new StringSelection(fcoords);
		try {
			ExecutorService execr = Executors.newSingleThreadExecutor();
			execr.submit(() -> cpcoords.setContents(injec, null));
			System.out.printf("Successfully copied coordinates to clipboard.");
		} catch (Exception e) {
			System.out.printf("\nSomething went wrong when copying the coordinates to the clipboard: %s", e.getMessage());
		}
		createInterf(fcoords);
	}
}
