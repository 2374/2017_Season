package org.usfirst.frc.team2374.util;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class MultiCANTalonPIDSource implements PIDSource {
	
	private PIDSourceType type;
	private CANTalon leftTalon, rightTalon;
	
	public MultiCANTalonPIDSource(CANTalon left, CANTalon right) {
		leftTalon = left;
		rightTalon = right;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		type = pidSource;
		leftTalon.setPIDSourceType(pidSource);
		rightTalon.setPIDSourceType(pidSource);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return type;
	}

	@Override
	public double pidGet() {
		double left = leftTalon.pidGet();
		double right = rightTalon.pidGet();
		return (left + right) / 2;
	}

}
