package dbo.FirstExperiment;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import junit.framework.TestCase;

public class SlideshowPanelTest extends TestCase {

	public SlideshowPanelTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSlideshowPanel() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		JFrame frame = new JFrame();
		SlideshowPanel slideshow = new SlideshowPanel();
		
		frame.setTitle("Writer 1.0");
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		frame.setLocation(100, 100);
		frame.setSize(960, 540);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(slideshow, BorderLayout.CENTER);
		slideshow.setVisible(true);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					testSlideshowPanel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void testChangeImage() {
		fail("Not yet implemented");
	}

	public void testPlay() {
		fail("Not yet implemented");
	}

	public void testPause() {
		fail("Not yet implemented");
	}

}
