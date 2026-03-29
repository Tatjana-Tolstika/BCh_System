using OwnProject;

namespace OwnProject
{
    class Program
    {
        private static void Main(string[] args)
        {
            using (Game game = new Game(500, 500))
            {
                game.Run();
            }
        }
    }
}