package io.github.lorods.dummycoords.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
	String hint, territ; // object variables
	String instr;
	boolean isw; // also an object variable
	GridLayout errlay;
	Shell minish;
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
		this.hint = hint;
		this.territ = territ;
		this.isw = isw;
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
			terlabl.setText("Nation/State that is primarily bound to the location: ");
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

			savebtn.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					CompanionFile cfile = new CompanionFile(ccoords);
					String joindmeta = hinttxt.getText() + " | " + tertxt.getText();
					if (!hinttxt.getText().isEmpty() && !tertxt.getText().isEmpty())
						cfile.writeMeta(joindmeta, true);
					minish.close();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});
			abrtbtn.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
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
			minish = new Shell(shell);
			errlay.horizontalSpacing = SWT.DEFAULT;
			minish.setLayout(errlay);
			errlabl = new Label(minish, SWT.NONE);
			errlabl.setImage(display.getSystemImage(SWT.ICON_ERROR));
			errspacer = errlabl.getImage().getImageData().width / 2;
			errlay.marginLeft = errspacer;
			errlay.marginRight = errspacer;
			errlay.horizontalSpacing = errspacer / 2;
			msglabl = new Label(minish, SWT.NONE);
			msglabl.setText("This pair is already exported. Nothing else has been done.");
			instr = "Press ESC to return to the main window";
			minish.setToolTipText(instr);
			for (Control cont : minish.getChildren()) {
				cont.setToolTipText(instr);
			}
			msglabl.setLayoutData(auxgrds[1]);
			errlabl.pack();
			msglabl.pack();
			minish.pack();
			minish.open();
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
}
