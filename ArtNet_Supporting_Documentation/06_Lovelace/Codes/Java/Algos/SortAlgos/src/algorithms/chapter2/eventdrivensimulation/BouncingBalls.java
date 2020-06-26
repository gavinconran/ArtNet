package algorithms.chapter2.eventdrivensimulation;

import edu.princeton.cs.algs4.StdDraw;

public class BouncingBalls
{
    public static void main(String[] args)
    {
//        int N = Integer.parseInt(args[0]);
        int N = 100;
        Ball[] balls = new Ball[N];
        for (int i = 0; i < N; i++)
            balls[i] = new Ball();
        while(true)
        {
            StdDraw.clear();
            for (int i = 0; i < N; i++)
            {
                balls[i].move(0.5);
                balls[i].draw();
            }
            StdDraw.show(50);
        }
    }
}
