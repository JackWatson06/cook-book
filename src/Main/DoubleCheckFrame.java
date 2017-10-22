package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DoubleCheckFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel areYouSure = new JLabel("Are You Sure: ");
	private JLabel yes = new JLabel("Yes");
	private JLabel no = new JLabel("No");
	
	private BufferedImage img;
	private ImageIcon imgIcon;
	
	private Font chalkBoardFont = new Font("Dialog", Font.BOLD, 16);

	public DoubleCheckFrame(RightClickFrame frame, JLabel label){
		super("Double Check");
		try{
			img = ImageIO.read(Main.class.getResource("/WoodBackgroundDoubleCheck.jpg"));
			imgIcon = new ImageIcon(ImageIO.read(Main.class.getResource("/ChefIcon.png")));
		}catch(IOException e){
			
		}
		setSize(200, 60);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setContentPane(new JLabel(new ImageIcon(img)));
		setIconImage(imgIcon.getImage());

		
		setLayout(new FlowLayout());
		
		MouseListener mouse = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent component) {}

			@Override
			public void mouseEntered(MouseEvent component) {
				if(component.getSource().equals(yes)){
					yes.setForeground(darken(Color.white, 0.25F));
				}
				if(component.getSource().equals(no)){
					no.setForeground(darken(Color.white, 0.25F));
				}
			}

			@Override
			public void mouseExited(MouseEvent component) {
				if(component.getSource().equals(yes)){
					yes.setForeground(Color.white);
				}
				if(component.getSource().equals(no)){
					no.setForeground(Color.white);
				}
			}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent component) {
				if(component.getSource().equals(yes)){
					frame.delete(label.getText());
					dispose();
					frame.dispose();
				}
				if(component.getSource().equals(no)){
					dispose();
				}
			}
			
		};
		
		areYouSure.setFont(chalkBoardFont);
		yes.setFont(chalkBoardFont);
		no.setFont(chalkBoardFont);
		
		areYouSure.setForeground(Color.black);
		yes.setForeground(Color.white);
		no.setForeground(Color.white);
		
		yes.addMouseListener(mouse);
		no.addMouseListener(mouse);
		
		add(areYouSure);
		add(yes);
		add(no);
		
		setVisible(true);
		
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2,dim.height / 2 - this.getSize().height / 2);
		
	}
	
	private Color darken(Color color, double percentage){
		
		int red = (int) Math.round(Math.max(0, color.getRed() - (255 * percentage)));
		int green = (int) Math.round(Math.max(0, color.getGreen() - (255 * percentage)));
		int blue = (int) Math.round(Math.max(0, color.getBlue() - (255 * percentage)));
		
		int alpha = color.getAlpha();
		
		return new Color(red, green, blue, alpha);
		
	}
}
