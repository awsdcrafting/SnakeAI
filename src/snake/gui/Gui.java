package snake.gui;
import javax.swing.*;
import java.awt.*;
/**
 * Created by scisneromam on 16.02.2018.
 */
public class Gui extends JFrame
{

	private JPanel contentPane;

	public Gui(String title, int width, int height)
	{
		this.setTitle(title);
		this.setSize(width, height);
		contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(width,height));
		contentPane.setBackground(new Color(225, 245, 255));
	}

	public void addToContentPane(JComponent component)
	{
		contentPane.add(component);
	}

	public void initialize()
	{
		this.add(contentPane);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}


}
