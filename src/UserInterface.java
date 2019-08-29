import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

public class UserInterface extends JFrame {
    Grid grid = Grid.getInstance();

    // GUI Component dimensions.
    private final int CANVAS_INITIAL_WIDTH = grid.getWidth();
    private final int CANVAS_INITIAL_HEIGHT = grid.getHeight();
    private final int CONTROL_PANEL_WIDTH = 200;

    // Swing Elements
    private Canvas canvas;
    private JMenuBar menuBar;
    private JPanel controlPanel;
    private JButton nextButton, resetButton, scaleButton, templateButton;
    private JToggleButton playButton;
    private JCheckBox infiniteButton, invertButton;
    private JSlider gridScaleSlider;
    private ButtonGroup buttonGroup;
    public static JLabel infoLabel;

    public UserInterface()
    {
        setTitle("Game Of Life +");
        setLayout(new BorderLayout());  // Layout manager for the frame.

        // Windows Look and Feel
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // LISTENERS
        class CanvasMouseMotionListener implements MouseMotionListener
        {
            public void mouseMoved(MouseEvent e) { ; }

            public void mouseDragged(MouseEvent event)
            {
                int row = (event.getY()/(grid.getCellSize() + grid.getCellInnerGap())) - grid.getScale();
                int col = (event.getX()/(grid.getCellSize() + grid.getCellInnerGap())) - grid.getScale();

                if((row >= 0 && row < grid.getRowSize()) && (col >= 0 && col < grid.getColSize()) && !grid.isRunning()) {
                    if(SwingUtilities.isLeftMouseButton(event)){
                        grid.setCell(row,col,true);
                    } else if(SwingUtilities.isRightMouseButton(event)) {
                        grid.setCell(row,col,false);
                    }
                }
                canvas.repaint();
            }
        }
        class CanvasMouseListener implements MouseListener
        {
            CanvasMouseMotionListener motionListener = new CanvasMouseMotionListener();
            public void mousePressed(MouseEvent event) {
                motionListener.mouseDragged(event);
            }
            public void mouseReleased(MouseEvent event) { ; }
            public void mouseClicked(MouseEvent event) { ; }
            public void mouseEntered(MouseEvent event) { ; }
            public void mouseExited(MouseEvent event) { ; }
        }
        class FileExitMenuListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                int a = JOptionPane.showConfirmDialog(null,"Are you sure you want to quit?", "Are you sure?", JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        }
        class HelpAboutMenuListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                JOptionPane.showMessageDialog(new JFrame(),"A remake of John Conway's Game of Life with added functionality.\n\nMade by Matthew Peake","About",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        class ClearGridListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                playButton.setSelected(false);
                grid.resetGrid();
                canvas.repaint();
            }
        }
        class NextButtonListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                playButton.setSelected(false);
                grid.nextGeneration();
                canvas.repaint();
            }
        }
        class PlayActionListener implements ItemListener
        {
            public void itemStateChanged(ItemEvent event)
            {
                int state = event.getStateChange();

                if (state == ItemEvent.SELECTED) {
                    grid.setRunning(true);
                    playButton.setText("Pause");
                }
                else {
                    grid.setRunning(false);
                    playButton.setText("Play");
                }
            }
        }
        class InfiniteButtonListener implements ItemListener
        {
            public void itemStateChanged(ItemEvent event)
            {
                if(event.getStateChange() == ItemEvent.SELECTED) {
                    grid.setInfinite(true);
                } else {
                    grid.setInfinite(false);
                }
            }
        }
        class InvertButtonListener implements ItemListener
        {
            public void itemStateChanged(ItemEvent event)
            {
                if(event.getStateChange() == ItemEvent.SELECTED) {
                    grid.setInverted(true);
                } else {
                    grid.setInverted(false);
                }
                canvas.repaint();
            }
        }
        class ScaleButtonListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                gridScaleSlider = new JSlider(JSlider.HORIZONTAL,1,4,grid.getScale());
                  gridScaleSlider.setPreferredSize(new Dimension(400, 75));
                  gridScaleSlider.setMajorTickSpacing(1);
                  gridScaleSlider.setPaintTicks(true);
                  gridScaleSlider.setPaintLabels(true);

                Object[] options = {"Confirm & Reset Grid","Cancel"};
                int answer = JOptionPane.showOptionDialog(null, new Object[] { "Select a value: ", gridScaleSlider }, "Change Grid Scale", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,options,null);

                if( answer == JOptionPane.OK_OPTION){
                    if(gridScaleSlider.getValue() != grid.getScale()) {
                        grid.setScale(gridScaleSlider.getValue());
                        playButton.setSelected(false);
                        System.out.println("Input: " + grid.getScale());
                    }
                }
            }
        }
        class TemplateButtonListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                int TEMPLATE_PANEL_WIDTH = 400;
                int TEMPLATE_PANEL_HEIGHT = 200;

                buttonGroup = new ButtonGroup();

                JFrame frame = new JFrame("Templates");
                  frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel panel = new JPanel();
                  panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                  panel.setOpaque(true);

                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Frame Button Panel + ScrollPane
                JPanel buttonPanel = new JPanel();
                  buttonPanel.setPreferredSize(new Dimension(TEMPLATE_PANEL_WIDTH, TEMPLATE_PANEL_HEIGHT));
                  JScrollPane scroller = new JScrollPane(buttonPanel);
                  scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                  scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                // Glider Panel
                JPanel gliderPanel = new JPanel();
                  gliderPanel.setBorder(new TitledBorder(new EtchedBorder(), "Gliders"));
                  gliderPanel.setPreferredSize(new Dimension(TEMPLATE_PANEL_WIDTH - 20, 60));
                  // Load JRadioButton from Templates
                  for(String template : Templates.getGlider()) {
                      JRadioButton button = new JRadioButton(template);
                      buttonGroup.add(button);
                      gliderPanel.add(button);
                  }
                buttonPanel.add(gliderPanel);

                // Oscillator Panel
                JPanel oscillatorPanel = new JPanel();
                  oscillatorPanel.setBorder(new TitledBorder(new EtchedBorder(), "Oscillators"));
                  oscillatorPanel.setPreferredSize(new Dimension(TEMPLATE_PANEL_WIDTH - 20, 60));
                  // Load JRadioButtons from Templates
                  for(String template : Templates.getOscillator()) {
                      JRadioButton button = new JRadioButton(template);
                      buttonGroup.add(button);
                      oscillatorPanel.add(button);
                  }
                buttonPanel.add(oscillatorPanel);

                // Other Panel
                JPanel otherPanel = new JPanel();
                  otherPanel.setBorder(new TitledBorder(new EtchedBorder(), "Others"));
                  otherPanel.setPreferredSize(new Dimension(TEMPLATE_PANEL_WIDTH - 20, 60));
                  // Load JRadioButtons from Templates
                  for(String template : Templates.getOther()) {
                      JRadioButton button = new JRadioButton(template);
                      buttonGroup.add(button);
                      otherPanel.add(button);
                  }
                buttonPanel.add(otherPanel);

                // Input Panel
                JPanel inputPanel = new JPanel();
                  inputPanel.setLayout(new FlowLayout());
                  JButton confirmButton = new JButton("Confirm & Load Template");
                  confirmButton.addActionListener(new ActionListener() {
                      public void actionPerformed(ActionEvent e) {
                          Enumeration buttons = buttonGroup.getElements();
                          while (buttons.hasMoreElements()) {
                              AbstractButton button = (AbstractButton)buttons.nextElement();
                              if (button.isSelected()) {
                                  playButton.setSelected(false);
                                  Templates.loadTemplate(button.getText(),grid);
                                  canvas.repaint();
                              }
                          }
                          frame.dispose();
                      }
                  });
                  JButton cancelButton = new JButton("Cancel");
                  cancelButton.addActionListener(new ActionListener() {
                      public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                      }
                  });
                inputPanel.add(confirmButton);
                inputPanel.add(cancelButton);

                panel.add(scroller);
                panel.add(inputPanel);

                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);
            }
        }

        // Canvas
        canvas = new Canvas();
          canvas.setPreferredSize(new Dimension(CANVAS_INITIAL_WIDTH, CANVAS_INITIAL_HEIGHT));
          canvas.setBackground(Color.gray);
          canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
          canvas.addMouseMotionListener(new CanvasMouseMotionListener());
          canvas.addMouseListener( new CanvasMouseListener());
        add(canvas, BorderLayout.CENTER);

        // Menu bar
        menuBar = new JMenuBar();
          JMenu fileMenu = new JMenu("File");
          JMenuItem fileExitMenuItem = new JMenuItem("Exit");
          fileExitMenuItem.addActionListener(new FileExitMenuListener());
          fileMenu.add(fileExitMenuItem);
          menuBar.add(fileMenu);
          JMenu helpMenu = new JMenu("Help");
          JMenuItem helpAboutMenuItem = new JMenuItem("About");
          helpAboutMenuItem.addActionListener(new HelpAboutMenuListener());
          helpMenu.add(helpAboutMenuItem);
          menuBar.add(helpMenu);
        add(menuBar, BorderLayout.PAGE_START);

        // Control Panel
        controlPanel = new JPanel();
          controlPanel.setBorder(new TitledBorder(new EtchedBorder(), "Control Panel"));
          controlPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH, CANVAS_INITIAL_HEIGHT));
        add(controlPanel, BorderLayout.LINE_START);

        // Statistics Panel
        JPanel coordinatesPanel = new JPanel();
          coordinatesPanel.setBorder(new TitledBorder(new EtchedBorder(), "Statistics"));
          coordinatesPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 60));
          infoLabel = new JLabel("", SwingConstants.CENTER);
          coordinatesPanel.add(infoLabel);
        controlPanel.add(coordinatesPanel);

        // Grid Options Panel
        JPanel drawPanel = new JPanel();
          drawPanel.setBorder(new TitledBorder(new EtchedBorder(), "Grid Options"));
          drawPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 140));
          invertButton = new JCheckBox("Invert Colors");
          invertButton.addItemListener(new InvertButtonListener());
          infiniteButton = new JCheckBox("Infinite Grid  ");
          infiniteButton.addItemListener(new InfiniteButtonListener());
          scaleButton = new JButton("Change Scale");
          scaleButton.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 40, 50));
          scaleButton.addActionListener(new ScaleButtonListener());
          drawPanel.add(invertButton);
          drawPanel.add(infiniteButton);
          drawPanel.add(scaleButton);
        controlPanel.add(drawPanel);

        // Load Template Button
        templateButton = new JButton("Load Template");
          templateButton.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
          templateButton.addActionListener(new TemplateButtonListener());
        controlPanel.add(templateButton);

        // Reset Grid Button
        resetButton = new JButton("Reset Grid");
          resetButton.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
          resetButton.addActionListener(new ClearGridListener());
        controlPanel.add(resetButton);

        // Next Generation Button
        nextButton = new JButton("Next Generation");
          nextButton.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
          nextButton.addActionListener(new NextButtonListener());
        controlPanel.add(nextButton);

        // Play/Pause Button
        playButton = new JToggleButton("Play");
          playButton.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
          playButton.addItemListener(new PlayActionListener());
        controlPanel.add(playButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        setVisible(true);
    }
}

