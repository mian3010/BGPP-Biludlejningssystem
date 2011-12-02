package bgpp2011;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.*;

/**
 *
 * @author Michael Søby Andersen
 * @mail msoa@itu.dk
 * @date 26/11-2011
 * @time 10:23
 * 
 */
public abstract class View implements TableModelListener
{
    //Instance variables
    public JPanel contentPane = new JPanel();
    public String topText;
    public JLabel welcomeText;
    public JLabel copyrightText;
    public Canvas canvas;
    private JTable table;
    
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
        table.setModel(new TableModel(data,columnNames));
        table.getModel().addTableModelListener(this);
        for (int i = 0; i < columnSizes.length; i++)
        {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnSizes[i]);
        }
        this.table = table;
        return table;
    }
    public void addToTable(JTable table)
    {
        TableModel model = (TableModel)table.getModel();
        model.addRow();
    }
    public void removeFromTable(int rowID, JTable table)
    {
    	TableModel model = (TableModel)table.getModel();
    	model.removeRow(rowID);
    }
    public void tableChanged(TableModelEvent e)
    {
    	int row = e.getFirstRow();
    	int column = e.getColumn();
    	if (column == 5)
    	{
    		int response = JOptionPane.showConfirmDialog(canvas.getFrame(), "Are you sure you want to remove this customer?", "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    		if (response == 0)
    			removeFromTable(row, table);
    	}
    }
}
