package io.github.lorods.dummycoords;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import io.github.lorods.dummycoords.instantiable.CompanionFile;
import io.github.lorods.dummycoords.instantiable.Pair;
import io.github.lorods.dummycoords.interf.PairHandler;
import io.github.lorods.dummycoords.utils.DialogManager;

public class Main {

	public static String ccoords;

	public static void createInterf() {
		AtomicBoolean is_exp = new AtomicBoolean(false);
		Display display = new Display();
		String sysfnname = display.getSystemFont().getFontData()[0].getName();
		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		GridLayout shlay = new GridLayout(1, false);
		shell.setLayout(shlay);
		Composite rowonec = new Composite(shell, SWT.NONE);
		Button refrbtn = new Button(rowonec, SWT.NONE);
		refrbtn.setBackground(new Color(119, 118, 123));
		Label label = new Label(rowonec, SWT.CENTER);
		Composite rowtwoc = new Composite(shell, SWT.NONE);
		GridLayout glay = new GridLayout(2, false);
		glay.horizontalSpacing = 25;
		rowtwoc.setLayout(glay);
		rowonec.setLayout(glay);
		setLabelTxt(ccoords, label);
		refrbtn.setText("\u21BB"); // refresh char
		refrbtn.setFont(new Font(display, sysfnname,
				(int) Math.round(display.getSystemFont().getFontData()[0].getHeight() * 2.5), SWT.NORMAL));
		refrbtn.setToolTipText("Caution: this will discard the current pair of coordinates and set a new one");
		Button expbtn = new Button(rowtwoc, SWT.CENTER);
		Button openbtn = new Button(rowtwoc, SWT.CENTER);
		Button rembtn = new Button(shell, SWT.CENTER);
		openbtn.setText("Open ./export.dat with the default text editor");
		expbtn.setText("Append this pair to ./export.dat");
		rembtn.setText("Delete ./export.dat");
		refrbtn.pack();
		openbtn.pack();
		expbtn.pack();
		rembtn.pack();
		label.pack();
		shell.pack();
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		GridData[] auxgrds = new GridData[3];
		auxgrds[0] = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		auxgrds[0].minimumWidth = (int) Math.round(openbtn.getSize().x * 1.1);
		openbtn.setLayoutData(auxgrds[0]);
		auxgrds[1] = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		auxgrds[1].minimumWidth = (int) Math.round(expbtn.getSize().x * 1.1);
		expbtn.setLayoutData(auxgrds[1]);
		rowonec.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		rowtwoc.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		rowonec.requestLayout();
		rowtwoc.requestLayout();
		auxgrds[2] = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		auxgrds[2].minimumWidth = (int) Math.round(rembtn.getSize().x * 1.25);
		auxgrds[2].verticalIndent = 5;
		rembtn.setLayoutData(auxgrds[2]);
		int dimensX = (int) Math.round(rowtwoc.getSize().x * 1.25);
		rembtn.requestLayout();
		shell.requestLayout();
		shell.setSize(dimensX, (rowonec.getSize().y * 2 + rowtwoc.getSize().y));
		shell.addListener(SWT.Resize, new Listener() {

			@Override
			public void handleEvent(Event event) {
				rowonec.pack();
				label.pack();
				label.redraw();
				label.update();
				label.requestLayout();
				rowonec.requestLayout();
				rowonec.redraw();
				rowonec.update();
			}
		});

		refrbtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ccoords = refreshCoords();
				setLabelTxt(ccoords, label);
				is_exp.compareAndSet(true, false);
				rowonec.pack();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		expbtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!is_exp.get()) {
					boolean isw = true;
					new DialogManager(ccoords, isw, glay, shell, display);
					is_exp.compareAndSet(false, true);
				} else {
					boolean isw = false;
					new DialogManager(ccoords, isw, glay, shell, display);
					/*
					 * GridLayout errlay = glay; Shell errsh = new Shell(shell);
					 * errlay.horizontalSpacing = SWT.DEFAULT; errsh.setLayout(errlay); Label
					 * errlabl = new Label(errsh, SWT.NONE);
					 * errlabl.setImage(display.getSystemImage(SWT.ICON_ERROR)); int errspacer =
					 * errlabl.getImage().getImageData().width / 2; errlay.marginLeft = errspacer;
					 * errlay.marginRight = errspacer; errlay.horizontalSpacing = errspacer / 2;
					 * Label msglabl = new Label(errsh, SWT.NONE);
					 * msglabl.setText("This pair is already exported. Nothing else has been done."
					 * ); String instr = "Press ESC to return to the main window";
					 * errsh.setToolTipText(instr); for (Control cont : errsh.getChildren()) {
					 * cont.setToolTipText(instr); } msglabl.setLayoutData(auxgrds[3]);
					 * errlabl.pack(); msglabl.pack(); errsh.pack(); errsh.open();
					 */
				}
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
					is_exp.compareAndSet(true, false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		shell.setDefaultButton(expbtn);
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
		String mcoords = String.format("%.5f, %.5f", curlat, curlong);
		Clipboard cpcoords = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection injec = new StringSelection(mcoords);
		try {
			ExecutorService execr = Executors.newSingleThreadExecutor();
			execr.submit(() -> cpcoords.setContents(injec, null));
			System.out.printf("Successfully copied coordinates to clipboard.\n");
			execr.shutdown();
		} catch (Exception e) {
			System.out.printf("\nSomething went wrong when copying the coordinates to the clipboard: %s",
					e.getMessage());
		}
		return mcoords;
	}

	public static void setLabelTxt(String mcoords, Label label) {
		label.setText("Your new mock coordinates: " + mcoords);
		label.pack();
	}

	public static void main(String[] args) throws IOException {
		ccoords = refreshCoords();
		createInterf();
	}
}