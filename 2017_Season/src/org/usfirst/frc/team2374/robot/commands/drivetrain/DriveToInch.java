package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveToInch extends Command {

	private Drivetrain drive = Robot.drivetrain;
	private double wantedDistance;
	private final double offset = 1.0;

	public DriveToInch(double inches) {
		requires(drive);
		wantedDistance = inches;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		drive.resetEncoders();
		drive.resetGyro();
		Timer.delay(0.1);
		drive.setDrivePIDSetPoint(wantedDistance);
		drive.setGyroPIDSetPoint(0);
		drive.enableDrivePID(true);
		drive.enableGyroPID(true);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		drive.arcadeDrive(Robot.drivetrain.getDrivePIDOutput(), Robot.drivetrain.getGyroPIDOutput());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		double currentDistance = (drive.getLeftDistanceInches() + drive.getRightDistanceInches()) / 2;
		if (wantedDistance < 0)
			return currentDistance <= wantedDistance + offset;
		return currentDistance >= wantedDistance - offset;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		drive.enableDrivePID(false);
		drive.enableGyroPID(false);
		drive.arcadeDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
