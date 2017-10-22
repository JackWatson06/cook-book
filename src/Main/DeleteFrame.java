package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class DeleteFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JLabel deleteLabel = new JLabel("Delete");
	private JLabel cancelLabel = new JLabel("Cancel");
	private BufferedImage woodBack;
	private LineBorder blackline = new LineBorder(Color.black, 1);
	private Font chalkBoardFont = new Font("Dialog", Font.BOLD, 16);
	private JPanel panel = new JPanel();
	private ImageIcon imgIcon;
	
	public DeleteFrame(){
		super("Delete A Recipe");
		try{
			woodBack = ImageIO.read(Main.class.getResource("/WoodBackgroundDelete.jpg"));
			imgIcon = new ImageIcon(ImageIO.read(Main.class.getResource("/ChefIcon.png")));
		}catch(IOException e){
			
		}
		setSize(380, 70);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(new JLabel(new ImageIcon(woodBack)));
		setIconImage(imgIcon.getImage());
		
		FlowLayout flo = new FlowLayout();
		setLayout(flo);
		
		textField = new textField();
		
		
		MouseListener mouse = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent component) {}
			@Override
			public void mouseEntered(MouseEvent component) {
				if(component.getSource().equals(deleteLabel)){
					deleteLabel.setForeground(darken(Color.white, 0.25F));
				}
				if(component.getSource().equals(cancelLabel)){
					cancelLabel.setForeground(darken(Color.white, 0.25F));
				}
			}
			@Override
			public void mouseExited(MouseEvent component) {
				if(component.getSource().equals(deleteLabel)){
					deleteLabel.setForeground(Color.white);
				}
				if(component.getSource().equals(cancelLabel)){
					cancelLabel.setForeground(Color.white);
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent component) {
				if(component.getSource().equals(deleteLabel)){
					if(recipeFound() && !textField.getText().equals(new String(""))){
						if(!textField.getText().equals(new String("Delete A Recipe...")) && !textField.getText().equals(new String("Recipe Not Found..."))){
							dispose();
							deleteString();
						}
					}else if(textField.getText().equals(new String(""))){
						textField.setText("Delete A Recipe...");
					}else{
						textField.setText("Recipe Not Found...");
					}
				}
				if(component.getSource().equals(cancelLabel)){
					dispose();
				}
				if(component.getSource().equals(textField)){
					if(textField.getText().equals(new String("Delete A Recipe...")) || textField.getText().equals(new String("Recipe Not Found..."))){
						textField.setText("");
					}
				}
			}
			
		};
		
		panel.setOpaque(false);
		
		textField.setText("Delete A Recipe...");
		textField.setForeground(Color.white);
		textField.addMouseListener(mouse);
		textField.setBorder(blackline);
		deleteLabel.setFont(chalkBoardFont);
		deleteLabel.setForeground(Color.white);
		deleteLabel.addMouseListener(mouse);
		cancelLabel.setFont(chalkBoardFont);
		cancelLabel.setForeground(Color.white);
		cancelLabel.addMouseListener(mouse);
		panel.add(textField);
		panel.add(deleteLabel);
		panel.add(cancelLabel);
		
		add(panel);


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
	private boolean recipeFound(){
		for(int i = 0; i < Main.recipeList.size(); i++){
			if(textField.getText().equals(Main.recipeList.get(i).getText())){
				return true;
			}
		}
		return false;
	}
	private void deleteString(){
		int remove = 0;
		for(int i = 0; i < Main.recipeList.size() + remove; i++){
			if(textField.getText().equals(Main.recipeList.get(i - remove).getText())){
				Main.recipeList.remove(i - remove);
				Main.urlList.remove(i - remove);
				Main.fileList.remove(i - remove);
				remove++;
			}
		}
		Main.saveFirst = true;
		Main.update = true;
		Main.updateSearch = true;
	}
	
    public class textField extends JTextField {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage image;

        public textField() {
            super(20);
            try {
                image = ImageIO.read(Main.class.getResource("/ChalkBoardSearchDelete.jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public boolean isOpaque() {
            return false;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            if (image != null) {
                g2d.drawImage(image, 0, 0, this);    
            }
            super.paintComponent(g2d);
            g2d.dispose();
        }

    }
}
