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


public class Belt extends Subsystem {

	private SpeedController beltController;
	private Encoder beltEncoder;
	private DigitalInput leftLimitSwitch, rightLimitSwitch;

	private PIDController beltPID;
	
	private static final double BELT_P = 0.1;
	private static final double BELT_I = 0.0001;
	private static final double BELT_D = 0;
	
	public static final double MAX_BELT_SPEED = 0.5;

	public Belt() {
		beltController = new Spark(RobotMap.SPEED_CONTROLLER_BELT);
		beltController.setInverted(true);
		beltEncoder = new Encoder(RobotMap.ENCODER_BELT_A, RobotMap.ENCODER_BELT_B);
		// leftLimitSwitch = new DigitalInput(RobotMap.limitSwitchBeltLeft);
		// rightLimitSwitch = new DigitalInput(RobotMap.limitSwitchBeltRight);

		beltEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		beltPID = new PIDController(BELT_P, BELT_I, BELT_D, beltEncoder, new PIDOutput() {
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
