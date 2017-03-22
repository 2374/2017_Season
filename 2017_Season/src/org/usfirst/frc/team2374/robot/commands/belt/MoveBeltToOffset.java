package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 */
public class MoveBeltToOffset extends MoveBeltToPoint {

	public MoveBeltToOffset() {
		super();
	}

	@Override
	protected void initialize() {
		DriverStation.reportWarning("MoveBeltToOffset initialized", true);
		wantedPosition = Robot.belt.getPosition() + Belt.LEFT_OFFSET;
		super.initialize();
	}

}
