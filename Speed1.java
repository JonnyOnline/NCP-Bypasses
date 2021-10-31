package me.jonny.jclient.module.modules.speed.NCP;

import me.jonny.jclient.event.events.PacketReceivedEvent;
import me.jonny.jclient.event.events.UpdateEvent;
import me.jonny.jclient.module.AntiCheat;
import me.jonny.jclient.module.Category;
import me.jonny.jclient.module.Module;
import me.jonny.jclient.module.Movement;
import me.jonny.jclient.module.modules.PlayerUtils;

public class Speed1 extends Module {

	private int counter = 0;
	public Speed1() {
		super("Speed1", "This is a test module...", 0, Category.Speed, AntiCheat.NCP);
		
	}
	@Override
	public void onEnable() {
		this.mc.timer.timerSpeed = 1.0F;
	}

	@Override
	public void onDisable() {
		this.mc.timer.timerSpeed = 1.0F;
	}

	@Override
	public void onUpdate(UpdateEvent event) {
	      this.mc.gameSettings.keyBindJump.pressed = false;
	      if (PlayerUtils.playeriswalking())
	        if (this.mc.thePlayer.onGround) {
	          this.mc.thePlayer.jump();
	          this.mc.timer.timerSpeed = 3.0F;
	          this.counter = 0;
	        } else {
	          if (this.counter < 2) {
	            this.mc.timer.timerSpeed = 1.0F;
	          } else {
	            this.mc.timer.timerSpeed = 1.3F;
	          } 
	          this.counter++;
	        }  
	    } 
	public void setSpeed(Number speed) {
		Movement.setSpeed(speed.doubleValue());
	}
}