package org.usfirst.frc.team2374.util;

import edu.wpi.first.wpilibj.PIDOutput;

public class SimplePIDOutput implements PIDOutput {
	
	private double value;
	
	public SimplePIDOutput() {
		value = 0;
	}

	@Override
	public void pidWrite(double output) {
		value = output;
	}
	
	public double get() {
		return value;
	}

}
