//Yanaput Makbonsonglop 6481145
package Ex9_6481145;

import java.awt.event.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

class MainApplication extends JFrame 
{
    public static boolean isRunning = true;
    // components
    private JPanel            contentpane;
    private JLabel            drawpane;
    private JComboBox         combo;
    private JToggleButton     []tb;
    private ButtonGroup       bgroup;
    private JButton           moveButton, stopButton, moreButton;
    private JTextField        scoreText;
    private MyImageIcon       backgroundImg;    
    private MySoundEffect     themeSound;
    
    private BirdLabel         birdLabel;
    private MainApplication   currentFrame;

    private int framewidth  = MyConstants.FRAMEWIDTH;
    private int frameheight = MyConstants.FRAMEHEIGHT;
    private int score = 0;

    public static void main(String[] args) 
    {
        new MainApplication();
    }    

    //--------------------------------------------------------------------------
    public MainApplication(){
        setTitle("Bird Game");
	    setSize(framewidth, frameheight);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        currentFrame = this;

        // (1) Add WindowListener (anonymous class)
        //     - Stop everything. Show total score
        currentFrame.addWindowListener(new WindowListener() {

            @Override
            public void windowClosing(WindowEvent e) {
                String message = "Total score = " + score;
                isRunning = false;
                themeSound.stop();
                JOptionPane.showMessageDialog(new JFrame(), message, "Bird Game",
                        JOptionPane.INFORMATION_MESSAGE );
            }

            @Override
            public void windowOpened(WindowEvent e) { }

            @Override
            public void windowClosed(WindowEvent e) { }

            @Override
            public void windowIconified(WindowEvent e) { }

            @Override
            public void windowDeiconified(WindowEvent e) { }

            @Override
            public void windowActivated(WindowEvent e) { }

            @Override
            public void windowDeactivated(WindowEvent e) { }
        });

        contentpane = (JPanel)getContentPane();
        contentpane.setLayout(new BorderLayout());
        AddComponents();
    } 
    
    //--------------------------------------------------------------------------
    public void AddComponents() {
        backgroundImg  = new MyImageIcon(MyConstants.FILE_BG).resize(framewidth, frameheight);
        drawpane = new JLabel();
	    drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);

	    themeSound = new MySoundEffect(MyConstants.FILE_THEME);
        themeSound.playLoop();
        themeSound.setVolume(0.4f);
        
        birdLabel = new BirdLabel(currentFrame);
        drawpane.add(birdLabel);
        
        
        // (2) Add ActionListener (anonymous class) to moveButton
        //     - If Bird isn't moving, create birdThread to make it move
	    moveButton = new JButton("Move");
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!birdLabel.isMove()){
                    setBirdThread();
                    birdLabel.setMove(true);
                }
            }
        });
  

        // (3) Add ActionListener (anonymous class) to stopButton
        //     - Stop birdThread, i.e. make it return from method run
	    stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(birdLabel.isMove())
                    birdLabel.setMove(false);
            }
        });
  

        
	// (4) Add ItemListener (anonymouse class) to combo 
        //     - Set Bird's speed, i.e. sleeping time for birdThread
        String[] speed = { "fast", "medium", "slow"};
        combo = new JComboBox(speed);
	    combo.setSelectedIndex(1);
        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int sleepTime = 0;
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String selectedSpeed = (String) combo.getSelectedItem();
                    switch (selectedSpeed){
                        case "fast":
                            sleepTime = 100;
                            break;
                        case "medium":
                            sleepTime = 500;
                            break;
                        case "slow":
                            sleepTime = 1000;
                            break;
                        case null, default:
                            break;
                    }
                }
                birdLabel.setSpeed(sleepTime);
            }
        });

        
	// (5) Add ItemListener (anonymouse class) to tb[i]
        //     - Make Bird turn left/right
        //     - Make sure that only 1 direction is selected at a time
        tb = new JToggleButton[2];
        bgroup = new ButtonGroup();      
        tb[0] = new JRadioButton("Left");   tb[0].setName("Left");
        tb[1] = new JRadioButton("Right");  tb[1].setName("Right"); 
	    tb[1].setSelected(true);

        for(JToggleButton button: tb){
            bgroup.add(button);
            button.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED){
                       switch (button.getName()){
                        case "Left":
                            birdLabel.turnLeft();
                            break;
                       case "Right":
                            birdLabel.turnRight();
                            break;
                       }
                    }
                }
            });
        }


        
        // (6) Add ActionListener (anonymous class) to moreButton
        //     - Create a new itemThread that controls balloon/heart/rock
	    moreButton = new JButton("More Balloon");
        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setItemThread();
            }
        });
		

        scoreText = new JTextField("0", 5);
        scoreText.setEditable(false);

        JPanel control  = new JPanel();
        control.setBounds(0,0,1000,50);
	    control.add(new JLabel("Bird Control : "));
        control.add(moveButton);
        control.add(stopButton);
        control.add(combo);
        control.add(tb[0]);
        control.add(tb[1]);
        control.add(new JLabel("                 "));
        control.add(new JLabel("Item Control : "));
        control.add(moreButton);
        control.add(new JLabel("                 "));
        control.add(new JLabel("Score : "));
        control.add(scoreText);
        contentpane.add(control, BorderLayout.NORTH);
        contentpane.add(drawpane, BorderLayout.CENTER);     
        validate();       
    }    

    //--------------------------------------------------------------------------
    public void setBirdThread()
    {
        Thread birdThread = new Thread() {
            public void run()
            {
                while (birdLabel.isMove() && isRunning)
                {
                    birdLabel.updateLocation();
                }          
            } // end run
        }; // end thread creation
        birdThread.start();
    }
    //--------------------------------------------------------------------------
    public void setItemThread() {
        Thread itemThread = new Thread() {
            public void run()
            {
                ItemLabel item = new ItemLabel(currentFrame);
                drawpane.add(item);
                while(isRunning){
                    drawpane.repaint();
                    if(!item.isHit()){
                        if(item.getBounds().intersects((item.getParentFrame().birdLabel.getBounds()))){
                            item.playHitSound();
                            item.setIcon(item.getSecretImg());
                            updateScore(item.hitpoints[item.getType()]);
                            item.setHit(true);
                        }
                    }
                    else {
                        if (item.getType() == 0)
                            item.setLocation(item.getX(), item.getY() + 30);

                        else if(item.getType() == 1)
                            item.setLocation(item.getX(), item.getY() - 30);
                    }
                    if (item.getY() > MyConstants.FRAMEHEIGHT + MyConstants.ITEMHEIGHT || item.getY() < -MyConstants.ITEMHEIGHT) {
                        drawpane.remove(item);
                        drawpane.repaint();
                        break;
                    }
                    try{
                        Thread.sleep(item.getSpeed());
                    }catch (Exception e){
                        System.err.println(e);
                    }

                }

                // (7) Create a new ItemLabel & add it to drawpane
                //     Loop:
                //     - If the item is balloon, don't update location but check
                //       whether it collides with Bird. If it does, change image
                //       to heart/rock, play hit sound, update score
                //
                //     - If the item is now heart/rock, update its location. No
                //       need to check collision any more. Once reaching the
                //       top/bottom, remove it from drawpane and end this thread

            } // end run
        }; // end thread creation
        itemThread.start();

    }
    //--------------------------------------------------------------------------
    synchronized public void updateScore(int scoreUpdate)
    {
        score += scoreUpdate;
        scoreText.setText(Integer.toString(score));
        // (8) Score update must be synchronized since it can be done by >1 itemThreads

    }  
    
} // end class MainApplication


////////////////////////////////////////////////////////////////////////////////
class BirdLabel extends JLabel
{
    private MainApplication  parentFrame;   
    private MyImageIcon      leftImg, rightImg;      
        
    private int width    = MyConstants.BIRDWIDTH;
    private int height   = MyConstants.BIRDHEIGHT;
    private int curY     = MyConstants.BIRD_Y;
    private int curX     = 300;
    private int speed    = 500;
    private boolean right = true, move = false;        
        
    public BirdLabel(MainApplication pf)
    {
        parentFrame = pf;
            
        leftImg  = new MyImageIcon(MyConstants.FILE_BIRD_LEFT).resize(width, height);
        rightImg = new MyImageIcon(MyConstants.FILE_BIRD_RIGHT).resize(width, height);
        setIcon(rightImg);
        setBounds(curX, curY, width, height);
    }
        
    public void setSpeed(int s)     { speed = s; }
    public void turnLeft()          { setIcon(leftImg);  right = false; }
    public void turnRight()         { setIcon(rightImg); right = true; }
    public void setMove(boolean m)  { move = m; }
    public boolean isMove()         { return move; }
        
    public void updateLocation()
    {
        if (!right)
        {   
            curX = curX - 50;
            if (curX < -100) { curX = parentFrame.getWidth(); } 			
        }
        else
        {
            curX = curX + 50;
            if (curX > parentFrame.getWidth()-100) { curX = 0; }			
        }
        setLocation(curX, curY);
        repaint();             
        try { Thread.sleep(speed); } 
        catch (InterruptedException e) { e.printStackTrace(); }            
    } 
    
} // end class BirdLabel

////////////////////////////////////////////////////////////////////////////////
class ItemLabel extends JLabel 
{
    private MainApplication  parentFrame;   
    
    private int              type;    // 0 = bad item (falling down), 1 = good item (floating up)
    private MyImageIcon      mainImg, secretImg;
    private MySoundEffect    hitSound;
    private boolean          hit;
    
    String imageFileMain = MyConstants.FILE_BALLOON;
    String [] imageFiles = { MyConstants.FILE_ROCK, MyConstants.FILE_HEART };        
    String [] soundFiles = { MyConstants.FILE_SFX_BAD,MyConstants.FILE_SFX_GOOD };
    int    [] hitpoints  = { -1, 1 };

    private int width    = MyConstants.ITEMWIDTH;
    private int height   = MyConstants.ITEMHEIGHT;
    private int startY   = MyConstants.BIRD_Y;
    private int curX, curY;
    private int speed = 400;

    public ItemLabel(MainApplication pf)
    {
        Random rand = new Random();
        parentFrame = pf;
        mainImg     = new MyImageIcon(imageFileMain).resize(width, height+40);
        hit  = false;        
        curX = rand.nextInt(10, parentFrame.getWidth()-100);
        curY = startY + rand.nextInt(0, 50);
        setIcon(mainImg);
        setBounds(curX, curY, width, height+40);        
        
        if (curX % 2 == 0) { type = 0; }
        else               { type = 1; }          
        secretImg = new MyImageIcon(imageFiles[type]).resize(width, height);
        hitSound  = new MySoundEffect(soundFiles[type]);
    }
        
    public void playHitSound(){ hitSound.playOnce(); }

    public int  getHitPoints(){ return hitpoints[type]; }

    public boolean isHit(){return this.hit;}

    public int getType(){return this.type;}

    public MainApplication getParentFrame(){return this.parentFrame;}

    public void setHit(boolean hit){this.hit = hit;}

    public MyImageIcon getSecretImg(){return this.secretImg;}

    public int getSpeed(){return this.speed;}
} // end class ItemLabel