package demo;

import utils.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(time = 1, iterations = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(time = 1, iterations = 3, timeUnit = TimeUnit.SECONDS)
public class Benchmark {
    @Param({"100", "201", "405", "811"})
    public int iterationCount;


    BstSet<Integer> BstTree = new BstSet<>();
    AvlSet<Integer> AvlTree = new AvlSet<>();
    RedBlackSet<Integer> RedBlackTree = new RedBlackSet<>();

    @Setup(Level.Iteration)
    public void generateElements() {
     BstTree = new BstSet<>();
     AvlTree = new AvlSet<>();
     RedBlackTree = new RedBlackSet<>();

        for (int i = iterationCount + iterationCount; i > iterationCount; i--) {
            BstTree.add(i);
            AvlTree.add(i);
            RedBlackTree.add(i);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public BstSet<Integer> deletBstTree() {
        for (int i = 50; i < iterationCount; i++)
        {
            BstTree.remove(i);
        }
        return BstTree;
    }

    @org.openjdk.jmh.annotations.Benchmark
    public RedBlackSet<Integer> deletRedBlackTree() {
        for (int i = 50; i < iterationCount; i++)
        {
            RedBlackTree.remove(i);
        }
        return RedBlackTree;
    }

    @org.openjdk.jmh.annotations.Benchmark
    public AvlSet<Integer> deletAvlTree() {
        for (int i = 50; i < iterationCount; i++)
        {
            AvlTree.remove(i);
        }
        return AvlTree;
    }


    @TearDown(Level.Iteration)
    public void resetTrees() {
        BstTree.clear();
        AvlTree.clear();
        RedBlackTree.clear();
    }
    /*@org.openjdk.jmh.annotations.Benchmark
    public AvlSet<Integer> findAvlTree() {
        for(int i = 0; i < iterationCount; i++)
        {
            AvlTree.find(i);
        }
        return AvlTree;
    }

    @org.openjdk.jmh.annotations.Benchmark
    public BstSet<Integer> findBstTree() {
        for(int i = 0; i < iterationCount; i++)
        {
            BstTree.find(i);
        }
        return BstTree;
    }

    @org.openjdk.jmh.annotations.Benchmark
    public RedBlackSet<Integer> findRedBlackTree() {
        for(int i = 0; i < iterationCount; i++)
        {
            RedBlackTree.find(i);
        }
        return RedBlackTree;
    }*/

    /*@org.openjdk.jmh.annotations.Benchmark
    public AvlSet<Integer> addAvlTree() {
        AvlSet<Integer> carSetMoreThousend = new AvlSet<>();
        for(int i = 0; i < iterationCount; i++)
        {
            carSetMoreThousend.add(i);
        }
        return carSetMoreThousend;
    }

    @org.openjdk.jmh.annotations.Benchmark
    public BstSet<Integer> addBstTree() {
        BstSet<Integer> carSetMoreThousend = new BstSet<>();
        for(int i = 0; i < iterationCount; i++)
        {
            carSetMoreThousend.add(i);
        }
        return carSetMoreThousend;
    }

    @org.openjdk.jmh.annotations.Benchmark
    public RedBlackSet<Integer> addRedBlackTree() {
        RedBlackSet<Integer> carSetMoreThousend = new RedBlackSet<>();
        for(int i = 0; i < iterationCount; i++)
        {
            carSetMoreThousend.add(i);
        }
        return carSetMoreThousend;
    }*/
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Benchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
