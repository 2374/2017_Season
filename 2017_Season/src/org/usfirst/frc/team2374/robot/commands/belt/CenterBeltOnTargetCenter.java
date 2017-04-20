package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.commands.auto.AutoConstants;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch.DriveToType;

import edu.wpi.first.wpilibj.command.Scheduler;

public class CenterBeltOnTargetCenter extends CenterBeltOnTarget {

	public CenterBeltOnTargetCenter() {
		super();
	}

	@Override
	protected void exit() {
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().add(new DriveToInch(AutoConstants.CENTER_BELT_ON_TARGET_CENTER, DriveToType.SHORT));
	}

}
