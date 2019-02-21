package dbo.Viewer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MarkerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
	JTable table;
	JPanel input, inputTop, inputBottom;
	JButton add, delete, sync, save, load, sort;
	DefaultTableModel markers;
	String[] columnNames = { "timestamp", "description" };
	File file = new File("markers.tsv");

	MarkerPanel(File f) {
		this();
		file = f;
	}
	
	MarkerPanel() {
		/* initialize and configure components and variables */
		super(new BorderLayout());
		input = new JPanel(new BorderLayout());
		inputTop = new JPanel();
		inputBottom = new JPanel();
		markers = new DefaultTableModel(columnNames, 0);
//		markers.addRow(new Object[] { 123L, "onetwothree" });
//		table = new JTable(new Object[][] {{1, "aa"}, {2, "bb"}, {3, "bb"}, {4, "bb"}, {5, "bb"}, {7, "bb"}, {6, "bb"}, {8, "bb"}, {9, "bb"}, {10, "bb"}, {11, "bb"}, {12, "bb"}, {2, "bb"}}, new Object[] {"one", "two"});
		table = new JTable(markers);
		scrollPane = new JScrollPane(table);
		scrollPane.setVisible(true);
		table.setFillsViewportHeight(true);

		/* configure input */
		add = new JButton("add");
		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				long timestamp = getCurrentTimestamp();

//				UIManager.put("OptionPane.minimumSize", new Dimension(500, 500));
				String option = (String) JOptionPane.showInputDialog(null, "Timestamp: " + timestamp, "Add New Marker",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (option != null) {
					addMarker(timestamp, option);
				}

			}
		});
		delete = new JButton("delete");
		delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) { // TODO: find a better way of deleting multiples (currently,
															// a
															// repaint is
															// made at every entry delete and non consecutive selections
															// dont work)
				try {
					int row = table.getSelectedRow();
					for (int i = table.getSelectedRowCount(); i > 0; i--) {
						deleteMarker(row);
					}
				} catch (Exception e) {

				}
			}

		});
		sync = new JButton("sync");
		sync.setEnabled(false);
		sort = new JButton("sort");
		sort.setEnabled(false);
		save = new JButton("save");
		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					saveFile(file);
				} catch (IOException e) {
				}
			}
			
		});
		load = new JButton("load");
		load.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					loadFile(file);
				} catch (IOException e) {
				}
			}
			
		});

		/* add the components */
		inputTop.add(add);
		inputTop.add(delete);
		inputTop.add(sync);
		inputBottom.add(sort);
		inputBottom.add(save);
		inputBottom.add(load);
		input.add(inputTop, BorderLayout.NORTH);
		input.add(inputBottom, BorderLayout.SOUTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(input, BorderLayout.SOUTH);
	}

	public boolean addMarker(long timestamp, String text) {
		markers.addRow(new Object[] { timestamp, text });

		return true;
	}

	public boolean deleteMarker(int row) {
		markers.getDataVector().remove(row);
		markers.fireTableDataChanged();

		return true;
	}
	
	public void saveFile(File f) throws IOException {
		f.delete();
		//f.getParentFile().mkdirs(); 
		f.createNewFile();
		
		PrintWriter pw = new PrintWriter(new FileWriter(f), true);
		
		pw.print("timestamp\tdescription");
		
		@SuppressWarnings("unchecked")
		Vector<Vector<Object>> values = (Vector<Vector<Object>>) markers.getDataVector();
		int cols = markers.getColumnCount();
		
		for (Vector<Object> line : values) {
			pw.print("\n" + line.elementAt(0));
			for(int i = 1; i < cols; i++) {
				pw.print("\t" + line.elementAt(i));
			}
		}
		
		pw.close();
	}
	
	public void loadFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		boolean loop = true;
		String fullLine;
		String[] splitLine;
		
		/* loads header */
		fullLine = br.readLine();
		splitLine = fullLine.split("\t");
		markers = new DefaultTableModel(splitLine, 0);
		table.setModel(markers);
		
		/* loads data */
		while (loop) {
			fullLine = br.readLine();
			if (fullLine == null) {
				loop = false;
			} else {
				splitLine = fullLine.split("\t");

				markers.addRow(splitLine);
			}
		}
		
		br.close();
		
		markers.fireTableDataChanged();
	}

	/**
	 * Meant to be overriden.
	 * 
	 * @return the current time stamp
	 */
	public long getCurrentTimestamp() {
		return 0;
	}
}
