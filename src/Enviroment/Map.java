package Enviroment;

/**
 * 矩形地图, 使用一个二维表存储坐标，每个坐标对应一个节点，节点记录当前土地的类型和驻扎的动物
 */
public class Map {
    private class Block
    {
        public float x, y; // 此地的坐标
        public boolean haveGrass; // 这块地是否有草
        public Creature creature; // 此地的生物
    }

    private float mapWidth;
    private float mapHeight;

    public Map(float width, float height){
        this.mapWidth = width;
        this.mapHeight = height;
    }


    /**
     * 获得这块土地(x,y)的动物
     * @param x
     * @param y
     * @return 有则返回动物的引用
     */
    public Creature GetAnimal(int x, int y) {

        return null;
    }

    /**
     * 获得这块土地(x, y)的草
     * @param x
     * @param y
     * @return 有则返回草的引用
     */
    public Creature GetGrass(int x, int y) {

        return null;
    }

    /**
     * 在(x, y)放一棵草
     * @param x
     * @param y
     */
    public void SetGrass(int x, int y) {

    }

    /**
     * 在(x, y)放一只动物
     * @param x
     * @param y
     */
    public  void SetAnimal(int x, int y) {

    }
}
