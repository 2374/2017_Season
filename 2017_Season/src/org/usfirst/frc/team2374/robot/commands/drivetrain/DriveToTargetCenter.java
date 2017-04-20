package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.commands.auto.AutoConstants;
import org.usfirst.frc.team2374.robot.commands.auto.LoadGearAutoCenter;

import edu.wpi.first.wpilibj.command.Scheduler;

public class DriveToTargetCenter extends DriveToTarget {

	public DriveToTargetCenter(double target, DriveToType type) {
		super(target, type);
	}

	@Override
	protected void exit() {
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().add(new DriveToInch(AutoConstants.DRIVETOINCH_FAIL_CENTER_FOWRD, DriveToType.LONG));
		Scheduler.getInstance().add(new LoadGearAutoCenter());
	}

}
