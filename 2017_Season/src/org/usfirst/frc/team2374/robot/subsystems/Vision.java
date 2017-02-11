package org.usfirst.frc.team2374.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision extends Subsystem {

	private NetworkTable table;
	private List<Rectangle> contours;

	private static final String networkTableName = "vision";
	private static final int resolutionX = 640;
	private static final double offset = 5.50; //inches between camera and center of gear
	private static final double widthOfTarget = 10.25; //inches

	private static final double calibrationDistInches = 60;
	private static final double calibrationWidthInches = 10.25; // contingency:
																// initialize()
																// method in
																// centerBelt
																// command needs
																// to be fixed
																// with this
	private static final double calibrationWidthPixel = 116;
	private static final double focalLength = calibrationWidthPixel * calibrationDistInches / calibrationWidthInches;

	public Vision() {
		table = NetworkTable.getTable(networkTableName);
		contours = new ArrayList<>();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void updateContours() {
		double[] x = table.getNumberArray("x", new double[0]);
		double[] y = table.getNumberArray("y", new double[0]);
		double[] w = table.getNumberArray("w", new double[0]);
		double[] h = table.getNumberArray("h", new double[0]);
		contours.clear();
		for (int i = 0; i < x.length; i++) {
			try {
				Rectangle rect = new Rectangle((int) x[i], (int) y[i], (int) w[i], (int) h[i]);
				contours.add(rect);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
		}
	}
	
	public void fixRectangles() {
		Rectangle whole, fixed, top, bottom;
		int index = 0;
		for (int i = 0; i < 3; i++) {
			if (contours.get(i).getArea() > contours.get(index).getArea())
				index = i;
		}
		whole = contours.remove(index);
		if (contours.get(0).y < contours.get(1).y) {
			top = contours.get(0);
			bottom = contours.get(1);
		}
		else {
			top = contours.get(1);
			bottom = contours.get(0);
		}
		int height = bottom.y2 - top.y;
		fixed = new Rectangle(top.x, top.y, top.width, height);
		contours.clear();
		contours.add(whole);
		contours.add(fixed);
	}

	// returns true if there are at least two vision targets
	public boolean isReal() {
		return contours.size() >= 2;
	}

	// will return a positive or negative if valid, but will return the maximum
	// double if not valid
	public double pixelsToCenter() {
		if (contours.size() < 2)
			return Integer.MAX_VALUE;
		if (contours.size() == 3)
			fixRectangles();
		int center = contours.get(0).getCenter(contours.get(1));
		int pixToCenter = center - resolutionX / 2;
		double inchesToPixels = getTargetWidth() / widthOfTarget; //pixels/inches
		return inchesToPixels * offset + pixToCenter;
	}

	// will always be positive if its valid
	public int getTargetWidth() {
		if (contours.size() < 2)
			return -1;
		if (contours.size() == 3)
			fixRectangles();
		return contours.get(0).getWidth(contours.get(1));
	}

	public double distanceToTargetInches() {
		return calibrationWidthInches * focalLength / getTargetWidth();
	}

	// will return a positive or negative if valid, but will return the maximum
	// double if not valid
	public double compareAreas() {
		if (contours.size() < 2)
			return Double.MAX_VALUE;
		if (contours.size() == 3)
			fixRectangles();
		return contours.get(0).compareAreas(contours.get(1));
	}

	public void toSmartDashboard() {
		updateContours();
		SmartDashboard.putNumber("distanceToCenter", pixelsToCenter());
		SmartDashboard.putNumber("distanceToTarget", distanceToTargetInches());
		SmartDashboard.putNumber("areaDifference", compareAreas());
		SmartDashboard.putNumber("targetWidth", getTargetWidth());
	}

	private class Rectangle {

		private int x, y, width, height, y2;

		public Rectangle(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			width = w;
			height = h;
			y2 = y + height;
		}

		public int getArea() {
			return width * height;
		}

		public int getCenter(Rectangle rect) {
			Rectangle left, right;
			if (this.x < rect.x) {
				left = this;
				right = rect;
			} else {
				left = rect;
				right = this;
			}
			return ((right.x + right.width) - left.x) / 2 + left.x;
		}

		public int getWidth(Rectangle rect) {
			Rectangle left, right;
			if (this.x < rect.x) {
				left = this;
				right = rect;
			} else {
				left = rect;
				right = this;
			}
			return (right.x + right.width) - left.x;
		}

		public double compareAreas(Rectangle rect) {
			Rectangle left, right;
			if (this.x < rect.x) {
				left = this;
				right = rect;
			} else {
				left = rect;
				right = this;
			}
			return ((double) (left.getArea() - right.getArea())) / left.getArea() * 100;
		}

	}

}
