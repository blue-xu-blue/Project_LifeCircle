package Enviroment;

import Application.RenderTarget;
import Application.Renderer;

/**
 * class Creature
 * 所有生命体对象的基类
 */
public class Creature extends RenderTarget {
    /**
     * 生命体类型
     */
    public enum CreatureType {
        CREATURE_TYPE_GRASS, // 草
        CREATURE_TYPE_RABBIT, // 兔
        CREATURE_TYPE_WOLF, // 狼

        CREATURE_TYPE_UNKNOWN; // 不知道什么生物
    }

    private CreatureType creatureType; // 生物类型
    protected float xPos = 0.f, yPos = 0.f; // 地图坐标

    protected boolean alive; // 存活状态
    protected float currentAge; // 当前年龄
    protected float maxAge; // 最长寿命

    protected float initEnergy; // 初始能量，动物出生从父母获得，植物不变
    protected float currentEnergy; // 当前能量

    /**
     * 静态变量线程不安全
     */
    protected static Map map; // 地图
    protected static Enviroment enviroment; // 环境

    /**
     * Constructor of creature
     * 设置生命体的类型和初始化坐标
     * @param type
     * @param x
     * @param y
     */
    public Creature(CreatureType type, float x, float y) {
        this.creatureType = type;
        this.xPos = x;
        this.yPos = y;
    }

    /**
     * 获取当前对象在地图中的坐标
     * @param pos 第一个值为x坐标，第二个值为y坐标
     */
    public void GetPosition(float[] pos){
        pos[0] = xPos;
        pos[1] = yPos;
    }

    /**
     * 设置坐标
     * @param pos
     */
    public void SetPosition(float[] pos) {
        xPos = pos[0];
        yPos = pos[1];
    }

    /**
     * 查询存活状态
     * @return
     */
    public boolean GetAlive() {
        return alive;
    }

    /**
     * 设置存活状态
     * @param alive
     */
    public void SetAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * 获取年龄
     * @return
     */
    public float GetAge() {
        return currentAge;
    }

    /**
     * 设置最大寿命
     * @param age
     */
    public void SetMaxAge(float age) {
        maxAge = age;
    }

    /**
     * 设置初始能量
     * @param energy
     */
    public void SetInitEnergy(float energy) {
        initEnergy = energy;
    }

    /**
     * 设置环境
     * @param en
     */
    public static void SetEnviroment(Enviroment en) {
        enviroment = en;
    }

    /**
     * 设置地图
     * @param m
     */
    public static void SetMap(Map m) {
        map = m;
    }

    @Override
    public boolean OnUpdate(float dt) {
        return true;
    }

    @Override
    public boolean OnRender(float dt) {
        return true;
    }

    /**
     * 初始化当前对象的图形，在子类初始化时候记得调用就行
     * @return
     */
    private boolean InitGraphic() {

        return true;
    }
}
