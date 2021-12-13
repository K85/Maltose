package com.sakurawald.data;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;

import javax.management.ImmutableDescriptor;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    public MovementSystem() {
        super();
    }

    public void addToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            PositionComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);

            position.x += velocity.x * deltaTime;
            position.y += velocity.y * deltaTime;
        }
    }

}
