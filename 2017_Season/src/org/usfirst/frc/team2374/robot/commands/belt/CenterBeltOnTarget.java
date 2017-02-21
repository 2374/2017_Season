package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class CenterBeltOnTarget extends Command {

	private double initialPos;

	private final double offset = 30;

	public CenterBeltOnTarget() {
		requires(Robot.belt);
		requires(Robot.camera);
	}

	@Override
	protected void initialize() {
		// TODO: (Code review) What's the purpose of this sleep? Sleeps are generally
		// bad to have if you're waiting for an event to happen. If it's a multithreading issue,
		// use a semaphore
		Timer.delay(0.25);
		initialPos = Robot.camera.pixelsToCenter();
		if (initialPos > 0)
			Robot.belt.setBelt(-Belt.MAX_BELT_SPEED);
		else
			Robot.belt.setBelt(Belt.MAX_BELT_SPEED);
	}

	// TODO: (Code review) Shouldn't need to have an empty, overridden function
	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		if (initialPos > 0)
			return Robot.camera.pixelsToCenter() <= offset;
		return Robot.camera.pixelsToCenter() >= -offset;
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
