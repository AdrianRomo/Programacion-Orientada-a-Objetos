//Librerías
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class Game implements ActionListener {
    //Inicializador imagenes y colores
    private static final ImageIcon UP_MOLE = new ImageIcon("topo.jpg");
    private static final ImageIcon NO_MOLE = new ImageIcon("tapa.jpg");
    private static final ImageIcon DOWN_MOLE = new ImageIcon("topogolpe.jpg");
    private static final Color NO_COLOR = Color.LIGHT_GRAY;
    private static final Color UP_COLOR = Color.GREEN;
    private static final Color DOWN_COLOR = Color.RED;
    //Inicializador de Tiempo y Espacio
    private static int count = 30;
    private static int score;
    private JButton startButton;
    private JButton[] buttons;
    private JLabel timeLabel, scoreLabel, Empty;
    private JTextArea timeArea;
    private JTextArea scoreArea;
    private static Random random = new Random();
    
    //GUI
    public Game() {
        JFrame frame = new JFrame("Whack a Mole");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel pane = new JPanel();
        
        startButton = new JButton("Inicio");
        pane.add(startButton);
        startButton.addActionListener(this);
        
        timeLabel = new JLabel("Tiempo Restante:");
        pane.add(timeLabel);
        
        timeArea = new JTextArea(1, 5);
        timeArea.setEditable(false);
        pane.add(timeArea);
        timeArea.setVisible(true);
        
        scoreLabel = new JLabel("Puntuación:");
        pane.add(scoreLabel);

        scoreArea = new JTextArea(1, 5);
        scoreArea.setEditable(false);
        pane.add(scoreArea);
        Empty = new JLabel("                   ");
        pane.add(Empty);
        scoreArea.setVisible(true);
        
        buttons = new JButton[20];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(NO_MOLE);
            buttons[i].setOpaque(true);
            buttons[i].setBackground(NO_COLOR);
            pane.add(buttons[i]);
            buttons[i].addActionListener(this);
        }
        frame.setLocationRelativeTo(null);
        frame.setContentPane(pane);
        frame.setVisible(true);
        
    }
//Main
    public static void main(String[] args) {
        new Game();
    }
//
    public static class MoleThread extends Thread {
        JButton button;
        MoleThread(JButton button) {
            this.button = button;
            if (count > -1) {
                if (button.getIcon().equals(NO_MOLE)) {
                    button.setIcon(UP_MOLE);
                    button.setBackground(UP_COLOR);
                    
                } else {
                    button.setIcon(NO_MOLE);
                    button.setBackground(NO_COLOR);
                }
            }
        }
        //Corre Contador y sus diferentes casos
        public void run() {
            while (count > -1) {
                int randomSleepTime = random.nextInt(4000);
                synchronized (button) {
                    //Cambia a UP_MOLE luego de un tiempo de sleep
                    if (button.getIcon().equals(NO_MOLE)) {
                        button.setIcon(UP_MOLE);
                        button.setBackground(UP_COLOR);
                    } else {
                        button.setIcon(NO_MOLE);
                        button.setBackground(NO_COLOR);
                    }
                    
                }
                try {
                    Thread.sleep(randomSleepTime);
                } catch (InterruptedException e) {
                    throw new AssertionError(e);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    //Error en caso de interrupción*
                }
                
            }
            //Cambia a NO_MOLE si se acaba el tiempo
            if (count == -1) {
                button.setIcon(NO_MOLE);
                button.setBackground(NO_COLOR);
            }
            
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        Thread[] moleThread = new Thread[buttons.length];
        //Espera acción Inicio
        if (e.getSource() == startButton) {
            startButton.setEnabled(false);
            
            // Crea Tiempo, Inicia Tiempo, Crea arreglo con Topos, Inicia Juego
            Thread timerThread = new Thread(new Runnable() {
                
                @Override
                public void run() {
                    while (count > -1) {
                        
                        try {
                            
                            timeArea.setText("" + count);
                            count--;
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) { //Caso de error
                            ex.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace(); //Caso de error
                    }
                    //Inicia Contador y Score
                    count = 30;
                    score = 0;
                    timeArea.setText("" + count);
                    scoreArea.setText("" + score);
                    startButton.setEnabled(true);
                }
            });
            
            timerThread.start();
            //Crea Topos aleatorios
            for (int i = 0; i < moleThread.length; i++) {
                int randomMoleNum = random.nextInt(buttons.length);
                JButton button = buttons[randomMoleNum];
                moleThread[i] = new MoleThread(button);
                moleThread[i].start();
                
            }
        }
        //Contador de Score y baja Topos si le dió
        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {
                if (count > 0) {
                    if (buttons[i].getIcon().equals(UP_MOLE)) {
                        score++;
                        scoreArea.setText("" + score);
                        buttons[i].setIcon(DOWN_MOLE);
                        buttons[i].setBackground(DOWN_COLOR);
                        
                    }
                }
            }
        }
    }
}
