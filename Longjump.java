package me.jonny.jclient.module.modules.movement.NCP;

import me.jonny.jclient.event.events.PacketReceivedEvent;
import me.jonny.jclient.event.events.UpdateEvent;
import me.jonny.jclient.module.AntiCheat;
import me.jonny.jclient.module.Category;
import me.jonny.jclient.module.Module;
import me.jonny.jclient.module.Movement;
import me.jonny.jclient.module.modules.MoveUtils;
import me.jonny.jclient.module.modules.PlayerUtils;

public class LongJump extends Module {
	  private float air;
	  
	  private float groundTicks;
	  
	  private int stage;
	public LongJump() {
		super("LongJump", "Makes you jump but longly", 0, Category.MOVEMENT, AntiCheat.NCP);
	}
	@Override
	public void onEnable() {
	    this.air = 0.0F;
	    this.stage = 0;
	    this.groundTicks = 0.0F;
	}

	@Override
	public void onUpdate(UpdateEvent event) {
        mc.thePlayer.lastReportedPosY = 0.0D;
        float x2 = 1.0F + MoveUtils.getSpeedEffect() * 0.45F;
        if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && mc.thePlayer.onGround) {
          this.stage = 1;
          this.groundTicks++;
          mc.thePlayer.jump();
        } 
        if (MoveUtils.isOnGround(0.01D)) {
          this.air = 0.0F;
        } else {
          if (mc.thePlayer.isCollidedVertically)
            this.stage = 0; 
          if (this.stage > 0 && this.stage <= 3)
            mc.thePlayer.onGround = true; 
          double speed = (0.75F + MoveUtils.getSpeedEffect() * 0.2F - this.air / 25.0F);
          if (speed < MoveUtils.defaultSpeed())
            speed = MoveUtils.defaultSpeed(); 
            speed = (0.8F + MoveUtils.getSpeedEffect() * 0.2F - this.air / 25.0F);
            if (speed < MoveUtils.defaultSpeed())
              speed = MoveUtils.defaultSpeed(); 
          mc.thePlayer.jumpMovementFactor = 0.0F;
          if (this.stage < 4)
            speed = MoveUtils.defaultSpeed(); 
          MoveUtils.setMotion(speed);
            mc.thePlayer.motionY = getMotion(this.stage);
          if (this.stage > 0)
            this.stage++; 
          this.air += x2;
        }
	}
	public void setSpeed(Number speed) {
		Movement.setSpeed(speed.doubleValue());
	}
	double getMotion(int stage) {
	    boolean isMoving = (mc.thePlayer.moveStrafing != 0.0F || mc.thePlayer.moveForward != 0.0F);
	    double[] mot = { 
	        0.396D, -0.122D, -0.1D, 0.423D, 0.35D, 0.28D, 0.217D, 0.15D, 0.025D, -0.00625D, 
	        -0.038D, -0.0693D, -0.102D, -0.13D, -0.018D, -0.1D, -0.117D, -0.14532D, -0.1334D, -0.1581D, 
	        -0.183141D, -0.170695D, -0.195653D, -0.221D, -0.209D, -0.233D, -0.25767D, -0.314917D, -0.371019D, -0.426D };
	    stage--;
	    if (stage >= 0 && stage < mot.length) {
	      double motion = mot[stage];
	      return motion;
	    } 
	    return mc.thePlayer.motionY;
	  }
	  
	  double getOldMotion(int stage) {
	    boolean isMoving = (mc.thePlayer.moveStrafing != 0.0F || mc.thePlayer.moveForward != 0.0F);
	    double[] mot = { 
	        0.345D, 0.2699D, 0.183D, 0.103D, 0.024D, -0.008D, -0.04D, -0.072D, -0.104D, -0.13D, 
	        -0.019D, -0.097D };
	    double[] notMoving = { 
	        0.345D, 0.2699D, 0.183D, 0.103D, 0.024D, -0.008D, -0.04D, -0.072D, -0.14D, -0.17D, 
	        -0.019D, -0.13D };
	    stage--;
	    if (stage >= 0 && stage < mot.length) {
	      if ((mc.thePlayer.moveStrafing == 0.0F && mc.thePlayer.moveForward == 0.0F) || mc.thePlayer.isCollidedHorizontally)
	        return notMoving[stage]; 
	      return mot[stage];
	    } 
	    return mc.thePlayer.motionY;
	  }
}