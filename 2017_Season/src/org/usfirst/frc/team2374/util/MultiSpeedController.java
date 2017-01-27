package org.usfirst.frc.team2374.util;

import edu.wpi.first.wpilibj.SpeedController;

public class MultiSpeedController implements SpeedController {
	private SpeedController cont0, cont1, cont2;
	private double speed;
	private boolean inverted;

	public MultiSpeedController(SpeedController cont0, SpeedController cont1, SpeedController cont2) {
		this.cont0 = cont0;
		this.cont1 = cont1;
		this.cont2 = cont2;
		this.speed = 0;
		this.inverted = false;
	}

	@Override
	public double get() {
		return this.speed;
	}

	@Override
	public void set(double speed) {
		this.speed = speed;
		cont0.set(speed);
		cont1.set(speed);
		cont2.set(speed);
	}

	@Override
	public void disable() {
		cont0.disable();
		cont1.disable();
		cont2.disable();
	}

	@Override
	public void setInverted(boolean isInverted) {
		inverted = isInverted;
		cont0.setInverted(isInverted);
		cont1.setInverted(isInverted);
		cont2.setInverted(isInverted);
	}

	@Override
	public boolean getInverted() {
		return inverted;
	}

	@Override
	public void stopMotor() {
		cont0.stopMotor();
		cont1.stopMotor();
		cont2.stopMotor();
	}

	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
	}
}