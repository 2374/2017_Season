package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DrivetrainPID extends Command {
	// TODO: (Code review) Split declarations onto different, organized lines for readability
	private double speedLeft, speedRight, reallyOldSpeedLeft, reallyOldSpeedRight, integralLeft, integralRight, dxLeft,
			dxRight, dy, oldSpeedLeft, oldSpeedRight, time;
	private final double P, I, D;

	public DrivetrainPID() {
		requires(Robot.drivetrain);
		// TODO: (Code review) If these are being statically set, they should be
		// statically set as constants above
		P = .001;
		I = 0;
		D = 0;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		time = Timer.getFPGATimestamp();
		integralLeft = 0;
		integralRight = 0;
		dxLeft = 0;
		dxRight = 0;
		oldSpeedLeft = 0;
		oldSpeedRight = 0;
		reallyOldSpeedLeft = 0;
		reallyOldSpeedRight = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// TODO: (Code review) Marking speedLeft/speedRight as rawSpeedLeft/rawSpeedRight
		// would be helpful, as there's so many variables it's hard to tell immediately
		// what's being read directly from the controller
		speedLeft = Robot.oi.getDriverLeftY();// get speed from joystick
		speedRight = Robot.oi.getDriverRightY();
		double currentTime = Timer.getFPGATimestamp();// get time
		double currentSpeedLeft = P * (speedLeft - oldSpeedLeft) + I * integralLeft + D * (dxLeft / dy);// calculate
																										// the
																										// speed
																										// to
																										// set
																										// using
																										// PID
		double currentSpeedRight = P * (speedRight - oldSpeedRight) + I * integralRight + D * (dxRight / dy);
		DriverStation.reportWarning("left" + currentSpeedLeft, true);
		DriverStation.reportWarning("right" + currentSpeedRight, true);
		dxLeft = currentSpeedLeft - oldSpeedLeft;// calculate the difference
													// between the speed that is
													// set versus the previous
													// speed
		dxRight = currentSpeedRight - oldSpeedRight;
		dy = currentTime - time;// time differential
		if (reallyOldSpeedLeft != speedLeft)
			integralLeft = 0;// reset integral if joystick speed changes
		if (reallyOldSpeedRight != speedRight)
			integralRight = 0;
		if (speedLeft == 0 && speedRight == 0) {
			currentSpeedLeft = 0;
			currentSpeedRight = 0;
		} else if (speedLeft == 0) {
			currentSpeedLeft = 0;
		} else if (speedRight == 0) {
			currentSpeedRight = 0;
		}
		Robot.drivetrain.tankDrive(currentSpeedLeft, currentSpeedRight);// Set
																		// speed
																		// using
																		// PID
		integralLeft += (speedLeft - currentSpeedLeft) * dy; // summation of the
																// speed
																// differential
																// between the
																// set speed and
																// the desired
																// speed
		integralRight += (speedRight - currentSpeedRight) * dy;
		// TODO: (Code review) Shouldn't it be:
		// reallyOldSpeed
		oldSpeedLeft = currentSpeedLeft;// current left speed
		oldSpeedRight = currentSpeedRight;// current right speed
		reallyOldSpeedLeft = speedLeft;
		reallyOldSpeedRight = speedRight;
		time = currentTime;
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
