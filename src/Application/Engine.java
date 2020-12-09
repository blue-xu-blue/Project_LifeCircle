package Application;

import glm_.vec2.Vec2;
import glm_.vec2.Vec2i;
import glm_.vec3.Vec3;

import static imgui.ImguiKt.DEBUG;
import static imgui.ImguiKt.UNICODE_CODEPOINT_INVALID;
import static imgui.impl.gl.CommonGLKt.setGlslVersion;

import glm_.vec4.Vec4i;
import gln.GlnKt;
import kotlin.Function;
import kotlin.Unit;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Platform;
import uno.glfw.*;
import unsigned.Uint;

import static gln.GlnKt.glClearColor;
import static gln.GlnKt.glViewport;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static uno.glfw.windowHint.Profile.core;

/**
 *  抽象类Engine，实现应用程序初始化，向用户提供抽象接口，运行时反复调用
 */
abstract public class Engine {

    /**
     * 窗口属性
     */
    class WindowAttribute {
        public Vec2i windowSize;
        public Vec2i windowPos;
        public Vec2i fullscreenSize;
        public boolean fullscreen;
        public String title;

        public GlfwMonitor currentMonitor;
        public GlfwVidMode currentVideoMode;


        /**
         * Constructor of Window Attribute
         * @param windowWidth
         * @param windowHeight
         * @param title
         * @param fullscreen
         */
        public WindowAttribute(int windowWidth, int windowHeight, String title, boolean fullscreen) {
            this.windowSize = new Vec2i(windowWidth, windowHeight);
            this.fullscreen = fullscreen;
            this.title = title;
        }
    }

    /**
     * 时钟
     */
    class Clock {
        private double now = 0.0;
        private double pre = 0.0;
        private double timeSum = 0.0;
        private double fps = 0.0;
        private int frameCount = 0;

        /**
         * 返回delta t
         * @return
         */
        public double Update() {
            now = glfw.getTime();
            double dt = now - pre;
            pre = now;

            timeSum += dt;
            frameCount++;

            return dt;
        }

        /**
         * 返回FPS
         * @return
         */
        public double GetFPS() {
            if(timeSum >= 1000.0) {
                fps = frameCount / timeSum;
                frameCount = 0;
                timeSum = 0.0;
            }
            return fps;
        }
    }

    /**
     * class Mouse
     */
    class Mouse {
        public double xPos;
        public double yPos;
        public double xOffset;
        public double yOffset;
        public int button;
        public int action;
    }

    /**
     * class Key
     */
    class Key {
        public int keyCode;
        public int action;
    }

    /**
     * Atrributes
     */
    protected GlfwWindow window;
    private uno.glfw.glfw glfw = uno.glfw.glfw.INSTANCE;
    private uno.glfw.windowHint windowHint = uno.glfw.windowHint.INSTANCE;
    private WindowAttribute attribute;
    private Clock clock;
    private Renderer renderer;
    private static Key key;
    private static Mouse mouse;

    /**
     * Constructor of class Engine
     * @param windowWidth 窗口宽度
     * @param windowHeight 窗口高度
     * @param title 窗口标题
     * @param fullscreen 是否全屏显示
     */
    public Engine (int windowWidth, int windowHeight, String title, boolean fullscreen) {
        this.attribute = new WindowAttribute(windowWidth, windowHeight, title, fullscreen);
        this.clock = new Clock();
        this.renderer = new Renderer();
        this.key = new Key();
        this.mouse = new Mouse();
    }

    /**
     * 初始化引擎
     * @return 返回真，初始化成功
     */
    public boolean Init() {
        if(!CreateWindow()){
            return false;
        }

        return  true;
    }

    /**
     * 主循环Run
     * @return 返回0
     */
    public int Run() {
        while(!window.getShouldClose()){
            double dt = clock.Update();

            glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glClearColor(new Vec3(0.4f, 0.5f, 0.6f), 1.f);

            /**
             * Update and render
             */
            OnUserUpdate(dt);
            OnUserRender(dt);
            OnUpdate(dt);
            OnRender(dt);

            window.swapBuffers();
            glfw.pollEvents();

            /**
             * 更新用户输入消息
             */
            OnUserKeyInput(key.keyCode, key.action);
            OnUserMouseInput(mouse.xPos, mouse.yPos, mouse.button, mouse.action);
        }

        /**
         * clear
         */
        window.onWindowClosed();
        glfw.terminate();
        return 0;
    }

    /**
     * 退出
     */
    public void ToggleTerminate() {
        if(window != null) {
            window.setShouldClose(true);
        }
    }

    /**
     * 切换全屏或窗口模式
     */
    public final void ToggleFullScreen() {
        attribute.fullscreen = !attribute.fullscreen;
        if(attribute.fullscreen) {
           window.setMonitor(new Monitor(attribute.currentMonitor.getHandle(), attribute.currentMonitor.getPos().getX(),
                   attribute.currentMonitor.getPos().getY(), attribute.currentMonitor.getWorkArea().getZ(),
                   attribute.currentMonitor.getWorkArea().getW(), 60));
            window.setSize(attribute.fullscreenSize);
        } else {
            window.setMonitor(new Monitor(0L, attribute.currentMonitor.getPos().getX(),
                    attribute.currentMonitor.getPos().getY(), attribute.currentMonitor.getWorkArea().getZ(),
                    attribute.currentMonitor.getWorkArea().getW(), 60));
            window.setPos(attribute.windowPos);
            window.setSize(attribute.windowSize);
        }
    }

    /**
     * 获取窗口长度
     * @return 窗口长度
     */
    public final float GetWindowWidth() {
        return attribute.windowSize.getX();
    }

    /**
     * 获取窗口宽度
     * @return 窗口宽度
     */
    public final float GetWindowHeight() {
        return attribute.windowSize.getY();
    }

    /**
     * 抽象接口OnUserUpdate()将会被子类重载，并在每次循环中调用。用户通过重载这个接口实现状态更新
     * @param dt 每帧的间隔时间，单位ms
     * @return 如果返回假，抛出运行时异常
     */
    abstract public boolean OnUserUpdate(double dt);

    /**
     * 抽象接口OnUserUpdate()将会被子类重载，并在每次循环中调用。用户通过重载这个接口实现渲染物体
     * @param dt 每帧的间隔时间，单位ms
     * @return 如果返回假，抛出运行时异常
     */
    abstract public boolean OnUserRender(double dt);

    /**
     * 键盘回调函数
     * @param keyCode 键值
     * @param action 按键动作，按下或释放
     */
    abstract public void OnUserKeyInput(int keyCode, int action);
    /**
     * 鼠标回调函数
     * @param xPos 指针x坐标值
     * @param yPos 指针y坐标值
     * @param button 按钮值
     * @param action 按键动作，按下或释放
     */
    abstract public void OnUserMouseInput(double xPos, double yPos, int button, int action);

    /**
     * 创建窗口
     * @return 返回假，窗口创建失败
     */
    private boolean CreateWindow() {
        glfw.setErrorCallback((error, description)-> {
            ErrorCallBack(error, description);
            return Unit.INSTANCE;
        });
        glfw.init();
        windowHint.setDebug(DEBUG);

        // Decide GL+GLSL versions
        setGlslVersion(400);
        windowHint.getContext().setVersion("3.3");
        windowHint.setProfile(core);
        windowHint.setResizable(false); // 禁止窗口调整大小

        GlfwMonitor monitors[] = glfw.getMonitors();
        for(GlfwMonitor monitor : monitors) {
            System.out.format("Monitor: %s\n", monitor.getName());
            GlfwVidMode vidmodes[] = monitor.getVideoModes();
            for(GlfwVidMode vidMode : vidmodes) {
                System.out.format("Resolution: %d x %d\n", vidMode.getSize().getX(), vidMode.getSize().getY());
            }
        }

        /**
         * 创建窗口
         */
        attribute.currentMonitor = glfw.getPrimaryMonitor();
        attribute.fullscreenSize = new Vec2i(attribute.currentMonitor.getWorkArea().getZ(),
                attribute.currentMonitor.getWorkArea().getW());
        if(attribute.fullscreen){
            /** 全屏模式 */
            window = new GlfwWindow(attribute.fullscreenSize,
                    attribute.title, attribute.currentMonitor, new Vec2i(30), true);
        }else{
            /** 窗口模式 */
            attribute.windowPos = new Vec2i((int)((float)attribute.fullscreenSize.getX() - attribute.windowSize.getX()) / 2,
            (int)((float)attribute.fullscreenSize.getY() - attribute.windowSize.getY()) / 2);

            window = new GlfwWindow(attribute.windowSize,
                    attribute.title, null, attribute.windowPos, true);

        }

        window.makeContextCurrent();
        glfw.setSwapInterval(VSync.ON); // 使用垂直同步
        GL.createCapabilities(); // 初始化OpenGL

        SetCallBacks(); // 注册回调函数

        return true;
    }

    /**
     * 出错消息回调函数
     */
    private static void ErrorCallBack(uno.glfw.glfw.Error error, String description) {
        throw new RuntimeException("Exception: Glfw Error: " + error.name() + description);
    }

    /**
     * 帧缓冲大小回调函数
     * @param size
     */
    private static void FrameBufferSizeCallBack(Vec2i size) {
        glViewport(size);
    }

    /**
     * 键盘回调函数
     * @param keyCode
     * @param action
     */
    private static void KeyCallBack(int keyCode, int action) {
        key.keyCode = keyCode;
        key.action = action;
    }

    /**
     * 鼠标按钮回调函数
     * @param button
     * @param action
     */
    private static void MouseButtonCallBack(int button, int action) {
        mouse.button = button;
        mouse.action = action;
    }

    /**
     * 鼠标指针回调函数
     * @param xPos
     * @param yPos
     */
    private static void CursorPosCallBack(double xPos, double yPos) {
        mouse.xOffset = xPos - mouse.xPos;
        mouse.yOffset = yPos - mouse.yPos;
        mouse.xPos = xPos;
        mouse.yPos = yPos;
    }

    /**
     *  注册回调函数
     */
    private void SetCallBacks() {
        window.setFramebufferSizeCB((Vec2i size)-> {
            FrameBufferSizeCallBack(size);
            return Unit.INSTANCE;
        });
        window.setKeyCB((Integer key, Integer scanCode, Integer action, Integer mods)-> {
            KeyCallBack(key, action);
            return Unit.INSTANCE;
        });
        window.setCursorPosCB((Vec2 pos) ->{
            CursorPosCallBack(pos.getX(), pos.getY());
            return Unit.INSTANCE;
        });
        window.setMouseButtonCB((Integer button, Integer action, Integer mods)->{
            MouseButtonCallBack(button, action);
            return Unit.INSTANCE;
        });
    }

    /**
     * 内部更新函数，负责更新内部状态
     * @param dt
     * @return
     */
    private boolean OnUpdate(double dt) {
        return true;
    }

    /**
     * 内部渲染函数，负责调用渲染器的渲染队列
     * @param dt
     * @return
     */
    private boolean OnRender(double dt) {
        renderer.Render(dt);
        return true;
    }
}
