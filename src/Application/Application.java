package Application;

import org.lwjgl.glfw.GLFW;

public class Application extends Engine
{
    private Gui gui;

    /**
     * Constructor of class Application
     * @param windowWidth
     * @param windowHeight
     * @param title
     * @param fullscreen
     */
    public Application(int windowWidth, int windowHeight, String title, boolean fullscreen)
    {
        super(windowWidth, windowHeight, title, fullscreen);
    }

    /**
     * Default constructor of class Application
     */
    public Application()
    {
        super(1280, 720, "Life Circle", false);
    }

    /**
     * 子类初始化，成功返回真
     * @return
     */
    public boolean Init() {
        /**
         * 调用基类初始化
         */
        if(!super.Init()) {
            return false;
        }

        gui = new Gui(window);

        return true;
    }

    @Override
    public boolean OnUserUpdate(double dt) {
        gui.Update(dt);
        return true;
    }

    @Override
    public boolean OnUserRender(double dt) {
        gui.Render(dt);
        return true;
    }

    @Override
    public void OnUserKeyInput(int keyCode, int action) {
        if(action == GLFW.GLFW_PRESS){
            switch (keyCode) {
                case GLFW.GLFW_KEY_ESCAPE:
                    ToggleTerminate();
                    break;
                case GLFW.GLFW_KEY_F11:
                    ToggleFullScreen();
                    break;
            }
        }
    }

    @Override
    public void OnUserMouseInput(double xPos, double yPos, int button, int action) {

    }

    /**
     * Main函数，程序入口
     * @param argv 命令行参数列表
     */
    static public void main(String argv[])
    {
        Application app = new Application();
        if(app.Init()){
            app.Run();
        }
    }

}
