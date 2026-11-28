package main.gui.panels.main.map;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import main.comm.asnwer.MapBorderPointsAnswer;
import main.comm.asnwer.MapStopDataAnswer;
import main.comm.request.MapBorderPointsRequest;
import main.comm.request.MapStopDataRequest;
import main.gui.RequestSender;
import main.gui.Window;
import main.gui.config.theme.Theme;
import main.gui.panels.util.VisibleJLabel;

public class MapCanvas extends JPanel {
	private class StopNamePopup extends JPopupMenu {
		public StopNamePopup(String stopName) {
			Theme theme = Window.getConfig().theme;
			
			super();
			this.setOpaque(false);
			this.setBorder(new EmptyBorder(0, 0, 0, 0));
			this.setLayout(new BorderLayout());
			JPanel stopNamePanel = getStopNamePanel(stopName);
			this.add(stopNamePanel, BorderLayout.CENTER);
		}
		
		public void show(Component invoker, Point center, int r) {
			Point pos = calculatePosition(center, r);
			this.show(invoker, pos.x, pos.y);
		}
		
		private Point calculatePosition(Point center, int r) {
			int x = (int)(center.x + r*0.2);
			int y = (int)(center.y + r*2);
			return new Point(x, y);
		}
		
		private JPanel getStopNamePanel(String stopName) {
			Theme theme = Window.getConfig().theme;
			
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBackground(theme.baseColor);
			panel.setBorder(BorderFactory.createLineBorder(theme.secondaryColor, 2));
			JLabel label = new VisibleJLabel(stopName);
			panel.add(label, BorderLayout.CENTER);
			
			return panel;
		}
		
	}
	
	private int circleRad = 5;
	private int borderStrokeWidth = 1;
	
	private Rectangle boundCoordinates;
	private Dimension screenSize;

	// Added variables for drag and zoom
	private Point lastMousePos;
	private double exactX, exactY, exactWidth, exactHeight;
	private static final double ZOOM_FACTOR = 1.15;
	
	private StopNamePopup popup;
	private MouseMotionListener stopPopupListener;
	
	public MapCanvas() {
		boundCoordinates = new Rectangle(161138, 485853, 67839, 28482);
		exactX = boundCoordinates.x;
		exactY = boundCoordinates.y;
		exactWidth = boundCoordinates.width;
		exactHeight = boundCoordinates.height;

		initDrivers();
	}

	private void initDrivers() {
		MouseAdapter mouseHandler = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lastMousePos = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				lastMousePos = null;
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (lastMousePos == null || getWidth() == 0 || getHeight() == 0) return;

				double ratioX = exactWidth / getWidth();
				double ratioY = exactHeight / getHeight();

				exactX -= (e.getX() - lastMousePos.x) * ratioX;
				exactY += (e.getY() - lastMousePos.y) * ratioY;

				updateBounds();
				lastMousePos = e.getPoint();
				repaint();
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (getWidth() == 0 || getHeight() == 0) return;

				double relX = (double) e.getX() / getWidth();
				double relY = (double) e.getY() / getHeight();

				double anchorX = exactX + relX * exactWidth;
				double anchorY = exactY - relY * exactHeight;

				double scale = (e.getWheelRotation() < 0) ? (1.0 / ZOOM_FACTOR) : ZOOM_FACTOR;
				exactWidth *= scale;
				exactHeight *= scale;

				exactX = anchorX - relX * exactWidth;
				exactY = anchorY + relY * exactHeight;

				updateBounds();
				repaint();
			}
		};

		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
		addMouseWheelListener(mouseHandler);
	}

	private void updateBounds() {
		boundCoordinates.setBounds((int)exactX, (int)exactY, Integer.max(1, (int)exactWidth), Integer.max(1, (int)exactHeight));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Theme theme = Window.getConfig().theme;
		this.setBackground(theme.baseColor);
		
		screenSize = new Dimension(getWidth(), getHeight());

		paintBorder(g);
		paintStops(g);
		
	}
	
	public void paintStops(Graphics g) {
		Theme theme = Window.getConfig().theme;
		
		Graphics2D g2d = (Graphics2D) g;
		int smallCircleRad = (int)(circleRad * 0.5);
		
		MapStopDataAnswer answer = (MapStopDataAnswer) RequestSender.sendRequest(new MapStopDataRequest(this.boundCoordinates));
		List<Point> points = answer.points.get(0);
		List<String> names = answer.names;
		
		for (int i = 0; i < points.size(); i++) {
			Point screenPoint = MapCanvas.map2ScreenCoordinate(boundCoordinates,  screenSize,  points.get(i));
			
			g2d.setColor(theme.secondaryColor);
			g2d.fillOval(screenPoint.x - circleRad, screenPoint.y - circleRad, circleRad*2, circleRad*2);
			g2d.setColor(theme.baseColor);
			g2d.fillOval(screenPoint.x - smallCircleRad, screenPoint.y - smallCircleRad, smallCircleRad*2, smallCircleRad*2);
		}
		
		this.removeMouseMotionListener(stopPopupListener);
		
		stopPopupListener = new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {}

			public void mouseMoved(MouseEvent e) {
				Point mousePoint = e.getPoint();
				for (int i = 0; i < points.size(); i++) {
					Point screenPoint = MapCanvas.map2ScreenCoordinate(boundCoordinates, screenSize, points.get(i));
					if (inCircle(screenPoint, circleRad, mousePoint)) {
						createStopPopup(screenPoint, circleRad, names.get(i));
						return;
					}
				}
				hideStopPopup();
			}
		};

		this.addMouseMotionListener(stopPopupListener);
	}
	
	private void hideStopPopup() {
		if (popup != null) popup.setVisible(false);
	}
	
	private void createStopPopup(Point center, int r, String stopName) {
		popup = new StopNamePopup(stopName);
		popup.show(this, center, r);
	}
	
	public void paintBorder(Graphics g) {
		Theme theme = Window.getConfig().theme;

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(theme.secondaryColor);
		g2d.setStroke(new BasicStroke(borderStrokeWidth));
		
		MapBorderPointsAnswer answer = (MapBorderPointsAnswer) RequestSender.sendRequest(new MapBorderPointsRequest(this.boundCoordinates));
		List<List<Point>> points = answer.points;
		
		for (int i = 0; i < points.size(); i++) {
			List<Point> pointsList = points.get(i);
			for (int j = 0; j < pointsList.size(); j++) {
				Point point1 = pointsList.get(j);
				Point screenPoint1 = MapCanvas.map2ScreenCoordinate(boundCoordinates, screenSize, point1);
				
				int point2Id = (j == pointsList.size() - 1) ? 0 : j+1;
				Point point2 = pointsList.get(point2Id);
				Point screenPoint2 = map2ScreenCoordinate(boundCoordinates, screenSize, point2);
				g2d.drawLine(screenPoint1.x, screenPoint1.y, screenPoint2.x, screenPoint2.y);
			}
		}
		
	}
	
	public static boolean inCircle(Point center, int r, Point point) {
		return center.distance(point) <= r;
	}
	
	public static boolean inRectangle(Rectangle rect, Point point) {
		return (rect.x <= point.x && point.x <= rect.x + rect.width) && (rect.y - rect.height <= point.y && point.y <= rect.y);
	}
	
	public static Point map2ScreenCoordinate(Rectangle boundCoordinates, Dimension screenSize, Point mapCoordinates) {
		float ratioX = (float)screenSize.width / boundCoordinates.width;
		float ratioY = (float)screenSize.height / boundCoordinates.height;
		
		Point relativeCoordinates = new Point(mapCoordinates.x - boundCoordinates.x, boundCoordinates.y - mapCoordinates.y);
		return new Point((int)(relativeCoordinates.x * ratioX), (int)(relativeCoordinates.y * ratioY));
	}
}
