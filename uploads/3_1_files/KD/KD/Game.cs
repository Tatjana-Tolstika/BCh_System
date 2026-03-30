using KD.Objects;//lai izmantot klases no mapes objects
using OpenTK.Graphics.OpenGL4;
using OpenTK.Mathematics;
using OpenTK.Windowing.Common;
using OpenTK.Windowing.Desktop;
using OpenTK.Windowing.GraphicsLibraryFramework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using StbImageSharp;



namespace KD
{
    internal class Game : GameWindow
    {
        int width;
        int height;
        int shaderProgram;
        List<Figure> objects = new List<Figure>();

        private Random random = new Random();

        Camera camera;

        public Game(int width, int height) : base(GameWindowSettings.Default, NativeWindowSettings.Default)
        {
            this.width = width;
            this.height = height;

            CenterWindow(new Vector2i(this.width, this.height));
        }
        protected override void OnResize(ResizeEventArgs e)
        {
            
            base.OnResize(e);
            GL.Viewport(0, 0, e.Width, e.Height);
            this.width = e.Width;
            this.height = e.Height;
        }

        protected override void OnLoad()
        {
            base.OnLoad();

           

            // Shaderi
            shaderProgram = GL.CreateProgram();

            int vert = GL.CreateShader(ShaderType.VertexShader);
            GL.ShaderSource(vert, readFile("Default.vert"));
            GL.CompileShader(vert);
            string log = GL.GetShaderInfoLog(vert);
            Console.WriteLine(log);

            int frag = GL.CreateShader(ShaderType.FragmentShader);
            GL.ShaderSource(frag, readFile("Default.frag"));
            GL.CompileShader(frag);
            log = GL.GetShaderInfoLog(frag);
            Console.WriteLine(log);

            GL.AttachShader(shaderProgram, vert);
            GL.AttachShader(shaderProgram, frag);
            GL.LinkProgram(shaderProgram);

            camera = new Camera(width, height, new Vector3(0,0,0)); //(0, -30, -40)); //
            GL.Enable(EnableCap.DepthTest);
            CursorState = CursorState.Grabbed;

        }

        protected override void OnUnload()
        {
            base.OnUnload();
            foreach (var obj in objects)
            {
                obj.Dispose();
            }
            
        }

        

        protected override void OnUpdateFrame(FrameEventArgs args)
        {
            MouseState mouse = MouseState;
            KeyboardState input = KeyboardState;

            //  Pogas lai izveidotu objektus
            if (KeyboardState.IsKeyPressed(Keys.C))
                spawnCube();

            if (KeyboardState.IsKeyPressed(Keys.F))
                spawnOne();
            if (KeyboardState.IsKeyPressed(Keys.V))
                spawnFloor();



            base.OnUpdateFrame(args);
            camera.InputController(input, mouse, args);
        }

        protected override void OnRenderFrame(FrameEventArgs e)
        {
            

            GL.ClearColor(0.1f, 0.1f, 0.15f, 1f);
            GL.Clear(ClearBufferMask.ColorBufferBit | ClearBufferMask.DepthBufferBit);

            GL.UseProgram(shaderProgram);

            Matrix4 view = camera.GetViewMatrix();
            Matrix4 projection = camera.GetProjectionMatrix();

            int modelLoc = GL.GetUniformLocation(shaderProgram, "model");
            int viewLoc = GL.GetUniformLocation(shaderProgram, "view");
            int projLoc = GL.GetUniformLocation(shaderProgram, "projection");

            GL.UniformMatrix4(viewLoc, true, ref view);
            GL.UniformMatrix4(projLoc, true, ref projection);

            foreach (Figure fig in objects)
            {
                 Matrix4 model = Matrix4.CreateTranslation(fig.position);
                
                GL.UniformMatrix4(modelLoc, true, ref model);
                fig.Draw();

            }
            


            SwapBuffers();
            base.OnRenderFrame(e);
        }
        //----------------------------------------------------------------------------------------------
        private string readFile(string path)
        {
            string text = "";

            using (StreamReader sr = new StreamReader("../../../shaders/" + path))
            {
                text = sr.ReadToEnd();
            }
            return text;
        }

        private void spawnCube()
        {
            objects.Add(new Cube(randomPosition(), 1.0f));
            
        }

        private void spawnOne()
        {
            objects.Add(new FigureOne(randomPosition(), 1f));
            
        }

        private void spawnFloor()
        {
            objects.Add(new Floor(randomForFloor(), 1.5f));
        }

        private Vector3 randomPosition()
        {
            float x = random.Next(-10, 10);
            float y = random.Next(-5, 5);
            float z = random.Next(-15, -5);
            return new Vector3(x, y, z);
        }
        private Vector3 randomForFloor()
        {
            float x = random.Next(-10, 10);
            float z = random.Next(-10, 10);
            return new Vector3(x, 0, z);
        }
    }
}
