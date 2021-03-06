package com.destroyordefend.project.utility;

import com.destroyordefend.project.Core.Game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ConsoleZoomUI {

    //Step1 - Declaring variables
    private JFrame myFrame;
    // Added by me
    private JPanel contentPane;
    private JPanel myPanel;
    private JLabel xLable=null;
    private JLabel yLable=null;
    private JLabel zoom = null;
    private JTextField xFiled=null;
    private JTextField yFiled=null;
    private JTextField zoomFiled = null;
    private JButton pauseBtn = null;
    private Color myColor=new Color(0, 0, 0);
    private Font myFont11=new Font("Tahoma", 1, 11);
    private Font myFont12bold=new Font("Tahoma", Font.BOLD, 12);
    private Font myFont11bold=new Font("Tahoma", Font.BOLD, 11);

    public void createComponents() {

        contentPane = new JPanel();
        contentPane.setOpaque(true);
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBorder(BorderFactory.createTitledBorder("My Program"));

        xLable=new JLabel("X");
        xLable.setLabelFor(xFiled);
        xLable.setFont(myFont11);
        xLable.setForeground(Color.white);

        zoom=new JLabel("Zoom");
        zoom.setLabelFor(xFiled);
        zoom.setFont(myFont11);
        zoom.setForeground(Color.white);

        pauseBtn = new JButton("Pause");
        yLable=new JLabel("Y");
        yLable.setLabelFor(yFiled);
        yLable.setFont(myFont11);
        yLable.setForeground(Color.white);


        xFiled=new JTextField(10);
        xFiled.setBorder(new LineBorder(null, 0, false));
        zoomFiled=new JTextField(10);
        zoomFiled.setBorder(new LineBorder(null, 0, false));
        yFiled=new JTextField(10);
        yFiled.setBorder(new LineBorder(null, 0, false));

        //Panel
        myPanel=new JPanel();
        myPanel.setOpaque(true);
        myPanel.setBorder(BorderFactory.createTitledBorder("Zoom at"));
        myPanel.setBackground(myColor);
        myPanel.setLayout(new GridLayout(4, 2, 2, 2));
        myPanel.add(xLable);
        myPanel.add(xFiled);
        myPanel.add(yLable);
        myPanel.add(yFiled);
        myPanel.add(zoom);
        myPanel.add(zoomFiled);
        myPanel.add(pauseBtn);
        //----------------------------------------------------------
        contentPane.add(myPanel);

        myFrame=new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //myFrame.setPreferredSize(new Dimension(400,300));//width:400px, height:300px
        myFrame.setLocationRelativeTo(null);//to show at center of screen
        myFrame.setTitle("DoD Game");
        //myFrame.add(myPanel);
        myFrame.setContentPane(contentPane);
        myFrame.pack();//this alone will not give the frame a size
        myFrame.setVisible(true);

        zoomFiled.addActionListener(e -> {
                Log.initZoom(Integer.parseInt(xFiled.getText()), Integer.parseInt(yFiled.getText()), Integer.parseInt(zoomFiled.getText()));
            });
        pauseBtn.addActionListener(e -> {

            Game.getGame().setGameState(pauseBtn.getText() == "Pause"?"Paused":"Running");
               pauseBtn.setText(pauseBtn.getText() =="Pause"?"Resume":"Pause");
               if(Game.getGame().getGameStateName().equals("Paused")){

                       Game.getGame().pause();
               }else{
                   Game.getGame().resume();

               }
            System.out.println(Game.getGame().getGameStateName());

        });
    }
}
