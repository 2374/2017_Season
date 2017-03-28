package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveToInch extends Command {

	public enum DriveToType {
		SHORT, LONG, VIOLENT
	}

	protected DriveToType type;
	protected double wantedDistance;

	private static final Drivetrain DRIVE = Robot.drivetrain;
	private static final double THRESHOLD = 1.0;

	public DriveToInch(double inches, DriveToType type, double timeout) {
		requires(DRIVE);
		this.type = type;
		wantedDistance = inches;
		setTimeout(timeout);
	}

	public DriveToInch(double inches, DriveToType type) {
		requires(DRIVE);
		this.type = type;
		wantedDistance = inches;
		setTimeout(3);
	}

	public DriveToInch(double timeout) {
		requires(DRIVE);
		setTimeout(timeout);
	}

	public DriveToInch() {
		requires(DRIVE);
		setTimeout(3);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		DRIVE.resetAllSenors(true);
		if (type.equals(DriveToType.SHORT))
			DRIVE.setShortPID();
		else if (type.equals(DriveToType.LONG))
			DRIVE.setLongPID();
		else
			DRIVE.setViolentPID();
		DRIVE.setDrivePIDSetPoint(wantedDistance);
		DRIVE.setGyroPIDSetPoint(0);
		DRIVE.enableDrivePID(true);
		DRIVE.enableGyroPID(true);
		SmartDashboard.putNumber("DTI_wantedDist", wantedDistance);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		DRIVE.arcadeDrive(Robot.drivetrain.getDrivePIDOutput(), Robot.drivetrain.getGyroPIDOutput());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Math.abs((DRIVE.getLeftDistanceInches() + DRIVE.getRightDistanceInches()) / 2 - wantedDistance) <= THRESHOLD
				|| isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		DRIVE.enableDrivePID(false);
		DRIVE.enableGyroPID(false);
		DRIVE.arcadeDrive(0, 0);
		if (isTimedOut())
			DriverStation.reportWarning("DriveToInch timed out.", true);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
