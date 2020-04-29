
package com.legend.jmh;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

public class StringBuilderRunner {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                // 导入要测试的类
                .include(StringConnectBenchmark.class.getSimpleName())
                // 预热5轮
                .warmupIterations(5)
                .warmupTime(new TimeValue(3, TimeUnit.SECONDS))
                // 度量10轮
                .measurementIterations(10)
                //吞吐量模式
                .mode(Mode.Throughput)
                //启三个线程
                .forks(3)
                .build();
        new Runner(opt).run();

    }

}