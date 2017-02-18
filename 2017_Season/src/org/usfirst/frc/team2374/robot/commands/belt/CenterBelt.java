package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class CenterBelt extends Command {

	private double initialPos;

	private final double offset = 30;

	public CenterBelt() {
		requires(Robot.belt);
		requires(Robot.camera);
	}

	@Override
	protected void initialize() {
		Timer.delay(0.25);
		initialPos = Robot.camera.pixelsToCenter();
		if (initialPos > 0)
			Robot.belt.setBelt(-Belt.MAX_BELT_SPEED);
		else
			Robot.belt.setBelt(Belt.MAX_BELT_SPEED);
	}

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
