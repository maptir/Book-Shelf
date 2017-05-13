package PartOfProgram;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ImageSlide extends JFrame implements ActionListener {

	ImageIcon m[];
	JLabel l;
	JButton b, b1;
	int i, l1;
	JPanel p;

	public ImageSlide() {
		setLayout(new BorderLayout());
		setSize(1000, 1000);
		setVisible(true);
		JPanel p = new JPanel(new FlowLayout());
		b = new JButton("Pre");
		b1 = new JButton("Next");
		p.add(b);
		p.add(b1);
		add(p, BorderLayout.SOUTH);
		b.addActionListener(this);
		b1.addActionListener(this);
		m = new ImageIcon[2];
		m[0] = new ImageIcon("picture\\1.jpg");
		m[1] = new ImageIcon("picture\\2.jpg");
		l = new JLabel();
		l.setBounds(400, 0, getWidth(), getHeight());
		add(l, BorderLayout.CENTER);
		l.setIcon(m[0]);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == b) {
			if (i == 0) {
				JOptionPane.showMessageDialog(null, "This is First Image");
			} else {
				i = i - 1;
				l.setIcon(m[i]);
			}
		}
		if (e.getSource() == b1) {
			if (i == m.length - 1) {
				JOptionPane.showMessageDialog(null, "This is LastImage");
			} else {
				i = i + 1;
				l.setIcon(m[i]);
			}
		}

	}

	public static void main(String args[]) {
		ImageSlide i1 = new ImageSlide();
	}

}