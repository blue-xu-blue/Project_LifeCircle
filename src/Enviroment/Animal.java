package Enviroment;

public class Animal extends Creature {

    private float moveSpeed; // 移动速度
    private float consumeEnergyPerTime; // 单位时间消耗能量
    private float huntingEnergyTreshold; // 低于能量阈值感到饥饿

    private float eatingTimeConsume; // 进食用时

    private boolean alert; // 警觉
    private boolean idle; // 闲置
    private boolean eatting; // 进食


    public Animal(CreatureType type, float x, float y) {
        super(type, x, y);
    }

    public static Animal Create(CreatureType type, float x, float y) {
        return new Animal(type, x, y);
    }

    /**
     * 设置食物类型
     * @param type
     */
    public void SetFoodType(CreatureType type) {
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

    /**
     * 搜索环境
     */
    private void Search() {
    }

    /**
     * 移动
     */
    private void Move() {
    }
}
