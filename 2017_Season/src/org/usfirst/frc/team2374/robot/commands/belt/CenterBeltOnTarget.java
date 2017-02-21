package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.command.Command;

public class CenterBeltOnTarget extends Command {

	private double initialPos;

	private static final double OFFSET = 30;

	public CenterBeltOnTarget() {
		requires(Robot.belt);
		requires(Robot.camera);
	}

	@Override
	protected void initialize() {
		initialPos = Robot.camera.pixelsToCenter();
		if (initialPos > 0)
			Robot.belt.setBelt(-Belt.MAX_BELT_SPEED);
		else
			Robot.belt.setBelt(Belt.MAX_BELT_SPEED);
	}

	// intentionally left blank
	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.camera.pixelsToCenter() - 0) <= OFFSET;
	}

	@Override
	protected void end() {
		Robot.belt.setBelt(0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
