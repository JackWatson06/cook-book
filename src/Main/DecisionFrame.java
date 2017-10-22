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

public class DecisionFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JLabel onlyOne = new JLabel("You May Only Have One URL or Path: ");
	public JLabel keepUrl = new JLabel("Keep URL");
	public JLabel keepPath = new JLabel("Keep Path");
	public JLabel cancel = new JLabel("Cancel");
	private Font chalkBoardFont = new Font("Dialog", Font.BOLD, 16);
	public BufferedImage woodBack;
	private ImageIcon imgIcon;
	
	public DecisionFrame(boolean frame){
		super("Decision Frame");
		try{
			woodBack = ImageIO.read(Main.class.getResource("/WoodBackgroundDecision.jpg"));
			imgIcon = new ImageIcon(ImageIO.read(Main.class.getResource("/ChefIcon.png")));
		}catch(IOException e){
			
		}
		setSize(540,60);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(new JLabel(new ImageIcon(woodBack)));
		setIconImage(imgIcon.getImage());
		setLayout(new FlowLayout());
		
		MouseListener mouse = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent component) {}
			@Override
			public void mouseEntered(MouseEvent component) {
				if(component.getSource().equals(cancel)){
					cancel.setForeground(darken(Color.white, 0.25F));
				}
				if(component.getSource().equals(keepUrl)){
					keepUrl.setForeground(darken(Color.white, 0.25F));
				}
				if(component.getSource().equals(keepPath)){
					keepPath.setForeground(darken(Color.white, 0.25F));
				}
			}
			@Override
			public void mouseExited(MouseEvent component) {
				if(component.getSource().equals(cancel)){
					cancel.setForeground(Color.white);
				}
				if(component.getSource().equals(keepUrl)){
					keepUrl.setForeground(Color.white);
				}
				if(component.getSource().equals(keepPath)){
					keepPath.setForeground(Color.white);
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent component) {
				if(component.getSource().equals(cancel)){
					dispose();
				}
				if(component.getSource().equals(keepUrl)){
					if(frame){
						AddFrame.changeFields(true);
					}else{
						RightClickFrame.changeFields(true);
					}
					dispose();
				}
				if(component.getSource().equals(keepPath)){
					if(frame){
						AddFrame.changeFields(false);
					}else{
						RightClickFrame.changeFields(false);
					}
					dispose();
				}
			}
			
		};
		
		onlyOne.setForeground(Color.black);
		keepUrl.setForeground(Color.white);
		keepPath.setForeground(Color.white);
		cancel.setForeground(Color.white);
		
		onlyOne.setFont(chalkBoardFont);
		keepUrl.setFont(chalkBoardFont);
		keepPath.setFont(chalkBoardFont);
		cancel.setFont(chalkBoardFont);
		
		keepUrl.addMouseListener(mouse);
		keepPath.addMouseListener(mouse);
		cancel.addMouseListener(mouse);
		
		add(onlyOne);
		add(keepUrl);
		add(keepPath);
		add(cancel);
		
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
