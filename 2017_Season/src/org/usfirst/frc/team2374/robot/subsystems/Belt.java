package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.belt.BeltWithJoystick;
import org.usfirst.frc.team2374.util.SimplePIDOutput;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Belt extends Subsystem {

	private SpeedController beltController;
	private Encoder beltEncoder;
	private DigitalInput leftLimitSwitch, rightLimitSwitch;

	private PIDController beltPID;
	private SimplePIDOutput beltOut;
	private static final double beltP = 0;
	private static final double beltI = 0;
	private static final double beltD = 0;

	private static final double MAX_BELT_SPEED = 0.5;

	// direction reference:
	// front
	// left right
	// back

	public Belt() {
		beltController = new Spark(RobotMap.speedControllerBelt);
		beltEncoder = new Encoder(RobotMap.encoderBeltA, RobotMap.encoderBeltB);
		leftLimitSwitch = new DigitalInput(RobotMap.limitSwitchBeltLeft);
		rightLimitSwitch = new DigitalInput(RobotMap.limitSwitchBeltRight);

		beltEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		beltPID = new PIDController(beltP, beltI, beltD, beltEncoder, beltOut);
		beltPID.setOutputRange(-MAX_BELT_SPEED, MAX_BELT_SPEED);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new BeltWithJoystick());
	}

	// negative speed is left and positive speed is right
	public void setBelt(double speed) {
		if (speed < 0 && leftLimitSwitch.get())
			beltController.set(0);
		else if (speed > 0 && rightLimitSwitch.get())
			beltController.set(0);
		else
			beltController.set(Math.min(speed, MAX_BELT_SPEED));
	}

	public boolean isAtLimit() {
		return leftLimitSwitch.get() || rightLimitSwitch.get();
	}

	public void setPIDSpeed(double speed) {
		double output = Math.min(speed, MAX_BELT_SPEED);
		beltPID.setOutputRange(-output, output);
	}

	public void setPIDSetpoint(double setpoint) {
		beltPID.setSetpoint(setpoint);
	}

	public double getPosition() {
		return beltEncoder.getDistance();
	}

	public double getPIDOutput() {
		return beltOut.get();
	}

	public void enablePID(boolean enable) {
		if (enable)
			beltPID.enable();
		else
			beltPID.disable();
	}

	public void resetEncoder() {
		beltEncoder.reset();
	}

}
