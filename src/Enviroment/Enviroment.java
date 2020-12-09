package Enviroment;

import Application.Renderer;

/**
 * class Enviroment
 * 管理全局生态圈逻辑
 */
public class Enviroment {

    /**
     * 静态变量线程不安全
     */
    private static Renderer renderer; // 渲染器
    private static Map map; // 地图

    public Enviroment() {
    }

    /**
     * 设置渲染器
     * @param r
     */
    public static void SetRenderer(Renderer r) {
        renderer = r;
    }

    /**
     * 设置地图
     * @param m
     */
    public static void SetMap(Map m) {
        map = m;
    }


    /**
     * 在坐标(x,y)处种一颗草
     * @param x
     * @param y
     */
    public void AddGrass(float x, float y) {
        Plant grass = Plant.Create(Creature.CreatureType.CREATURE_TYPE_GRASS, x, y);

        renderer.AddObject(grass);
    }

    /**
     * 在坐标(x,y)处放置一只兔子
     * @param x
     * @param y
     */
    public void AddRabit(float x, float y) {
        Animal rabbit = Animal.Create(Creature.CreatureType.CREATURE_TYPE_RABBIT, x, y);

        renderer.AddObject(rabbit);
    }

    /**
     * 在坐标(x,y)处放置一只狼
     * @param x
     * @param y
     */
    public void AddWolf(float x, float y) {
        Animal wolf = Animal.Create(Creature.CreatureType.CREATURE_TYPE_WOLF, x, y);

        renderer.AddObject(wolf);
    }

    /**
     * 获取兔子数量
     * @return
     */
    int GetTotalRabbitsNumber() {
        return 0;
    }

    /**
     * 获取草数量
     * @return
     */
    int GetTotalGrassNumber() {
        return 0;
    }

    /**
     * 获取狼数量
     * @return
     */
    int GetTotalWolvesNumber() {
        return 0;
    }


}
