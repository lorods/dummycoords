package io.github.lorods.dummycoords.utils;

import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import io.github.lorods.dummycoords.instantiable.CompanionFile;

public class DialogManager {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	AtomicBoolean isexp = new AtomicBoolean(true);
	String hint, territ; // object variables
	String instr;
	boolean isw; // also an object variable
	GridLayout errlay, missinlay;
	Shell minish, errsh;
	Label errlabl, msglabl;
	int errspacer;
	GridData[] auxgrds = new GridData[] { (new GridData(SWT.FILL, SWT.FILL, true, false)),
			(new GridData(SWT.LEFT, SWT.CENTER, true, false)),
			(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1)),
			(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1)) };

	public DialogManager() {
	}

	public DialogManager(String ccoords, boolean isw, GridLayout glay, Shell shell, Display display) {
		super();
		switch (Boolean.toString(isw)) {
		case "true": {
			glay.horizontalSpacing = 25;
			GridLayout shlay = new GridLayout(1, false);
			minish = new Shell(display);
			minish.setLayout(shlay);
			Composite rowonec = new Composite(minish, SWT.NONE);
			Composite rowtwoc = new Composite(minish, SWT.NONE);
			rowonec.setLayoutData(auxgrds[0]);
			rowonec.setLayout(glay);
			Label hintlabl = new Label(rowonec, SWT.CENTER);
			Text hinttxt = new Text(rowonec, SWT.BORDER | SWT.CENTER);
			Label terlabl = new Label(rowtwoc, SWT.CENTER);
			Text tertxt = new Text(rowtwoc, SWT.BORDER | SWT.CENTER);
			rowtwoc.setLayout(glay);
			rowtwoc.setLayoutData(auxgrds[0]);
			hinttxt.setLayoutData(auxgrds[1]);
			tertxt.setLayoutData(auxgrds[1]);
			hintlabl.setText("Location short name: ");
			terlabl.setText("Nation/State that the location is primarily bound to: ");
			Composite btncomp = new Composite(minish, SWT.NONE);
			btncomp.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
			btncomp.setLayout(new GridLayout(2, false));

			Button savebtn = new Button(btncomp, SWT.CENTER);
			savebtn.setLayoutData(auxgrds[2]);
			savebtn.setText("Save location");

			Button abrtbtn = new Button(btncomp, SWT.CENTER);
			abrtbtn.setText("Cancel");
			abrtbtn.setLayoutData(auxgrds[3]);
			hinttxt.pack();
			tertxt.pack();
			savebtn.pack();
			abrtbtn.pack();
			minish.pack();
			minish.setSize(shell.getSize().x + hintlabl.getSize().x, (int) Math.round(shell.getSize().y * 0.8));
			int inputwth = shell.getSize().x / 2;
			auxgrds[1].minimumWidth = inputwth;
			auxgrds[2].minimumWidth = (int) Math.round(savebtn.getSize().x * 1.25);
			auxgrds[3].minimumWidth = (int) Math.round(abrtbtn.getSize().x * 1.5);
			tertxt.requestLayout();
			hinttxt.requestLayout();
			savebtn.requestLayout();
			abrtbtn.requestLayout();

			minish.addDisposeListener(new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent arg0) {
					pcs.removePropertyChangeListener("expstate", null);
				}
			});

			savebtn.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					String joindmeta = hinttxt.getText() + " | " + tertxt.getText();
					if (!hinttxt.getText().isEmpty() && !tertxt.getText().isEmpty()) {
						CompanionFile cfile = new CompanionFile(ccoords);
						cfile.writeMeta(joindmeta, true);
						setHint(hint);
						setTerrit(territ);
						setIsw(isw);
						// if(isexp.get()==false)
						// isexp.set(true);
						pcs.firePropertyChange("expstate", new AtomicBoolean(false), new AtomicBoolean(true));
						minish.close();
					} else {
						missinlay = glay;
						errsh = new Shell(shell);
						missinlay.horizontalSpacing = SWT.DEFAULT;
						errsh.setLayout(missinlay);
						errlabl = new Label(errsh, SWT.NONE);
						errlabl.setImage(display.getSystemImage(SWT.ICON_ERROR));
						errspacer = errlabl.getImage().getImageData().width / 2;
						missinlay.marginLeft = errspacer;
						missinlay.marginRight = errspacer;
						missinlay.horizontalSpacing = errspacer / 2;
						msglabl = new Label(errsh, SWT.NONE);
						msglabl.setText("One or more required fields are empty. Please fill all to continue.");
						instr = "Press ESC to return to the previous window";
						errsh.setToolTipText(instr);
						msglabl.setLayoutData(auxgrds[1]);
						errlabl.pack();
						msglabl.pack();
						errsh.pack();
						for (Control cont : errsh.getChildren()) {
							cont.setToolTipText(instr);
							// cont.requestLayout();
						}

						errsh.open();

						errsh.addDisposeListener(new DisposeListener() {
							public void widgetDisposed(DisposeEvent arg0) {
								minish.setFocus();
							}
						});
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
			abrtbtn.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						getHint();
						getTerrit();
					} catch (NullPointerException npe) {
						// isexp.set(false);
						pcs.firePropertyChange("expstate", new AtomicBoolean(true), new AtomicBoolean(false));
					}
					minish.close();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});
			minish.open();
			break;
		}

		case "false": {
			errlay = glay;
			errsh = new Shell(shell);
			errlay.horizontalSpacing = SWT.DEFAULT;
			errsh.setLayout(errlay);
			errlabl = new Label(errsh, SWT.NONE);
			errlabl.setImage(display.getSystemImage(SWT.ICON_ERROR));
			errspacer = errlabl.getImage().getImageData().width / 2;
			errlay.marginLeft = errspacer;
			errlay.marginRight = errspacer;
			errlay.horizontalSpacing = errspacer / 2;
			msglabl = new Label(errsh, SWT.NONE);
			msglabl.setText("This pair is already exported. Nothing else has been done.");
			instr = "Press ESC to return to the main window";
			errsh.setToolTipText(instr);
			for (Control cont : errsh.getChildren()) {
				cont.setToolTipText(instr);
			}
			msglabl.setLayoutData(auxgrds[1]);
			errlabl.pack();
			msglabl.pack();
			errsh.pack();
			errsh.open();
			break;
		}
		}
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getTerrit() {
		return territ;
	}

	public void setTerrit(String territ) {
		this.territ = territ;
	}

	public boolean getIsw() {
		return isw;
	}

	public void setIsw(boolean isw) {
		this.isw = isw;
	}

	public AtomicBoolean getIsExp() {
		return isexp;
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		this.pcs.addPropertyChangeListener(pcl);
	}
}
