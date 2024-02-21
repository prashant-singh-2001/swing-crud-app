package com.swingcrud;

import java.sql.*;
import java.util.*;
import javax.swing.table.*;

public class DbUtils {

	public static TableModel resultSetToTableModel(ResultSet rs) {
	    try {
	        ResultSetMetaData mD = rs.getMetaData();
	        int cols = mD.getColumnCount();
	        Vector colNames = new Vector();

	        // Start loop from 1 instead of 0
	        for (int i = 1; i <= cols; i++) {
	            colNames.addElement(mD.getColumnLabel(i));
	        }

	        Vector r = new Vector();
	        while (rs.next()) {
	            Vector nr = new Vector();
	            // Start loop from 1 instead of 0
	            for (int i = 1; i <= cols; i++) {
	                nr.addElement(rs.getObject(i));
	            }
	            r.addElement(nr);
	        }

	        return new DefaultTableModel(r, colNames);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return new DefaultTableModel();
	}
}
