package com.letter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Main{
    public static void main(String[] args) {
        new GUIProgram();
    }
}
class GUIProgram extends JPanel{
    JFrame frame;
    JPanel buttonPanel, bigPanel;
    JMenuBar menuBar;
    JButton n, s, e, w, reset;
    JMenu fonts, sizes, colors, backgrounds, outlines;
    JMenu[] menus;
    JButton[] buttons;
    JTextArea text;
    JMenuItem[] defaults;
    public GUIProgram() {
        frame = new JFrame("GUI Program");
        frame.add(this);
        frame.setSize(800,400);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        fonts = new JMenu("Font");
        sizes = new JMenu("Size");
        colors = new JMenu("Color");
        backgrounds = new JMenu("Background");
        outlines = new JMenu("Outline");
        reset = new JButton("Reset");

        n = new JButton("North");
        n.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.add(bigPanel,BorderLayout.NORTH);
                horizontal();
            }
        });
        s = new JButton("South");
        s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.add(bigPanel,BorderLayout.SOUTH);
                horizontal();
            }
        });
        e = new JButton("East");
        e.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.add(bigPanel,BorderLayout.EAST);
                vertical();
            }
        });
        w = new JButton("West");
        w.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.add(bigPanel,BorderLayout.WEST);
                vertical();
            }
        });

        defaults = new JMenuItem[5];
        menus = new JMenu[]{fonts, sizes, colors, backgrounds, outlines};
        buttons = new JButton[]{n, s, e, w, reset};

        String[] sFonts = {"Times New Roman","Arial","Consolas"};
        for (String s : sFonts) {
            JMenuItem a = new JMenuItem(s);
            a.setFont(new Font(s, Font.PLAIN, 12));
            a.addActionListener(e -> updateFonts(new Font(s,Font.PLAIN,12)));
            fonts.add(a);
            if(s.equals(sFonts[0]))
                defaults[0] = a;
        }
        int[] sSizes = {10,14,18,24};
        for (int i : sSizes) {
            JMenuItem a = new JMenuItem(i+"");
            a.setFont(fonts.getItem(0).getFont());
            a.addActionListener(e -> {
                text.setFont(text.getFont().deriveFont((float)i));
            });
            sizes.add(a);
            if(i==sSizes[0])
                defaults[1] = a;
        }
        Color[] sColors = {Color.GREEN, Color.BLUE, new Color((int)(Math.random() * 0x1000000))};
        for (Color s : sColors) {
            JMenuItem a = new JMenuItem(parseColor(s));
            a.addActionListener(e -> {
                text.setForeground(s);
            });
            colors.add(a);
            if(s.equals(sColors[0]))
                defaults[2] = a;
        }
        Color[] sBackgrounds = {Color.BLUE, Color.MAGENTA, new Color((int)(Math.random() * 0x1000000))};
        for (Color s : sBackgrounds) {
            JMenuItem a = new JMenuItem(parseColor(s));
            a.addActionListener(e -> {
                text.setBackground(s);
            });
            backgrounds.add(a);
            if(s.equals(sBackgrounds[0]))
                defaults[3] = a;
        }
        Color[] sOutlines = {Color.PINK,Color.GREEN, Color.MAGENTA, new Color((int)(Math.random() * 0x1000000))};
        for (Color s : sOutlines) {
            JMenuItem a = new JMenuItem(parseColor(s));
            a.addActionListener(e -> {
                for(JButton i : buttons)
                    i.setBorder(s==Color.PINK ? null : new LineBorder(s));
            });
            outlines.add(a);
            if(s.equals(sOutlines[0]))
                defaults[4] = a;
        }
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ActionEvent i = new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null);
                n.getActionListeners()[0].actionPerformed(i);
                for(JMenuItem m : defaults)
                    m.getActionListeners()[0].actionPerformed(i);
            }
        });
        
        text = new JTextArea();
        text.setBackground(sBackgrounds[0]);
        text.setForeground(sColors[0]);
        text.setFont(fonts.getItem(0).getFont().deriveFont(Float.parseFloat(defaults[1].getText())));

        menuBar = new JMenuBar();
        menuBar.setLayout(new GridLayout(1,6));
        buttonPanel = new JPanel(new GridLayout(1,4));
        bigPanel = new JPanel(new GridLayout(1,2));

        fillAll();
        updateFonts(fonts.getItem(0).getFont());

        frame.add(bigPanel,BorderLayout.NORTH);
        frame.add(text,BorderLayout.CENTER);

        frame.setVisible(true);
    }
    public String parseColor(Color s){
        if(s.equals(Color.BLUE))
            return "Blue";
        else if(s.equals(Color.GREEN))
            return "Green";
        else if(s.equals(Color.MAGENTA))
            return "Magenta";
        else if(s.equals(Color.PINK))
            return "No Outline";
        return "("+s.getRed()+", "+s.getGreen()+", "+s.getBlue()+")";
    }
    public void updateFonts(Font f){
        for(int i = 1; i<menus.length; i++) {
            menus[i].setFont(f);
            for (Component c : menus[i].getMenuComponents())
                c.setFont(f);
        }
        for(JButton i : buttons)
            i.setFont(f);
        text.setFont(f.deriveFont((float)text.getFont().getSize()));
    }
    public void horizontal(){
        buttonPanel.setLayout(new GridLayout(1,4));
        menuBar.setLayout(new GridLayout(1,6));
        bigPanel.setLayout(new GridLayout(1,2));
        fillAll();
    }
    public void vertical(){
        buttonPanel.setLayout(new GridLayout(4,1));
        menuBar.setLayout(new GridLayout(6,1));
        bigPanel.setLayout(new GridLayout(2,1));
        fillAll();
    }
    public void addButtons(){
        for(int i = 0; i<buttons.length-1; i++)
            buttonPanel.add(buttons[i]);
    }
    public void fillMenu(){
        for(JMenu i : menus)
            menuBar.add(i);
        menuBar.add(reset);
    }
    public void fillAll(){
        addButtons();
        fillMenu();
        bigPanel.add(buttonPanel);
        bigPanel.add(menuBar);
        frame.revalidate();
    }
}
