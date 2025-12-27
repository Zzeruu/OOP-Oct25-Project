import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class placeholder {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new placeholder().buildAndShow());
	}

	private JTextField nameField;
	private JTextField emailField;
	private JTextField ageField;
	private JLabel statusLabel;

	private Color nameOrigBg;
	private Color emailOrigBg;
	private Color ageOrigBg;

	private void buildAndShow() {
		JFrame f = new JFrame("Form Validator");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(360, 220);
		f.setLocationRelativeTo(null);

		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
		p.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(6,6,6,6);
		c.anchor = GridBagConstraints.WEST;

		// Name
		c.gridx = 0; c.gridy = 0;
		p.add(new JLabel("Name:"), c);
		nameField = new JTextField(18);
		c.gridx = 1; c.gridy = 0;
		p.add(nameField, c);

		// Email
		c.gridx = 0; c.gridy = 1;
		p.add(new JLabel("Email:"), c);
		emailField = new JTextField(18);
		c.gridx = 1; c.gridy = 1;
		p.add(emailField, c);

		// Age
		c.gridx = 0; c.gridy = 2;
		p.add(new JLabel("Age:"), c);
		ageField = new JTextField(8);
		c.gridx = 1; c.gridy = 2;
		p.add(ageField, c);

		// status label
		statusLabel = new JLabel(" ");
		statusLabel.setForeground(Color.DARK_GRAY);
		c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
		p.add(statusLabel, c);

		// store original backgrounds
		nameOrigBg = nameField.getBackground();
		emailOrigBg = emailField.getBackground();
		ageOrigBg = ageField.getBackground();

		// attach focus listeners
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				clearFieldState(nameField, nameOrigBg);
			}

			@Override
			public void focusLost(FocusEvent e) {
				String v = nameField.getText();
				if (v == null || v.trim().isEmpty()) {
					nameField.setBackground(Color.RED);
					statusLabel.setText("Name must not be empty.");
				} else {
					clearFieldState(nameField, nameOrigBg);
				}
			}
		});

		emailField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				clearFieldState(emailField, emailOrigBg);
			}

			@Override
			public void focusLost(FocusEvent e) {
				String v = emailField.getText();
				if (v == null || !v.contains("@")) {
					emailField.setBackground(Color.YELLOW);
					statusLabel.setText("Email must contain '@'.");
				} else {
					clearFieldState(emailField, emailOrigBg);
				}
			}
		});

		ageField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				clearFieldState(ageField, ageOrigBg);
			}

			@Override
			public void focusLost(FocusEvent e) {
				String v = ageField.getText();
				if (v == null || v.trim().isEmpty()) {
					// allow empty age silently (or consider warning)
					clearFieldState(ageField, ageOrigBg);
					return;
				}
				try {
					Integer.parseInt(v.trim());
					clearFieldState(ageField, ageOrigBg);
				} catch (NumberFormatException ex) {
					ageField.setText("");
					ageField.setBackground(Color.YELLOW);
					statusLabel.setText("Age must be a number; cleared.");
				}
			}
		});

		f.setContentPane(p);
		f.setVisible(true);
	}

	private void clearFieldState(JTextField field, Color orig) {
		field.setBackground(orig);
		// if no other field shows error, clear status
		if (allFieldsHaveDefaultBg()) {
			statusLabel.setText(" ");
		}
	}

	private boolean allFieldsHaveDefaultBg() {
		return nameField.getBackground().equals(nameOrigBg)
				&& emailField.getBackground().equals(emailOrigBg)
				&& ageField.getBackground().equals(ageOrigBg);
	}
}

