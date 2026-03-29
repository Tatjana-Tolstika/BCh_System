using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using OpenTK.Graphics.OpenGL4;
using OpenTK.Mathematics;
using OpenTK.Windowing.GraphicsLibraryFramework;
using OpenTK.Windowing.Desktop;
using OpenTK.Windowing.Common;
using StbImageSharp;

namespace OwnProject
{
    internal class Game : GameWindow
    {
        int width;
        int height;


        List<Vector3> coords = new List<Vector3>()
        {
            // Back face
            new Vector3(-0.5f, 0.5f, -0.5f),
            new Vector3(0.5f, 0.5f, -0.5f),
            new Vector3(0.5f, -0.5f, -0.5f),
            new Vector3(-0.5f, -0.5f, -0.5f),

            // Front face
            new Vector3(-0.5f, 0.5f, 0.5f),
            new Vector3(0.5f, 0.5f, 0.5f),
            new Vector3(0.5f, -0.5f, 0.5f),
            new Vector3(-0.5f, -0.5f, 0.5f),

            // Right face
            new Vector3(0.5f, 0.5f, 0.5f),
            new Vector3(0.5f, -0.5f, 0.5f),
            new Vector3(0.5f, -0.5f, -0.5f),
            new Vector3(0.5f, 0.5f, -0.5f),

            // Left face
            new Vector3(-0.5f, 0.5f, 0.5f),
            new Vector3(-0.5f, -0.5f, 0.5f),
            new Vector3(-0.5f, -0.5f, -0.5f),
            new Vector3(-0.5f, 0.5f, -0.5f),
            
            // Top face
            new Vector3(-0.5f, 0.5f, 0.5f),
            new Vector3(0.5f, 0.5f, 0.5f),
            new Vector3(0.5f, 0.5f, -0.5f),
            new Vector3(-0.5f, 0.5f, -0.5f),

            // Bottom face
            new Vector3(-0.5f, -0.5f, 0.5f),
            new Vector3(0.5f, -0.5f, 0.5f),
            new Vector3(0.5f, -0.5f, -0.5f),
            new Vector3(-0.5f, -0.5f, -0.5f),
        };


        List<Vector2> texCoords = new List<Vector2>()
        {
            new Vector2(0.0f, 0.0f),
            new Vector2(1.0f, 0.0f),
            new Vector2(1.0f, 1.0f),
            new Vector2(0.0f, 1.0f),

            new Vector2(0.0f, 0.0f),
            new Vector2(1.0f, 0.0f),
            new Vector2(1.0f, 1.0f),
            new Vector2(0.0f, 1.0f),

            new Vector2(0.0f, 0.0f),
            new Vector2(1.0f, 0.0f),
            new Vector2(1.0f, 1.0f),
            new Vector2(0.0f, 1.0f),

            new Vector2(0.0f, 0.0f),
            new Vector2(1.0f, 0.0f),
            new Vector2(1.0f, 1.0f),
            new Vector2(0.0f, 1.0f),

            new Vector2(0.0f, 0.0f),
            new Vector2(1.0f, 0.0f),
            new Vector2(1.0f, 1.0f),
            new Vector2(0.0f, 1.0f),

            new Vector2(0.0f, 0.0f),
            new Vector2(1.0f, 0.0f),
            new Vector2(1.0f, 1.0f),
            new Vector2(0.0f, 1.0f),
        };

        int[] indices =
        {
            0, 1, 2,
            2, 3, 0,

            4, 5, 6,
            6, 7, 4,

            8, 9, 10,
            10, 11, 8,

            12, 13, 14,
            14, 15, 12,

            16, 17, 18,
            18, 19, 16,

            20, 21, 22,
            22, 23, 20
        };

        List<Vector3> positions = new List<Vector3>()
        {
            //new Vector3(-3, 2, -10),
            //new Vector3(1, -2, -5),
            //new Vector3(2, 3, -3)
        };

        int vao;
        int vbo;
        int ebo;
        int tvbo;
        int textureId;
        int shaderProgram;

        float yRot;
        Camera camera;


        public Game(int width, int height) : base(GameWindowSettings.Default, NativeWindowSettings.Default)
        {
            this.width = width;
            this.height = height;

            CenterWindow(new Vector2i(width, height));
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
            vao = GL.GenVertexArray();
            vbo = GL.GenBuffer();
            ebo = GL.GenBuffer();
            tvbo = GL.GenBuffer();

            GL.BindVertexArray(vao);

            //VBO-----------------------------------------------------------------------------
            GL.BindBuffer(BufferTarget.ArrayBuffer, vbo);
            GL.BufferData(BufferTarget.ArrayBuffer, coords.Count * Vector3.SizeInBytes, coords.ToArray(), BufferUsageHint.StaticDraw);
            GL.VertexAttribPointer(0, 3, VertexAttribPointerType.Float, false, 0, 0);
            GL.EnableVertexAttribArray(0);

            //TVBO-----------------------------------------------------------------------------
            GL.BindBuffer(BufferTarget.ArrayBuffer, tvbo);
            GL.BufferData(BufferTarget.ArrayBuffer, texCoords.Count * Vector3.SizeInBytes, texCoords.ToArray(),BufferUsageHint.StaticDraw);
            GL.VertexAttribPointer(1,2, VertexAttribPointerType.Float, false,0, 0);
            GL.EnableVertexAttribArray(1);

            //EBO------------------------------------------------------------------------------
            GL.BindBuffer(BufferTarget.ElementArrayBuffer, ebo);
            GL.BufferData(BufferTarget.ElementArrayBuffer, indices.Length * sizeof(float), indices, BufferUsageHint.StaticDraw);
        
            //Texture---------------------------------------------------------------------------
            textureId = GL.GenTexture();
            GL.ActiveTexture(TextureUnit.Texture0);
            GL.BindTexture(TextureTarget.Texture2D,textureId);

            GL.TexParameter(TextureTarget.Texture2D, TextureParameterName.TextureWrapS, (int)TextureWrapMode.Repeat);
            GL.TexParameter(TextureTarget.Texture2D, TextureParameterName.TextureWrapT, (int)TextureWrapMode.Repeat);
            GL.TexParameter(TextureTarget.Texture2D, TextureParameterName.TextureMinFilter, (int)TextureMinFilter.Nearest);
            GL.TexParameter(TextureTarget.Texture2D, TextureParameterName.TextureMagFilter, (int)TextureMagFilter.Nearest);

            ImageResult newTexture = ImageResult.FromStream(File.OpenRead("../../../textures/Buh.png"), ColorComponents.RedGreenBlueAlpha);
            GL.TexImage2D(TextureTarget.Texture2D, 0, PixelInternalFormat.Rgba, newTexture.Width, newTexture.Height, 0, PixelFormat.Rgba, PixelType.UnsignedByte, newTexture.Data);
            GL.BindTexture(TextureTarget.Texture2D, textureId);

            //ShaderProgram-------------------------------------------------------------------------------
            
            shaderProgram = GL.CreateProgram();
            int vertexShader = GL.CreateShader(ShaderType.VertexShader);
            GL.ShaderSource(vertexShader, readFile("Default.vert"));
            GL.CompileShader(vertexShader);
            string log = GL.GetShaderInfoLog(vertexShader);
            Console.WriteLine(log);

            int fragShader = GL.CreateShader(ShaderType.FragmentShader);
            GL.ShaderSource(fragShader, readFile("Default.frag"));
            GL.CompileShader(fragShader);
            log = GL.GetShaderInfoLog(fragShader);
            Console.WriteLine(log);

            GL.AttachShader(shaderProgram, fragShader);
            GL.AttachShader(shaderProgram, vertexShader);
            GL.LinkProgram(shaderProgram);

            camera = new Camera(width, height, new Vector3(0, 0, 0));
            GL.Enable(EnableCap.DepthTest);
           // CursorState = CursorState.Grabbed;
            for (int x = -5; x < 6; x++)
            {
                for (int y = -5; y < 6; y++)
                {
                    for (int z = -10; z < 0; z++)
                    {
                        positions.Add(new Vector3(x * 3, y * 3, z * 3));
                    }
                }
            }



        }

        protected override void OnUnload() 
        {
            base.OnUnload();

        }

        protected override void OnUpdateFrame(FrameEventArgs args) 
        {
            MouseState mouse = MouseState;
            KeyboardState input = KeyboardState;
            base.OnUpdateFrame(args);
            camera.InputController(input, mouse, args);
        }

        protected override void OnRenderFrame(FrameEventArgs args) 
        {
            yRot += 0.001f;
            GL.ClearColor(0.1f, 0.1f, 0.0f, 1.0f);
            GL.Clear(ClearBufferMask.ColorBufferBit | ClearBufferMask.DepthBufferBit);

            // Create matrix values
            Matrix4 view = camera.GetViewMatrix();
            Matrix4 projection = camera.GetProjectionMatrix();

            // Get Uniform location
            int modelLocation = GL.GetUniformLocation(shaderProgram, "model");
            int viewLocation = GL.GetUniformLocation(shaderProgram, "view");
            int projectionLocation = GL.GetUniformLocation(shaderProgram, "projection");


            GL.UseProgram(shaderProgram);
            // Assign uniform values
            GL.UniformMatrix4(viewLocation, true, ref view);
            GL.UniformMatrix4(projectionLocation, true, ref projection);

            GL.BindVertexArray(vao);
            GL.BindTexture(TextureTarget.Texture2D, textureId);

            for (int i = 0; i < positions.Count; i++)
            {
                Matrix4 model = Matrix4.Identity;
                model = Matrix4.CreateRotationY(yRot);
                Matrix4 translation = Matrix4.CreateTranslation(positions[i]);
                model *= translation;

                GL.UniformMatrix4(modelLocation, true, ref model);
                GL.DrawElements(BeginMode.Triangles, indices.Length, DrawElementsType.UnsignedInt, 0);
            }

            Context.SwapBuffers();

            base.OnRenderFrame(args);
        }


        private string readFile(string path)
        {
            string text = "";

            using (StreamReader sr = new StreamReader("../../../shaders/" + path))
            {
                text = sr.ReadToEnd();
            }
            return text;
        }


    }
}
