package Application;

/**
 * class RenderTarget
 * 所有渲染对象的基类
 */
abstract public class RenderTarget {
    abstract public boolean OnUpdate(float dt);
    abstract public boolean OnRender(float dt);

    GraphicBuffer graphicBuffer;
}
