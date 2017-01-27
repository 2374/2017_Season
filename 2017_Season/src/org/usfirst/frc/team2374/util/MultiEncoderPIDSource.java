package org.usfirst.frc.team2374.util;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class MultiEncoderPIDSource implements PIDSource {

	private PIDSourceType type;
	private Encoder left, right;

	public MultiEncoderPIDSource(Encoder left, Encoder right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		type = pidSource;
		left.setPIDSourceType(pidSource);
		right.setPIDSourceType(pidSource);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return type;
	}

	@Override
	public double pidGet() {
		double left = this.left.pidGet();
		double right = this.right.pidGet();
		return (left + right) / 2;
	}

}
