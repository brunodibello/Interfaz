package app;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {
	
	private DefFrame defFrame;
	private InputFrame inputFrame;
	
	public MainFrame(String title) {
		super(title);
		
		defFrame = new DefFrame();
		inputFrame = new InputFrame();
		
		inputFrame.confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean consistent = HermitHelper.runReasoner(defFrame.getDefRows(), inputFrame.getInputRows());
				showReasonerResult(consistent);
			}
		});
				
		defFrame.updateDefBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputFrame.updateDef(defFrame.getAsientos());
			}
		});
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		add(mainPanel);
		
		mainPanel.add(defFrame);
		mainPanel.add(inputFrame);
		
		setSize(1600,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void showReasonerResult(Boolean consistent) {
		if (consistent) {
			JOptionPane.showMessageDialog(this, "Ontologia Consistente");
		} else {
			JOptionPane.showMessageDialog(this, "Ontologia Inconsistent");
		}
	}

}
