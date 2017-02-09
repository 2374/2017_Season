package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class TurnToDegree extends Command {

	private Drivetrain drive = Robot.drivetrain;
	private double wantedAngle;
	private double speed;
	private double offset = 1.0;

	public TurnToDegree(double angle, double speed) {
		requires(drive);
		wantedAngle = angle;
		this.speed = speed;
	}

	public TurnToDegree(double angle) {
		this(angle, Drivetrain.MAX_AUTO_SPEED);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		drive.resetGyro();
		Timer.delay(0.1);
		drive.setGyroPIDSetPoint(wantedAngle);
		drive.enableGyroPID(true);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		drive.arcadeDrive(0, drive.getGyroPIDOutput());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (wantedAngle < 0)
			return drive.getAngle() <= wantedAngle + offset;
		return drive.getAngle() >= wantedAngle - offset;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
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
