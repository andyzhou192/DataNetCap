package test.util.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;
 
//学生评教
 
public class Judge {
 
    static JPanel jj;
 
    public JPanel judge_panel(){
        jj = new JPanel();
        jj.setBounds(0, 0, 1000, 650);
        jj.setBackground(Color.blue);
        jj.setLayout(null);
        pop();
        return jj;
    }
 
    public void pop(){
        String[] tableTitleArray = {"ID","Name","Sex"};
       Object[][] body = new Object[6][tableTitleArray.length];
       for(int i = 0; i < 6;i++){
           body[i][0] = i;
           body[i][1] = "张三"+i;
           body[i][2] = "男";
       }
       final JTable table = new JTable(new DefaultTableModel(body,tableTitleArray));
       final MouseInputListener mouseInputListener = getMouseInputListener(table);//添加鼠标右键选择行
       table.addMouseListener(mouseInputListener);
       table.addMouseMotionListener(mouseInputListener);
       JScrollPane scrollPane = new JScrollPane(); 
       scrollPane.setViewportView(table);
       scrollPane.setSize(1000, 500);
       jj.add(scrollPane, BorderLayout.CENTER);
 
    }
 
    private MouseInputListener getMouseInputListener(final JTable jTable) {  
        return new MouseInputListener() {  
            public void mouseClicked(MouseEvent e) {  
                processEvent(e);  
            }  
 
            /*** 
             * //in order to trigger Left-click the event 
             */  
            public void mousePressed(MouseEvent e) {  
                processEvent(e);// is necessary!!!  
            }  
 
            public void mouseReleased(MouseEvent e) {  
                // processEvent(e);  
//                System.out.println(); 
                System.out.println(jTable.getValueAt(jTable.getSelectedRow(),jTable.getSelectedColumn()));
 
                if (e.getButton() == MouseEvent.BUTTON3) {// right click  
 
                    JPopupMenu popupmenu = new JPopupMenu();  
                    JMenuItem runM = new JMenuItem("111");  
                    JMenuItem copyParameterM = new JMenuItem("222");  
                    JMenuItem copyResponseM = new JMenuItem("333");  
//                  JMenuItem encodingM = new JMenuItem(ACTION_COMMAND_ENCODING);  
                    // JMenuItem editM=new JMenuItem("edit");  
                    MyMenuActionListener yMenuActionListener = new MyMenuActionListener();  
                    runM.addActionListener(yMenuActionListener);  
                    copyParameterM.addActionListener(yMenuActionListener);  
                    copyResponseM.addActionListener(yMenuActionListener);  
//                  encodingM.addActionListener(yMenuActionListener);  
                    popupmenu.add(runM);  
                    popupmenu.add(copyParameterM);  
                    popupmenu.add(copyResponseM);  
//                  popupmenu.add(encodingM);  
                    popupmenu.show(e.getComponent(), e.getX(), e.getY());  
                }  
            }  
 
            public void mouseEntered(MouseEvent e) {  
                processEvent(e);  
            }  
 
            public void mouseExited(MouseEvent e) {  
                processEvent(e);  
            }  
 
            public void mouseDragged(MouseEvent e) {  
                processEvent(e);  
            }  
 
            public void mouseMoved(MouseEvent e) {  
                processEvent(e);  
            }  
 
            private void processEvent(MouseEvent e) {  
                // Right-click on  
//                if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {  
//                    // System.out.println(e.getModifiers());  
////                     System.out.println("Right-click on");  
//                    
//                    int modifiers = e.getModifiers();  
//                    modifiers -= MouseEvent.BUTTON3_MASK;  
//                    modifiers |= MouseEvent.BUTTON1_MASK;  
//                    MouseEvent ne = new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), modifiers, e.getX(), e.getY(),   e.getClickCount(), false);  
//                    jTable.dispatchEvent(ne);// in order to trigger Left-click  
//                    
//                    // the event  
//                }  
            }  
        };  
    }  
    class MyMenuActionListener implements ActionListener {
 
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
 
        }  }
 
 public static void main(String[] args) {
	 JFrame frame = new JFrame();
	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 frame.setSize(600, 600);
	 frame.setVisible(true);
	 frame.add((new Judge()).judge_panel());
}
 
}
