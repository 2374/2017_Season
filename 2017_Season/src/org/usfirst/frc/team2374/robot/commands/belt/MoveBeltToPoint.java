package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.oi.TimedRumble;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class MoveBeltToPoint extends Command {

	private static final Belt BELT = Robot.belt;
	private static final double OFFSET = 10;

	protected double wantedPosition;

	public MoveBeltToPoint(double position) {
		requires(BELT);
		wantedPosition = position;
	}

	public MoveBeltToPoint() {
		requires(BELT);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		BELT.setPIDSetpoint(wantedPosition);
		BELT.enablePID(true);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double output = BELT.getPIDOutput();
		if ((BELT.isAtLeftLimit() && output < 0) || (BELT.isAtRightLimit() && output > 0)) {
			DriverStation.reportWarning("At a limit", true);
			exit();
		}
		BELT.setBelt(output);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Math.abs(BELT.getPosition() - wantedPosition) <= OFFSET;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		DriverStation.reportWarning("MoveBeltToPoint end", true);
		BELT.enablePID(false);
		BELT.setBelt(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

	private void exit() {
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().add(new TimedRumble(0.5));
	}
}
