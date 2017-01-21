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

	private static final double calibrationDistInches = 60;
	private static final double calibrationWidthInches = 10.25;
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

	public int pixelsToCenter() {
		if (contours.size() < 2)
			return Integer.MAX_VALUE;
		int center = contours.get(0).getCenter(contours.get(1));
		return center - resolutionX / 2;
	}

	public int getTargetWidth() {
		if (contours.size() < 2)
			return -1;
		return contours.get(0).getWidth(contours.get(1));
	}

	public double distanceToTargetInches() {
		return calibrationWidthInches * focalLength / getTargetWidth();
	}

	public double compareAreas() {
		if (contours.size() < 2)
			return Double.MAX_VALUE;
		return contours.get(0).compareAreas(contours.get(1));
	}

	public void toSmartDashboard() {
		SmartDashboard.putNumber("distanceToCenter", pixelsToCenter());
		SmartDashboard.putNumber("distanceToTarget", distanceToTargetInches());
		SmartDashboard.putNumber("areaDifference", compareAreas());
		SmartDashboard.putNumber("targetWidth", getTargetWidth());
	}

	private class Rectangle {

		@SuppressWarnings("unused")
		private int x, y, width, height;

		public Rectangle(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			width = w;
			height = h;
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
