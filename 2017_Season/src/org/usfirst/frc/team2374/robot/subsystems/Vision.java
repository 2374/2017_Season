package org.usfirst.frc.team2374.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision extends Subsystem {

	private NetworkTable tableIn, tableOut;
	private List<Rectangle> contours;

	private static final int RESOLUTION_X = 640;
	private static final double WIDTH_OF_TARGET_INCH = 10.25;
	private static final double OFFSET_TO_FRONT_BUMPER = 4.0;
	
	private double VIS_OFFSET_TO_GEAR;
	private double VIS_CALIB_DIST_INCHES;
	private double VIS_CALIB_WIDTH_INCHES;
	private double VIS_CALIB_WIDTH_PIXELS;

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
		double pixelsPerInch = getTargetWidth() / WIDTH_OF_TARGET_INCH; // pixels/inches
		SmartDashboard.putNumber("pixelsPerInch", pixelsPerInch);
		SmartDashboard.putNumber("pixelsToCenter_raw", -pixToCenter);
		return (-pixToCenter) + VIS_OFFSET_TO_GEAR * pixelsPerInch;
	}

	// will always be positive if its valid
	public int getTargetWidth() {
		if (contours.size() < 2)
			return -1;
		return contours.get(0).getWidth(contours.get(1));
	}

	public double distanceToTargetInches() {
		return VIS_CALIB_WIDTH_INCHES * (VIS_CALIB_WIDTH_PIXELS * VIS_CALIB_DIST_INCHES
				/ VIS_CALIB_WIDTH_INCHES) / getTargetWidth() - OFFSET_TO_FRONT_BUMPER;
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
		SmartDashboard.putNumber("pixelsToCenter", pixelsToCenter());
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
	
	public void updatePreferences() {
		VIS_OFFSET_TO_GEAR = Robot.prefs.getDouble("VIS_OFFSET_TO_GEAR", 7.16);
		VIS_CALIB_DIST_INCHES = Robot.prefs.getDouble("VIS_CALIB_DIST_INCHES", 60);
		VIS_CALIB_WIDTH_INCHES = Robot.prefs.getDouble("VIS_CALIB_WIDTH_INCHES", 10.25);
		VIS_CALIB_WIDTH_PIXELS = Robot.prefs.getDouble("VIS_CALIB_WIDTH_PIXELS", 122);
		Robot.prefs.putDouble("VIS_OFFSET_TO_GEAR", VIS_OFFSET_TO_GEAR);
		Robot.prefs.putDouble("VIS_CALIB_DIST_INCHES", VIS_CALIB_DIST_INCHES);
		Robot.prefs.putDouble("VIS_CALIB_WIDTH_INCHES", VIS_CALIB_WIDTH_INCHES);
		Robot.prefs.putDouble("VIS_CALIB_WIDTH_PIXELS", VIS_CALIB_WIDTH_PIXELS);
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
