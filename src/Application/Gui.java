package Application;
import glm_.vec2.Vec2;
import glm_.vec3.Vec3d;
import glm_.vec4.Vec4;
import imgui.Cond;
import imgui.ImGui;
import imgui.MutableProperty0;
import imgui.classes.IO;
import imgui.classes.Context;
import imgui.impl.gl.ImplGL3;
import imgui.impl.glfw.ImplGlfw;
import uno.glfw.GlfwWindow;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class Gui {
    private GlfwWindow window;
    private Context context;
    private ImplGlfw implGlfw;
    private ImplGL3 implGl3;
    private ImGui imgui = ImGui.INSTANCE;
    private IO io;


    private float[] f = {0f};
    private Vec4 clearColor = new Vec4(0.45f, 0.55f, 0.6f, 1f);
    // Java users can use both a MutableProperty0 or a Boolean Array
    private boolean[] showAnotherWindow = {true};
    private boolean[] showDemo = {true};
    private int[] counter = {0};

    Gui(GlfwWindow window) {
        context = new Context();
        implGlfw = new ImplGlfw(window, true, null);
        implGl3 = new ImplGL3();
        io = imgui.getIo();
    }

    public void Update(double dt) {
        // Start the Dear ImGui frame
        implGl3.newFrame();
        implGlfw.newFrame();
        imgui.newFrame();

        imgui.text("Hello, world!");                                // Display some text (you can use a format string too)
        // TODO
//        imgui.sliderFloat("float", f, 0, 0f, 1f, "%.3f", 1f);       // Edit 1 float using a slider from 0.0f to 1.0f
        imgui.colorEdit3("clear color", clearColor, 0);               // Edit 3 floats representing a color

        imgui.checkbox("Demo Window", showDemo);                 // Edit bools storing our windows open/close state
        imgui.checkbox("Another Window", showAnotherWindow);

        if (imgui.button("Button", new Vec2())) // Buttons return true when clicked (NB: most widgets return true when edited/activated)
            counter[0]++;

        imgui.sameLine(0f, -1f);
        imgui.text("counter = " + counter[0]);

        imgui.text("Application average %.3f ms/frame (%.1f FPS)", 1_000f / io.getFramerate(), io.getFramerate());

        // 2. Show another simple window. In most cases you will use an explicit begin/end pair to name the window.
        if (showAnotherWindow[0]) {
            imgui.begin("Another Window", showAnotherWindow, 0);
            imgui.text("Hello from another window!");
            if (imgui.button("Close Me", new Vec2()))
                showAnotherWindow[0] = false;
            imgui.end();
        }

        /*  3. Show the ImGui demo window. Most of the sample code is in imgui.showDemoWindow().
                Read its code to learn more about Dear ImGui!  */
        if (showDemo[0]) {
            /*  Normally user code doesn't need/want to call this because positions are saved in .ini file anyway.
                    Here we just want to make the demo initial state a bit more friendly!                 */
            imgui.setNextWindowPos(new Vec2(650, 20), Cond.FirstUseEver, new Vec2());
            imgui.showDemoWindow(showDemo);
        }

        // Rendering
        imgui.render();
    }

    public void Render(double dt) {
        implGl3.renderDrawData(imgui.getDrawData());
    }


}
