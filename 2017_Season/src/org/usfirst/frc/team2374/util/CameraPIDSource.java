package org.usfirst.frc.team2374.util;

import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CameraPIDSource implements PIDSource {

	private Vision camera;

	public CameraPIDSource() {
		// camera = Robot.camera;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return camera.compareAreas();
	}

}
