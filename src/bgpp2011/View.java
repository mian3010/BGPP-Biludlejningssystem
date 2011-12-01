package bgpp2011;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import java.util.*;

/**
 *
 * @author Michael Søby Andersen
 * @mail msoa@itu.dk
 * @date 26/11-2011
 * @time 10:23
 * 
 */
public abstract class View
{
    //Instance variables
    public JPanel contentPane = new JPanel();
    public String topText;
    public JLabel welcomeText;
    public JLabel copyrightText;
    public Canvas canvas;
    
    //Constructor
    public View(Canvas canvas)
    {
        this.canvas = canvas;
    }
    
    //Draw method
    public JPanel draw()
    {
        contentPane.setLayout(new BorderLayout());
        
        welcomeText = new JLabel(topText);
        welcomeText.setFont(new Font("Arial", Font.PLAIN, 24));
        welcomeText.setHorizontalAlignment(JLabel.CENTER);
        welcomeText.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        
        copyrightText = new JLabel("Copyright Toke Jensen, Magnus Stahl, Michael Søby Andersen, 2011");
        copyrightText.setFont(new Font("Arial", Font.ITALIC, 10));
        copyrightText.setHorizontalAlignment(JLabel.CENTER);
        
        contentPane.add(welcomeText, BorderLayout.NORTH);
        contentPane.add(copyrightText, BorderLayout.SOUTH);
        
        return contentPane;
    }
    public JTable createTable(String[] columnNames, Object[][] data, int[] columnSizes)
    {
        JTable table = new JTable(data, columnNames);
        for (int i = 0; i < columnSizes.length; i++)
        {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnSizes[i]);
        }
        return table;
    }
    public void addToTable(Object[] data, JTable table)
    {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.addRow(data);
    }
}
