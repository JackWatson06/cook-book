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

public class DoesNotWork extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel ok = new JLabel("Ok");
	private JLabel urlDoesNotWork = new JLabel("This URL Could Not Be Opened (URL Link Should Be Updated): ");
	private JLabel fileDoesNotWork = new JLabel("This File Could Not Be Opened (File Path Should Be Updated): ");
	
	private BufferedImage img;
	private ImageIcon imgIcon;
	
	private Font chalkBoardFont = new Font("Dialog", Font.BOLD, 16);
	
	public DoesNotWork(boolean frame){
		super("Does Not Work");
		try{
			if(frame){
				img = ImageIO.read(Main.class.getResource("/WoodBackgroundURLDoesNotWork.jpg"));
			}else{
				img = ImageIO.read(Main.class.getResource("/WoodBackgroundFileDoesNotWork.jpg"));
			}
			imgIcon = new ImageIcon(ImageIO.read(Main.class.getResource("/ChefIcon.png")));
		}catch(IOException e){
			
		}
		setSize(540,60);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setContentPane(new JLabel(new ImageIcon(img)));
		setIconImage(imgIcon.getImage());
		
		setLayout(new FlowLayout());
		
		MouseListener mouse = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent component) {	}

			@Override
			public void mouseEntered(MouseEvent component) {
				if(component.getSource().equals(ok)){
					ok.setForeground(darken(Color.white, 0.25F));
				}
			}

			@Override
			public void mouseExited(MouseEvent component) {
				if(component.getSource().equals(ok)){
					ok.setForeground(Color.white);
				}
			}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent component) {
				if(component.getSource().equals(ok)){
					dispose();
				}
			}
			
			
		};
		
		ok.addMouseListener(mouse);
		ok.setForeground(Color.white);
		ok.setFont(chalkBoardFont);
		
		if(frame){
			urlDoesNotWork.setForeground(Color.black);
			urlDoesNotWork.setFont(chalkBoardFont);
			add(urlDoesNotWork);
		}else{
			fileDoesNotWork.setForeground(Color.black);
			fileDoesNotWork.setFont(chalkBoardFont);
			add(fileDoesNotWork);
		}
		
		add(ok);
		
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
