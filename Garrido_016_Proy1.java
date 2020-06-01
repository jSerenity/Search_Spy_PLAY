import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.*;
 
 

public class Garrido_016_Proy1 extends JFrame {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private int intentos = 0;
    private static String[] presentation = {"Universidad Tecnologica de Panama", "Facultad de Ing. de Sistemas Computacionales", "Carrera: Desarrollo de Software", "Profesor: Ricardo Chan","Estudiante: Alba Garrido","Cedula: 7-709-2016","Grupo: ILS222","Fecha: 31 de Mayo de 2020"};
    Spy objSpy = new Spy();
    JButton NORTE = new JButton("NORTE");
    JButton SUR = new JButton("SUR");
    JButton ESTE = new JButton("ESTE");
    JButton OESTE = new JButton("OESTE");
    JButton verSpy;
    private JLabel spyLabel = new JLabel(); 
    private JLabel spyIntentos = new JLabel("intentos: 0"); 
    boolean DisplaySpy = false;

    public Garrido_016_Proy1() {
        super("Search SPY...");
        initComponents();
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
 
    protected void initComponents() {
        

        final JPanel panelButton = new JPanel();
        final JPanel panelMandos = new JPanel(new FlowLayout());
        final JPanel panelpresen = new JPanel();
        final JPanel panelInfo = new JPanel();
        panelInfo.setBackground(Color.LIGHT_GRAY);
        panelInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelInfo.setLayout(new GridLayout(40,1));
        panelInfo.add(spyIntentos);
        objSpy.SetX(SpyPositionX());
        objSpy.SetY(SpyPositionY());

        JButton Start = new JButton("Iniciar");
        Start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                InciarJuego();
            }
        });
        verSpy = new JButton("Ver Espia");
        verSpy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VerSpy(e.getSource());
            }
        });

        panelMandos.add(Start,BorderLayout.WEST);
        panelMandos.add(verSpy,BorderLayout.EAST);
        panelMandos.add(spyLabel, BorderLayout.SOUTH);
        panelMandos.setBorder(new EmptyBorder(10, 10, 10, 10));
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchSpy(e.getSource());
            }
        };

        
        panelButton.setLayout(new GridLayout(11, 11));
        for(int y=ROWS; y>=0; y--)
         {   
            final JLabel labelY = new JLabel(); 
            final var text= (y==0)?"":""+y; 
            labelY.setText(text); 
            labelY.setForeground(Color.RED);
            panelButton.add(labelY);

            for(int x=1; x<=COLS; x++){
                if(y==0)
                {
                    final JLabel labelx = new JLabel(); 
                    labelx.setText(" "+x); 
                    labelx.setForeground(Color.blue);
                    panelButton.add(labelx);
                }else{
                    XYBotones button = new XYBotones("",y,x);
                    button.addActionListener(buttonListener);           
                    panelButton.add(button);
                    panelButton.updateUI();
                }
                
            }
        }
                


        panelpresen.setLayout(new GridLayout(20,2));
        panelpresen.setAlignmentX(Component.CENTER_ALIGNMENT); 
        panelpresen.setBackground(Color.LIGHT_GRAY);
        panelpresen.setBorder(new EmptyBorder(10, 10, 10, 10));
        for(final String text: presentation)
        {
            final JLabel label = new JLabel(); 
            label.setText(text);        
            panelpresen.add(label);
        }
        panelMandos.setBackground(Color.LIGHT_GRAY);
        
        add(panelMandos, BorderLayout.NORTH);
        add(panelpresen, BorderLayout.WEST);
        JPanel p = new JPanel(new BorderLayout()); 
        propertyButton();
        p.add(NORTE, BorderLayout.NORTH); 
        p.add(SUR, BorderLayout.SOUTH); 
        p.add(ESTE, BorderLayout.EAST); 
        p.add(OESTE, BorderLayout.WEST); 
        p.add(panelButton, BorderLayout.CENTER); 
        add(p,BorderLayout.CENTER);
        add(panelInfo,BorderLayout.EAST);
    }
    public int SpyPositionX(){
        int x=0;
        x = (int)(Math.random()*9 + 1);
        return x;
    }
    public int SpyPositionY(){
        int y=0;
        y = (int)(Math.random()*9 + 1);
        return y;
    }

    public void InciarJuego(){
        resetColor(null);
        intentos=0;
        spyIntentos.setText("intentos: "+intentos);
        panico();
        DisplaySpy= false;
        verSpy.setText("Ver Espia");
        spyLabel.setVisible(false);
    }
    public void panico(){
        objSpy.SetX(SpyPositionX());
        objSpy.SetY(SpyPositionY());
        if(DisplaySpy)
        {
            spyLabel.setText("Posicion del espia: X="+objSpy.GetX()+" Y="+objSpy.GetY());
        }       
    }
    public void VerSpy(Object b){
        DisplaySpy= !DisplaySpy;
        var c = (JButton)b;
        var text = !DisplaySpy?"Ver Espia":"Ocultar Espia";
        c.setText(text);
        spyLabel.setText("Posicion del espia: X="+objSpy.GetX()+" Y="+objSpy.GetY());
        spyLabel.setVisible(DisplaySpy);
    }

    public void searchSpy(Object b){
        var bx= ((XYBotones) b).getRow();
        var by = ((XYBotones) b).getCol();
        var sx = objSpy.GetX();
        var sy = objSpy.GetY();
        intentos++;
        spyIntentos.setText("intentos: "+intentos);
    
        if(bx == sx && by == sy){
            resetColor(Color.green);
            JOptionPane.showMessageDialog(null, "Espia encontrado, depues de: "+intentos+" intentos, \nSe inicia otro juego");   
            InciarJuego();        
        }else if(distancia(bx, by)==1){
            panico();
            JOptionPane.showMessageDialog(null, "El espia entra en panico y se ha movido"); 
        }else{
            direccion(bx,by);
         
        }
    }

    public int distancia(int bx, int by){
        int dist=0;
        var sx = objSpy.GetX();
        var sy = objSpy.GetY();
         var x= (int) Math.pow((bx-sx), 2);
         var y= (int) Math.pow((by-sy), 2); 
         dist =(int)Math.sqrt((x+y));
        return dist;
    }

    public String direccion(int bx, int by){
        String result ="";
        var sx = objSpy.GetX();
        var sy = objSpy.GetY();
        var x = sx-bx;
        var y = sy-by;
        System.out.println("x: "+x+"y: "+y);
        resetColor(null);
        if(Math.abs(x)>Math.abs(y)){
            if(x>0){
                result= "muevete al ESTE";
                ESTE.setForeground(Color.green);
            }else{
                result= "muevete al OESTE";
                OESTE.setForeground(Color.green);
            }
        }else{
            if(y>0){
                result= "muevete al NORTE";
                NORTE.setForeground(Color.green);
            }else{
                result= "muevete al SUR";
                SUR.setForeground(Color.green);
            }
        }
        return result;
    }
    public void resetColor(Color value){
        SUR.setForeground(value);
        NORTE.setForeground(value);
        OESTE.setForeground(value);
        ESTE.setForeground(value);
    }
    public void propertyButton(){
        NORTE.setContentAreaFilled(false);
        NORTE.setOpaque(true);
        NORTE.setPreferredSize(new Dimension(100, 60));
        NORTE.setBorderPainted(false);
        SUR.setContentAreaFilled(false);
        SUR.setOpaque(true);
        SUR.setBorderPainted(false);
        SUR.setPreferredSize(new Dimension(100, 60));
        ESTE.setContentAreaFilled(false);
        ESTE.setOpaque(true);
        ESTE.setBorderPainted(false);
        OESTE.setContentAreaFilled(false);
        OESTE.setOpaque(true);
        OESTE.setBorderPainted(false);
    }
}
