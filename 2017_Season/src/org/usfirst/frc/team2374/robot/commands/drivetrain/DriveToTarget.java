package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.oi.TimedRumble;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Scheduler;

public class DriveToTarget extends DriveToInch {

	private static final Vision CAMERA = Robot.camera;
	private final double target;
	private final DriveToType type;

	public DriveToTarget(double target) {
		super();
		requires(CAMERA);
		this.target = target;
		type = DriveToType.SHORT;
	}

	public DriveToTarget(double target, DriveToType type) {
		super();
		requires(CAMERA);
		this.target = target;
		this.type = type;
	}

	public DriveToTarget(double target, DriveToType type, double timeout) {
		super(timeout);
		requires(CAMERA);
		this.target = target;
		this.type = type;
	}

	@Override
	protected void initialize() {
		super.type = type;
		double disToTar = CAMERA.distanceToTargetInches();
		if (disToTar <= 0) {
			DriverStation.reportWarning("Not a valid target", true);
			exit();
		}
		wantedDistance = disToTar - target;
		if (wantedDistance < 0)
			wantedDistance = -3.5;
		super.initialize();
	}

	private void exit() {
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().add(new TimedRumble(0.5));
	}

}
