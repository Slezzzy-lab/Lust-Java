package dev.cedo.lust.utils.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class EntityUtils {

    public static ArrayList<EntityLivingBase> getEntities(boolean players, boolean mobs, boolean animals, boolean villagers, boolean invisibles) {
        ArrayList<EntityLivingBase> ents = new ArrayList<>();

        for(Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if(entity instanceof EntityLivingBase) {

                if(((EntityLivingBase) entity).getHealth() == 0)
                    continue;

                if(entity == Minecraft.getMinecraft().thePlayer)
                    continue;

                if(!players && entity instanceof EntityPlayer)
                    continue;
                if(!mobs && entity instanceof EntityMob)
                    continue;
                if(!animals && entity instanceof EntityAnimal)
                    continue;
                if(!invisibles && entity.isInvisible())
                    continue;
                if(!villagers && entity instanceof EntityVillager) {
                    continue;
                }

                ents.add((EntityLivingBase) entity);

            }
        }
        return ents;
    }


    public static ArrayList<EntityLivingBase> checkEntities(ArrayList<EntityLivingBase> entities, boolean players, boolean mobs, boolean animals, boolean villagers, boolean invisibles) {
        ArrayList<EntityLivingBase> ents = new ArrayList<>();
        for(EntityLivingBase entity : entities) {
            if(entity != null) {

                if(entity.getHealth() == 0)
                    continue;

                if(entity == Minecraft.getMinecraft().thePlayer)
                    continue;

                if(!players && entity instanceof EntityPlayer)
                    continue;
                if(!mobs && entity instanceof EntityMob)
                    continue;
                if(!animals && entity instanceof EntityAnimal)
                    continue;
                if(!invisibles && entity.isInvisible())
                    continue;
                if(!villagers && entity instanceof EntityVillager) {
                    continue;
                }

                ents.add(entity);

            }
        }
        return ents;
    }
}
