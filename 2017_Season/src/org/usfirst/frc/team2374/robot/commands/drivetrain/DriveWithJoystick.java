package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command {

	private boolean isCreeping;

	private static final Drivetrain DRIVE = Robot.drivetrain;

	public DriveWithJoystick() {
		requires(DRIVE);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		isCreeping = false;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// determining if we should go into or out of creep mode
		if (Robot.oi.getPOV() == 0)
			isCreeping = false;
		else if (Robot.oi.getPOV() == 180)
			isCreeping = true;
		double leftTrigger = Robot.oi.getLeftTrigger();
		double rightTrigger = Robot.oi.getRightTrigger();
		double leftJoystick = Robot.oi.getDriverLeftY();
		double rightJoystick = Robot.oi.getDriverRightY();
		// if we are in creep mode, reduce all inputs by 50%
		if (isCreeping) {
			leftTrigger *= 0.50;
			rightTrigger *= 0.50;
			leftJoystick *= 0.50;
			leftJoystick *= 0.50;
		}
		if (rightTrigger != 0)
			DRIVE.arcadeDrive(rightTrigger, 0);
		else if (leftTrigger != 0)
			DRIVE.arcadeDrive(-leftTrigger, 0);
		else
			DRIVE.tankDrive(leftJoystick, rightJoystick);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drivetrain.tankDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
