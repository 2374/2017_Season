package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveToInch extends Command {
	
	public enum DriveToType {
		SHORT, LONG
	}
	
	private DriveToType type;
	private double wantedDistance;

	private static final Drivetrain DRIVE = Robot.drivetrain;
	private static final double OFFSET = 0.0;
	
	public DriveToInch(double inches, DriveToType type) {
		requires(DRIVE);
		this.type = type;
		wantedDistance = inches;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		DRIVE.resetEncoders(true);
		DRIVE.resetGyro(true);
		if (type.equals(DriveToType.SHORT))
			DRIVE.setShortPID();
		else
			DRIVE.setLongPID();
		DRIVE.setDrivePIDSetPoint(wantedDistance);
		DRIVE.setGyroPIDSetPoint(0);
		DRIVE.enableDrivePID(true);
		DRIVE.enableGyroPID(true);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		DRIVE.arcadeDrive(Robot.drivetrain.getDrivePIDOutput(), Robot.drivetrain.getGyroPIDOutput());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Math.abs((DRIVE.getLeftDistanceInches() + DRIVE.getRightDistanceInches()) / 2 - wantedDistance) <= OFFSET;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		DRIVE.enableDrivePID(false);
		DRIVE.enableGyroPID(false);
		DRIVE.arcadeDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
