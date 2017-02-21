package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.belt.BeltWithJoystick;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// TODO: (Code review) MochaPulp
public class Belt extends Subsystem {

	private SpeedController beltController;
	private Encoder beltEncoder;
	private DigitalInput leftLimitSwitch, rightLimitSwitch;

	private PIDController beltPID;
	private static final double beltP = 0.1;
	private static final double beltI = 0.0001;
	private static final double beltD = 0;
	// TODO: (Code review) Bad naming ^^^
	// TODO: (Code review) Good naming vvv
	public static final double MAX_BELT_SPEED = 0.5;
	
	// TODO: (Code review) What comment do?
	// direction reference:
	// front
	// left right
	// back

	public Belt() {
		beltController = new Spark(RobotMap.speedControllerBelt);
		beltController.setInverted(true);
		beltEncoder = new Encoder(RobotMap.encoderBeltA, RobotMap.encoderBeltB);
		// leftLimitSwitch = new DigitalInput(RobotMap.limitSwitchBeltLeft);
		// rightLimitSwitch = new DigitalInput(RobotMap.limitSwitchBeltRight);

		beltEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		beltPID = new PIDController(beltP, beltI, beltD, beltEncoder, new PIDOutput() {
			@Override
			public void pidWrite(double arg0) {
			}
		});
		beltPID.setOutputRange(-MAX_BELT_SPEED, MAX_BELT_SPEED);
		beltPID.setAbsoluteTolerance(60);
		beltPID.setContinuous(false);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new BeltWithJoystick());
	}

	// negative speed is left and positive speed is right
	public void setBelt(double speed) {
		if (speed < 0)
			beltController.set(Math.max(speed, -MAX_BELT_SPEED));
		else
			beltController.set(Math.min(speed, MAX_BELT_SPEED));

	}

	public boolean isAtLimit() {
		return leftLimitSwitch.get() || rightLimitSwitch.get();
	}

	public void setPIDSetpoint(double setpoint) {
		beltPID.setSetpoint(setpoint);
	}

	public double getPosition() {
		return beltEncoder.getDistance();
	}

	public double getPIDOutput() {
		return beltPID.get();
	}

	public void enablePID(boolean enable) {
		if (enable)
			beltPID.enable();
		else
			beltPID.reset();
	}

	public void resetEncoder() {
		beltEncoder.reset();
	}

	public void toSmartDashboard() {
		SmartDashboard.putNumber("belt_position", beltEncoder.getDistance());
		SmartDashboard.putBoolean("beltPID_enable", beltPID.isEnabled());
		SmartDashboard.putNumber("beltPID_out", beltPID.get());
		SmartDashboard.putNumber("beltPID_error", beltPID.getError());
	}

}
