package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class TurnToDegree extends Command {

	private static final Drivetrain DRIVE = Robot.drivetrain;
	private static final double THRESHOLD = 1.0;

	private double wantedAngle;

	public TurnToDegree(double angle) {
		requires(DRIVE);
		wantedAngle = angle;
	}

	public TurnToDegree(double angle, double timeout) {
		requires(DRIVE);
		wantedAngle = angle;
		setTimeout(timeout);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		DriverStation.reportWarning("TurnToDegree initialized.", true);
		DRIVE.resetGyro(true);
		DRIVE.setTurnPID();
		DRIVE.setGyroPIDSetPoint(wantedAngle);
		DRIVE.enableGyroPID(true);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		DRIVE.arcadeDrive(0, DRIVE.getGyroPIDOutput());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Math.abs(DRIVE.getGyroPIDError()) <= THRESHOLD || isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		DRIVE.enableGyroPID(false);
		DRIVE.arcadeDrive(0, 0);
		if (isTimedOut())
			DriverStation.reportWarning("TurnToDegree timed out.", true);
		else
			DriverStation.reportWarning("TurnToDegree ended with " + Double.toString(DRIVE.getGyroPIDError()) + " error.", true);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
