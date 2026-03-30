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
    internal class FigureOne : Figure
    {
        public FigureOne(Vector3 position, float size) : base(position)
        {
            float h = size / 2f;

            List<Vector3> vertices = new List<Vector3>()
            {

                new Vector3(-h, -h, -(2*h)),
                new Vector3( h, -h, -(2*h)),
                new Vector3( h,  h, -(2*h)),
                new Vector3(-h,  h, -(2*h)),

                
                new Vector3(-(2*h), -(2*h),  (2*h)),
                new Vector3( (2*h), -(2*h),  (2*h)),
                new Vector3( (2*h),  (2*h),  (2*h)),
                new Vector3(-(2*h),  (2*h),  (2*h)),

                //Vektori "viennieka jumtinam" (nepareizas)
                new Vector3(-h, -h, -h),
                new Vector3( h, -h, -h),
                new Vector3( h,  h, -h),
                new Vector3(-h,  h, -h),

                new Vector3(-h, -h,  h),
                new Vector3( h, -h,  h),
                new Vector3( h,  h,  h),
                new Vector3(-h,  h,  h)
            };

            uint[] indices =
            {

                // aizmugure
                0, 1, 2,
                2, 3, 0,

                // priekšpuse
                4, 5, 6,
                6, 7, 4,

                // pakreisi
                0, 4, 7,
                7, 3, 0,

                // palabi
                1, 5, 6,
                6, 2, 1,

                // apakša
                0, 1, 5,
                5, 4, 0,

                // augša
                3, 2, 6,
                6, 7, 3


            };

            LoadVAO(vertices, indices);

        }
    }
}
