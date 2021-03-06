package org.usfirst.frc.team2374.util;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class TwoEncoderPIDSource implements PIDSource {

	private Encoder leftEncoder, rightEncoder;
	private static final double EC_PER_REV_LEFT = 359.08;
	private static final double EC_PER_REV_RIGHT = 358.98;
	private static final double WHEEL_DIAMETER_INCHES = 6.0;

	public TwoEncoderPIDSource(Encoder left, Encoder right) {
		leftEncoder = left;
		rightEncoder = right;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		double leftInches = getLeftDistanceInches();
		double rightInches = getRightDistanceInches();
		return rightInches; // (leftInches + rightInches) / 2; commented because the left encoder is broken
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	public double getLeftDistanceInches() {
		return encoderCntsToInches(leftEncoder.getDistance(), EC_PER_REV_LEFT);
	}

	public double getRightDistanceInches() {
		return encoderCntsToInches(rightEncoder.getDistance(), EC_PER_REV_RIGHT);
	}

	public static double encoderCntsToInches(double counts, double countsPerRev) {
		return (counts / countsPerRev) * (WHEEL_DIAMETER_INCHES * Math.PI);
	}

	public static double inchesToEncoderCnts(double inches, double countsPerRev) {
		return inches * countsPerRev / (WHEEL_DIAMETER_INCHES * Math.PI);
	}

}
