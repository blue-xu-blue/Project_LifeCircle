package Enviroment;

public class Plant extends Creature {
    public Plant(CreatureType type, float x, float y) {
        super(type, x, y);
    }

    static Plant Create(CreatureType type, float x, float y) {
        return new Plant(type, x, y);
    }

    /**
     * 更新当前状态
     * @param dt
     * @return
     */
    @Override
    public boolean OnUpdate(float dt) {
        return super.OnUpdate(dt);
    }
}
