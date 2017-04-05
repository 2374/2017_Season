package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.belt.BeltWithJoystick;

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

	private PIDController beltPID;

	private static final double BELT_P = 0.1;
	private static final double BELT_I = 0.0001;
	private static final double BELT_D = 0;

	public static final double MAX_BELT_SPEED = 0.5;

	public static double BELT_LEFT_OFFSET;
	public static double BELT_LEFT_LIMIT;
	public static double BELT_RIGHT_LIMIT;
	public static double BELT_CNTR_TAR_THRESH;

	public Belt() {
		updatePreferences();

		beltController = new Spark(RobotMap.SPEED_CONTROLLER_BELT);
		beltController.setInverted(true);
		beltEncoder = new Encoder(RobotMap.ENCODER_BELT_A, RobotMap.ENCODER_BELT_B);

		beltEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		beltEncoder.setReverseDirection(true);
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

	public boolean isAtLeftLimit() {
		return getPosition() < BELT_LEFT_LIMIT;
	}

	public boolean isAtRightLimit() {
		return getPosition() > BELT_RIGHT_LIMIT;
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

	public double getPIDError() {
		return beltPID.getError();
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

	public void updatePreferences() {
		BELT_LEFT_OFFSET = Robot.prefs.getDouble("BELT_LEFT_OFFSET", -335.0);
		BELT_LEFT_LIMIT = Robot.prefs.getDouble("BELT_LEFT_LIMIT", -513.25);
		BELT_RIGHT_LIMIT = Robot.prefs.getDouble("BELT_RIGHT_LIMIT", 254.5);
		BELT_CNTR_TAR_THRESH = Robot.prefs.getDouble("BELT_CNTR_TAR_THRESH", 20);
		Robot.prefs.putDouble("BELT_LEFT_OFFSET", BELT_LEFT_OFFSET);
		Robot.prefs.putDouble("BELT_LEFT_LIMIT", BELT_LEFT_LIMIT);
		Robot.prefs.putDouble("BELT_RIGHT_LIMIT", BELT_RIGHT_LIMIT);
		Robot.prefs.putDouble("BELT_CNTR_TAR_THRESH", BELT_CNTR_TAR_THRESH);

	}

}
