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

namespace KD.Objects
{
    internal class Floor : Figure
    {
        public Floor(Vector3 position, float size) : base(position)
        {
            float h = size / 2f;
            //Floor
            List<Vector3> vertices = new List<Vector3>()
            {
               
                new Vector3(-h, 0, -h),
                new Vector3( h, 0, -h),
                new Vector3( h, 0,  h),
                new Vector3(-h, 0,  h),


            };

            uint[] indices =
            {
               0, 3, 2,
                2, 1, 0
            };

            LoadVAO(vertices, indices);
        }
    }
}
