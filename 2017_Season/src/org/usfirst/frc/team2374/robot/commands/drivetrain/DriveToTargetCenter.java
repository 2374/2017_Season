package org.usfirst.frc.team2374.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Scheduler;

public class DriveToTargetCenter extends DriveToTarget {

	public DriveToTargetCenter(double target) {
		super(target);
	}

	@Override
	protected void exit() {
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().add(new DriveToInch(80, DriveToType.LONG));
	}

}
