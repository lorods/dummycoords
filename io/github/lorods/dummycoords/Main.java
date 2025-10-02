package io.github.lorods.dummycoords;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Main {

	public static void createInterf(String mcoords) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));
		Label label = new Label(shell, SWT.NONE);
		Composite comp = new Composite(shell, SWT.NONE);
		GridLayout glay = new GridLayout(2, false);
		glay.horizontalSpacing = 25;
		comp.setLayout(glay);
		label.setText("Your new mock coordinates: " + mcoords);
		Button expbtn = new Button(comp, SWT.CENTER);
		Button resbtn = new Button(comp, SWT.CENTER);
		resbtn.setText("Reset ./set.bak");
		expbtn.setText("Export this pair to ./set.bak");
		resbtn.pack();
		expbtn.pack();
		label.pack();
		shell.pack();
		GridData[] auxgrds = new GridData[2];
		auxgrds[0] = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		auxgrds[0].minimumWidth = (int) Math.round(resbtn.getSize().x * 1.25);
		resbtn.setLayoutData(auxgrds[0]);
		auxgrds[1] = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		auxgrds[1].minimumWidth = (int) Math.round(expbtn.getSize().x * 1.25);
		expbtn.setLayoutData(auxgrds[1]);
		int dimensX = (int) Math.round(label.getSize().x * 1.5);
		shell.setSize(dimensX, label.getSize().y * 4);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));
		comp.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));
		label.getParent().requestLayout();
		comp.getParent().requestLayout();
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
			System.out.printf("\nSomething went wrong when copying the coordinates to the clipboard: %s",
					e.getMessage());
		}
		createInterf(fcoords);
	}
}