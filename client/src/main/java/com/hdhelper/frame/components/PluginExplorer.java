package com.hdhelper.frame.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.hdhelper.frame.components.plugin.Plugin;
import com.hdhelper.frame.components.plugin.PluginButtonEditor;
import com.hdhelper.frame.components.plugin.PluginButtonRenderer;
import com.hdhelper.frame.components.plugin.PluginTableModel;

public class PluginExplorer extends JFrame {

	public PluginExplorer() {

		Plugin row1 = new Plugin("Name", false, "Configure", "Info", "Extra");
		Plugin row2 = new Plugin("Name", false, "Configure", "Info", "Extra");
		Plugin row3 = new Plugin("Name", false, "Configure", "Info", "Extra");

		//build the list
		List<Plugin> employeeList = new ArrayList<Plugin>();
		employeeList.add(row1);
		employeeList.add(row2);
		employeeList.add(row3);

		//create the model
		PluginTableModel model = new PluginTableModel(employeeList);
		//create the table
		JTable table = new JTable(model);
		
		table.getColumn("Configure").setCellRenderer(new PluginButtonRenderer());
		
		table.getColumn("Configure").setCellEditor(new PluginButtonEditor(new JCheckBox()));
		
		table.getColumn("Info").setCellRenderer(new PluginButtonRenderer());
		
		table.getColumn("Info").setCellEditor(new PluginButtonEditor(new JCheckBox()));
		
		table.getColumn("Extra").setCellRenderer(new PluginButtonRenderer());
		
		table.getColumn("Extra").setCellEditor(new PluginButtonEditor(new JCheckBox()));
		
		//add the table to the frame
		this.add(new JScrollPane(table));
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);        
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PluginExplorer();
            }
        });
    }  
	
}
