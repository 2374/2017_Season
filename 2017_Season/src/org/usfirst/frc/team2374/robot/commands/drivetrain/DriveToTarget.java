package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Vision;


public class DriveToTarget extends DriveToInch {

	private static final Vision CAMERA = Robot.camera;

	public DriveToTarget(double target) {
		super(CAMERA.distanceToTargetInches() - target, DriveToType.SHORT /* this may need to change if we sometimes need long PID constants */);
		requires(CAMERA);
	}

}
