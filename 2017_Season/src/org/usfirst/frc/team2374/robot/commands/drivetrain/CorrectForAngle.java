package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.command.TimedCommand;

public class CorrectForAngle extends TimedCommand {

	private Drivetrain drive = Robot.drivetrain;
	private Vision camera = Robot.camera;
	private double initialError;

	public CorrectForAngle(double time) {
		super(time);
		requires(drive);
		requires(camera);
	}

	protected void Initialize() {

		initialError = camera.compareAreas(); // percent difference between
												// rectangle areas
		if (initialError < 0)
			drive.arcadeDrive(0, 0.5);
		else
			drive.arcadeDrive(0, -0.5);
	}

	protected void Execute() {
	}

	@Override
	protected boolean isFinished() {
		double error = camera.compareAreas();
		return error < -20.0 || error > 20.0 || this.isTimedOut();// ends
																	// when
																	// we
																	// are
																	// parallel
																	// or
																	// when
																	// one
																	// second
																	// has
																	// passed
	}

	@Override
	protected void end() {
		drive.arcadeDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
