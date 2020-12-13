package xin.jiangqiang;

import lombok.extern.slf4j.Slf4j;
import xin.jiangqiang.annotation.*;
import xin.jiangqiang.config.Config;
import xin.jiangqiang.crawler.RAMCrawler;
import xin.jiangqiang.entities.Crawler;
import xin.jiangqiang.entities.Next;
import xin.jiangqiang.entities.Page;
import xin.jiangqiang.filter.NextFilter;
import xin.jiangqiang.management.Record;
import xin.jiangqiang.management.RecordImpl;


import java.io.IOException;
import java.util.Set;

@Slf4j
@App
public class Test extends RAMCrawler {

    @Before
    public void before() {

    }

    @Match(value = "type1", code = "404")
    public void match1(Page page, Next next) {
//        log.info("url:" + page.getUrl() + " type:" + page.getType());
    }

    @Match(type = "type2")
    public void match2(Page page, Next next) {
//        log.info("url:" + page.getUrl() + " type:" + page.getType());
    }

    @Deal
    public void deal(Page page, Next next) {
//        log.info(page);
//        log.info(next);
//        next.addSeed("https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp/4.0.0").setType("type2");
//        next.addSeed("https://blog.csdn.net/wangmx1993328/article/details/81662001").setType("type1");
//        next.addSeed("https://blog.csdn.net/ds986619036/article/details/89310472");
//        log.info("url:" + page.getUrl() + " type:" + page.getType());
    }

    @After
    public void after() {
        log.info("after执行了");
    }

    public static void main(String[] args) throws IOException {
        Test test = new Test();
        Config config = new Config();
        config.setPackageName("xin.jiangqiang");
        Crawler crawler = new Crawler();
        crawler.addSeed("https://blog.jiangqiang.xin").setType("type2");
//        crawler.addSeed("https://mvnrepository.com").setType("type1");
        config.addRegEx("https://.*");
//        config.addDefaultRegEx("https://.*");
//        crawler.addSeed("https://blog.jiangqiang.xin");
//        crawler.addSeed("https://www.4399.com");
//        crawler.addSeed("https://github.com/jiangqiang2020/J-crawler");
        config.setSavePath("tmp.obj");
        config.setIsContinue(false);

        test.setConfig(config);
        test.setCrawler(crawler);
        test.setFilter(new NextFilter());
        test.setRecord(new RecordImpl());
        new Thread(() -> {
            try {
                test.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        Record record = new RecordImpl();
        while (true) {
            try {
                Thread.sleep(1000);
                Set<String> succ = (Set<String>) record.getSucc();
                Set<String> err = (Set<String>) record.getErr();
//                System.out.println(succ);
//                System.err.println(err);
                System.out.println("爬取成功的URL：   " + succ.size());
                System.out.println("爬取失败的URL：   " + err.size());
                if (test.getIsEnd()) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
