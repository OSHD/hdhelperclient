package com.hdhelper.frame.components.plugin;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PluginTableModel extends AbstractTableModel {

	private final List<Plugin> plugin_list;
	
	private String[] column_names = new String[] { 
			"Plugin name", "Enabled", "Configure", "Info", "Extra"
	};
	
	private final Class[] column_class = new Class[] {
	        String.class, Boolean.class, Object.class, Object.class, Object.class
	};
	
	public PluginTableModel(List<Plugin> plugin_list) {
        this.plugin_list = plugin_list;
    }
	
	@Override
    public String getColumnName(int column) {
        return column_names[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return column_class[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return column_names.length;
    }

    @Override
    public int getRowCount() {
        return plugin_list.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Plugin row = plugin_list.get(rowIndex);
        if(0 == columnIndex) {
            return row.getName();
        }
        else if(1 == columnIndex) {
            return row.getEnabled();
        }
        else if(2 == columnIndex) {
            return row.getConfigure();
        }
        else if(3 == columnIndex) {
            return row.getInfo();
        }
        else if(4 == columnIndex) {
            return row.getExtra();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)  {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	Plugin row = plugin_list.get(rowIndex);
        if(0 == columnIndex) {
            row.setName((String) aValue);
        }
        else if(1 == columnIndex) {
            row.setEnabled((Boolean) aValue);
        }
        else if(2 == columnIndex) {
            row.setConfigure((Object) aValue);
        }
        else if(3 == columnIndex) {
            row.setInfo((Object) aValue);
        }
        else if(4 == columnIndex) {
            row.setExtra((Object) aValue);
        }
    }
	
}
