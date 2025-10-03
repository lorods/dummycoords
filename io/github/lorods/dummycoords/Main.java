package io.github.lorods.dummycoords;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import io.github.lorods.dummycoords.instantiable.CompanionFile;
import io.github.lorods.dummycoords.instantiable.Pair;
import io.github.lorods.dummycoords.interf.PairHandler;

public class Main {

	public static void createInterf(String mcoords) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));
		Label label = new Label(shell, SWT.NONE);
		Composite comp = new Composite(shell, SWT.NONE);
		GridLayout glay = new GridLayout(2, false);
		glay.horizontalSpacing = 25;
		glay.verticalSpacing = 25;
		comp.setLayout(glay);
		label.setText("Your new mock coordinates: " + mcoords);
		Button expbtn = new Button(comp, SWT.CENTER);
		Button openbtn = new Button(comp, SWT.CENTER);
		Button rembtn = new Button(shell, SWT.CENTER);
		openbtn.setText("Open ./export.dat with the default text editor");
		expbtn.setText("Append this pair to ./export.dat");
		rembtn.setText("Delete ./export.dat");
		openbtn.pack();
		expbtn.pack();
		rembtn.pack();
		label.pack();
		shell.pack();
		GridData[] auxgrds = new GridData[3];
		auxgrds[0] = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		auxgrds[0].minimumWidth = (int) Math.round(openbtn.getSize().x * 1.1);
		openbtn.setLayoutData(auxgrds[0]);
		auxgrds[1] = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		auxgrds[1].minimumWidth = (int) Math.round(expbtn.getSize().x * 1.25);
		expbtn.setLayoutData(auxgrds[1]);
		auxgrds[2] = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		auxgrds[2].minimumWidth = (int) Math.round(rembtn.getSize().x * 1.25);
		rembtn.setLayoutData(auxgrds[2]);
		int dimensX = (int) Math.round(comp.getSize().x * 1.25);
		shell.setSize(dimensX, openbtn.getSize().y * 4);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));
		comp.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));
		label.getParent().requestLayout();
		comp.getParent().requestLayout();
		expbtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				new CompanionFile(mcoords);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		openbtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					PairHandler.openCompanionInst();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException iae) {
					iae.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		rembtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					PairHandler.deleteExport();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public static String refreshCoords() {
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
			execr.shutdown();
		} catch (Exception e) {
			System.out.printf("\nSomething went wrong when copying the coordinates to the clipboard: %s",
					e.getMessage());
		}
		return fcoords;
	}

	public static void main(String[] args) throws IOException {
		/*
		 * Pair coords = new Pair(); double curlat, curlong; curlat = coords.getLat();
		 * curlong = coords.getLongit(); String fcoords = String.format("%.5f, %.5f",
		 * curlat, curlong); Clipboard cpcoords =
		 * Toolkit.getDefaultToolkit().getSystemClipboard(); StringSelection injec = new
		 * StringSelection(fcoords); try { ExecutorService execr =
		 * Executors.newSingleThreadExecutor(); execr.submit(() ->
		 * cpcoords.setContents(injec, null));
		 * System.out.printf("Successfully copied coordinates to clipboard."); } catch
		 * (Exception e) { System.out.
		 * printf("\nSomething went wrong when copying the coordinates to the clipboard: %s"
		 * , e.getMessage()); }
		 */
		String fcoords = refreshCoords();
		createInterf(fcoords);
		// new CompanionFile(fcoords);
	}
}