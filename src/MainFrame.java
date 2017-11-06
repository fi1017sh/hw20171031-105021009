import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.TreeMap;

public class MainFrame extends JFrame{
    private int scrW = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int scrH = Toolkit.getDefaultToolkit().getScreenSize().height;
    private int frmW = 500, frmH = 450;
    private JDesktopPane jdp = new JDesktopPane();
    private JMenuBar jmb = new JMenuBar();
    private JMenu jmF = new JMenu("File");
    private JMenu jmS = new JMenu("Set");
    private JMenu jmG = new JMenu("Game");
    private JMenu jmA = new JMenu("About");
    private JMenuItem jmiExit = new JMenuItem("Exit");
    private JMenuItem jmiGame = new JMenuItem("Loto game");
    private JMenuItem jminum = new JMenuItem("Number");
    private JInternalFrame jif = new JInternalFrame();
    private JInternalFrame jifnum = new JInternalFrame();

    ///////////////////////////////////樂透視窗///////////////////////////////////
    private Container jifcp;
    private JPanel jpl = new JPanel(new GridLayout(1,6,5,5));
    private JPanel jpnl = new JPanel(new GridLayout(1,2,5,5));
    private JButton jbcls = new JButton("Close");
    private JButton jbgen = new JButton("Generate");
    private JLabel jlab[] = new JLabel[6];
    private int data[] = new int[6];
    private Random rnd = new Random(System.currentTimeMillis());


    ///////////////////////////////亂數計算機///////////////////////////////////////
    private Container jifnumcp;
    private JTextField jtf = new JTextField();
    private JPanel jpn = new JPanel(new GridLayout(4,3));
    private JButton jbs[] = new JButton[10];
    private JButton jbdelete = new JButton("Delete");
    private JButton jbdot = new JButton(".");
    private int ran;
    private Random random = new Random();
    private int[] arr = new int[10];

    /////////////////////////////更改使用者視窗////////////////////////////////////
    private JPanel jpl2 = new JPanel(new GridLayout(2,3,5,5));
    private JMenuItem jmifont = new JMenuItem("Font");
    private JLabel jlbfamily = new JLabel("Family");
    private JLabel jlbstyle = new JLabel("Style");
    private JLabel jlbsize = new JLabel("Size");
    private JTextField jtffamily = new JTextField("Time New Romans");
    private JTextField jtfsize = new JTextField("24");
    private String[] str = {"PLAIN","BOLD","ITALIC","BOLD+ITALIC"};
    private JComboBox jcb = new JComboBox(str);

    ////////////////////////////內部檔案讀取器///////////////////////////////////
    private JMenuItem jmiBook = new JMenuItem("Book");
    private JInternalFrame jifrea = new JInternalFrame();
    private Container jifreacp;
    private JMenuBar jifjmb = new JMenuBar();
    private JMenu jmudata = new JMenu("Data");
    private JMenuItem jmiload = new JMenuItem("Load");
    private JMenuItem jminew = new JMenuItem("New");
    private JMenuItem jmiclose = new JMenuItem("Close");
    private JFileChooser jfc = new JFileChooser();

    ///////////////////////////////連結LoginFrame///////////////////////////////
    private LoginFrame loginframe;
    public MainFrame(LoginFrame loginfr){
        loginframe = loginfr;
        initComp();
    }


    ////////////////////////////////表格//////////////////////////////////////
    private String[] heading = {"書名","作者","出版社","價格","類別"};
    private int number;
    private String[][] action = new String[30][5];
    private JTable jtb = new JTable(action, heading);
    private String [] Items = new String[5];
    private JScrollPane jsp = new JScrollPane(jtb);


    private void initComp(){
        this.setBounds(scrW/2-frmW/2,scrH/2-frmH/2,frmW,frmH);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                loginframe.reset();
                loginframe.setVisible(true);
            }
        });
        this.setJMenuBar(jmb);
        this.setContentPane(jdp);
        jif.setBounds(0,0,200,80);

        //////////////////////////主視窗上排按鍵///////////////////////////
        jmb.add(jmF);
        jmb.add(jmS);
        jmb.add(jmG);
        jmb.add(jmA);
        jmF.add(jmiBook);
        jmF.add(jmiExit);
        jmG.add(jmiGame);
        jmG.add(jminum);

        jmiExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
                loginframe.reset();
                loginframe.setVisible(true);
            }
        });

        jmiExit.setAccelerator(KeyStroke.getKeyStroke('X',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        jmiGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jdp.add(jif);
                jif.setVisible(true);
                lotoGenerate();
                jifnum.dispose();
                jifrea.dispose();
            }
        });
        jmiGame.setAccelerator(KeyStroke.getKeyStroke('L',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        jminum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jdp.add(jifnum);
                jifnum.setVisible(true);
                jif.dispose();
                jifrea.dispose();
            }
        });
        jminum.setAccelerator(KeyStroke.getKeyStroke('N',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        jmiBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jdp.add(jifrea);
                jifrea.setVisible(true);
                jif.dispose();
                jifnum.dispose();
            }
        });

        ////////////////////////////樂透//////////////////////////////////
        jifnum.setBounds(0,0,300,400);
        jifcp = jif.getContentPane();
        jifcp.add(jpl, BorderLayout.CENTER);
        jifcp.add(jpnl, BorderLayout.SOUTH);
        jpnl.add(jbcls);
        jpnl.add(jbgen);

        for(int i=0; i<6; i++){
            jlab[i] = new JLabel(Integer.toString(data[i]));
            jpl.add(jlab[i]);
        }


        jbcls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jif.dispose();
            }
        });

        jbgen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lotoGenerate();
            }
        });

        ///////////////////////////////亂數計算機///////////////////////////////
        jifnumcp = jifnum.getContentPane();
        jifnumcp.setLayout(new BorderLayout(5,5));
        jifnumcp.add(jtf, BorderLayout.NORTH);
        jtf.setEditable(false);
        jifnumcp.add(jpn, BorderLayout.CENTER);

        for(int j=0; j<10; j++)
            arr[j] = j;

        for (int j = 0; j < 10; j++) {
            ran = random.nextInt(10);
            int tmp = arr[ran];
            arr[ran] = arr[j];
            arr[j] = tmp;
        }

        for(int i =0; i<10 ;i++) {

            jbs[i] = new JButton(Integer.toString(arr[i]));
            jpn.add(jbs[i]);
            jbs[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton tempbtn = (JButton) e.getSource();
                    jtf.setText(jtf.getText() + tempbtn.getText());
                }
            });
        }

        jpn.add(jbdot);
        jbdot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtf.setText(jtf.getText()+".");
            }
        });
        jpn.add(jbdelete);
        jbdelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtf.setText("");
            }
        });

        //////////////////////更改使用者視窗/////////////////////////
        jmS.add(jmifont);
        jpl2.add(jlbfamily);
        jpl2.add(jlbstyle);
        jpl2.add(jlbsize);
        jpl2.add(jtffamily);
        jpl2.add(jcb);
        jpl2.add(jtfsize);

        jmifont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(MainFrame.this, jpl2,"Font Setting",JOptionPane.OK_CANCEL_OPTION);
                int fontStyle=0;
                switch (jcb.getSelectedIndex()){
                    case 0: fontStyle = Font.PLAIN;
                        break;
                    case 1: fontStyle = Font.BOLD;
                        break;
                    case 2: fontStyle = Font.ITALIC;
                        break;
                    case 3: fontStyle = Font.BOLD+Font.ITALIC;
                }
                if(result == JOptionPane.OK_OPTION){
                    UIManager.put("Menu.font",new Font(jtffamily.getText(),fontStyle,Integer.parseInt(jtfsize.getText())));
                    UIManager.put("MenuItem.font",new Font(jtffamily.getText(),fontStyle,Integer.parseInt(jtfsize.getText())));
                }
            }
        });

        //////////////////////////////檔案讀取器///////////////////////////////////
        jifreacp = jifrea.getContentPane();
        jifreacp.setLayout(new BorderLayout(5,5));
        jifreacp.add(jsp, BorderLayout.CENTER);
        jifrea.setJMenuBar(jifjmb);
        jifrea.setBounds(0,0,400,350);
        jifrea.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jifjmb.add(jmudata);
        jmudata.add(jmiload);
        jmudata.add(jminew);
        jmudata.add(jmiclose);

        jmiload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        File inFile = jfc.getSelectedFile();
                        BufferedReader br = new BufferedReader(new FileReader(inFile));
                        String str1 = "";
                        number = 0;
                        while ((str1 = br.readLine()) != null){
                            number++;
                            Items = str1.split(",");
                            for(int j=0; j<5; j++)
                                action[number-1][j] = Items[j];
                        }
                    }catch (Exception ioe)
                    {
                        JOptionPane.showMessageDialog(null,"Open file error: "+ ioe.toString());
                    }
                }
            }
        });


        jminew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] action1 = new String[100][5];
                for(int j=0; j<5; j++)
                    action1[number][j]="";
                jtb = new JTable(action1,heading);
            }
        });

        jmiclose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jifrea.dispose();
            }
        });


    }

    /////////////////////////函式：樂透亂數/////////////////////////////
    private void lotoGenerate(){
        int i=0;
        while (i<6){
            boolean flag = true;
            data[i]=rnd.nextInt(42)+1;
            int j=0;
            while(j<i && flag){
                if(data[i] == data[j]){
                    flag = false;
                }
                j++;
            }
            if(flag){
                jlab[i].setText((Integer.toString(data[i])));
                i++;
            }
        }
    }
}