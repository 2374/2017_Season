package org.usfirst.frc.team2374.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision extends Subsystem {

	private NetworkTable tableIn, tableOut;
	private List<Rectangle> contours;

	private static final int RESOLUTION_X = 640;
	private static final double OFFSET_TO_GEAR = 6.21;
	// inches between camera and center of gear
	private static final double WIDTH_OF_TARGET = 10.25; // inches
	private static final double OFFSET_TO_FRONT = 4.0;

	private static final double CALIBRATION_DIST_INCHES = 60;
	private static final double CALIBRATION_WIDTH_INCHES = 10.25;
	private static final double CALIBRATION_WIDTH_PIXELS = 122;
	private static final double FOCAL_LENGTH = CALIBRATION_WIDTH_PIXELS * CALIBRATION_DIST_INCHES
			/ CALIBRATION_WIDTH_INCHES;

	public Vision() {
		tableIn = NetworkTable.getTable("vision");
		tableOut = NetworkTable.getTable("visionOut");
		contours = new ArrayList<>();
	}

	@Override
	protected void initDefaultCommand() {
	}

	public void updateContours() {
		double[] x = tableIn.getNumberArray("x", new double[0]);
		double[] y = tableIn.getNumberArray("y", new double[0]);
		double[] w = tableIn.getNumberArray("w", new double[0]);
		double[] h = tableIn.getNumberArray("h", new double[0]);
		contours.clear();
		for (int i = 0; i < x.length; i++) {
			try {
				Rectangle rect = new Rectangle((int) x[i], (int) y[i], (int) w[i], (int) h[i]);
				contours.add(rect);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
		}
		if (contours.size() == 3)
			fixRectangles();
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
		} else {
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
	public boolean twoOrMoreTargets() {
		return contours.size() >= 2;
	}

	// will return a positive or negative if valid, but will return the maximum
	// double if not valid
	public double pixelsToCenter() {
		if (contours.size() < 2)
			return Integer.MAX_VALUE;
		int center = contours.get(0).getCenter(contours.get(1));
		int pixToCenter = center - RESOLUTION_X / 2;
		double pixelsPerInch = getTargetWidth() / WIDTH_OF_TARGET; // pixels/inches
		// DriverStation.reportWarning("pixPerInch " + pixelsPerInch, true);
		return (-pixToCenter) + OFFSET_TO_GEAR * pixelsPerInch;
	}

	// will always be positive if its valid
	public int getTargetWidth() {
		if (contours.size() < 2)
			return -1;
		return contours.get(0).getWidth(contours.get(1));
	}

	public double distanceToTargetInches() {
		return CALIBRATION_WIDTH_INCHES * FOCAL_LENGTH / getTargetWidth() - OFFSET_TO_FRONT;
	}

	// will return a positive or negative if valid, but will return the maximum
	// double if not valid
	public double compareAreas() {
		if (contours.size() < 2)
			return Double.MAX_VALUE;
		return -(contours.get(0).compareAreas(contours.get(1)));
	}

	public void toSmartDashboard() {
		updateContours();
		SmartDashboard.putNumber("distanceToCenter", pixelsToCenter());
		SmartDashboard.putNumber("distanceToTarget", distanceToTargetInches());
		SmartDashboard.putNumber("areaDifference", compareAreas());
		SmartDashboard.putNumber("targetWidth", getTargetWidth());
		double[] x = new double[contours.size()];
		double[] y = new double[contours.size()];
		double[] width = new double[contours.size()];
		double[] height = new double[contours.size()];
		for (int i = 0; i < contours.size(); i++) {
			x[i] = contours.get(i).x;
			y[i] = contours.get(i).y;
			width[i] = contours.get(i).width;
			height[i] = contours.get(i).height;
		}
		tableOut.putNumberArray("x", x);
		tableOut.putNumberArray("y", y);
		tableOut.putNumberArray("width", width);
		tableOut.putNumberArray("height", height);
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
