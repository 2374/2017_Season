package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.OI.TimedRumble;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class CenterBeltOnTarget extends Command {

	private double initialPos;

	private static final double OFFSET = 30;

	public CenterBeltOnTarget() {
		requires(Robot.belt);
	}

	@Override
	protected void initialize() {
		DriverStation.reportWarning("CenterBelt initialized", true);
		initialPos = Robot.camera.pixelsToCenter();
		if (Math.abs(initialPos - Integer.MAX_VALUE) <= 10) {
			DriverStation.reportWarning("Not a valid target", true);
			exit();
		} else if (initialPos > 0)
			Robot.belt.setBelt(-Belt.MAX_BELT_SPEED);
		else
			Robot.belt.setBelt(Belt.MAX_BELT_SPEED);
	}

	// intentionally left blank
	@Override
	protected void execute() {
		if (initialPos > 0 && Robot.belt.isAtLeftLimit()) {
			DriverStation.reportWarning("At left limit", true);
			exit();
		} else if (initialPos < 0 && Robot.belt.isAtRightLimit()) {
			DriverStation.reportWarning("At right limit", true);
			exit();
		} else if (initialPos > 0 && Robot.belt.getPosition() < Belt.LEFT_LIMIT - Belt.LEFT_OFFSET) {
			DriverStation.reportWarning("Not enough room on left to eject gear", true);
			exit();
		}
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.camera.pixelsToCenter() - 0) <= OFFSET;
	}

	@Override
	protected void end() {
		DriverStation.reportWarning("CenterBelt end; at " + Double.toString(Robot.camera.pixelsToCenter()), true);
		Robot.belt.setBelt(0);
		Scheduler.getInstance().add(new TimedRumble(0.1));
	}

	@Override
	protected void interrupted() {
		Robot.belt.setBelt(0);
	}

	private void exit() {
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().add(new TimedRumble(0.5));
	}

}
