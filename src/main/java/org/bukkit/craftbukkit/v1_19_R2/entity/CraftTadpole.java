package org.bukkit.craftbukkit.v1_19_R2.entity;

import net.minecraft.world.entity.animal.frog.Tadpole;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;
import org.bukkit.entity.EntityType;

public class CraftTadpole extends CraftFish implements org.bukkit.entity.Tadpole {

    public CraftTadpole(CraftServer server, Tadpole entity) {
        super(server, entity);
    }

    @Override
    public Tadpole getHandle() {
        return (Tadpole) entity;
    }

    @Override
    public String toString() {
        return "CraftTadpole";
    }

    @Override
    public EntityType getType() {
        return EntityType.TADPOLE;
    }

    @Override
    public int getAge() {
        return getHandle().age;
    }

    @Override
    public void setAge(int age) {
        getHandle().age = age;
    }
}
