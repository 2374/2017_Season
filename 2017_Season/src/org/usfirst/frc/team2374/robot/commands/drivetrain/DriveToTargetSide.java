package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.commands.auto.AutoConstants;
import org.usfirst.frc.team2374.robot.commands.auto.LoadGearAuto;

import edu.wpi.first.wpilibj.command.Scheduler;

public class DriveToTargetSide extends DriveToTarget {

	public DriveToTargetSide(double target) {
		super(target);
	}

	@Override
	protected void exit() {
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().add(new DriveToInch(AutoConstants.DRIVETOINCH_FAIL_SIDE_FOWRD, DriveToType.LONG));
		Scheduler.getInstance().add(new LoadGearAuto());
	}

}
