package API;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    public static void main(String[] args) {
        ArrayList<String> products = new ArrayList<>();
        products.add("https://www.bestbuy.ca/en-ca/product/laifen-premium-hair-dryer-lpr200gp-golden-pink-only-at-best-buy/17113615?source=collection&adSlot=1&slotPos=1");
        products.add("https://www.bestbuy.ca/en-ca/product/hyperx-quadcast-s-rgb-usb-condenser-microphone-black/15101853?source=collection&adSlot=3&slotPos=3");
        products.add("https://www.bestbuy.ca/en-ca/product/hyperx-cloud-alpha-2-pro-on-ear-noise-cancelling-true-wireless-headphones-black/19491515?icmp=Recos_4across_y_mght_ls_lk");
        products.add("https://www.bestbuy.ca/en-ca/product/logitech-mx-master-4-bluetooth-optical-mouse-w-advanced-performance-haptic-feedback-black-only-at-best-buy/19430806");
        products.add("https://www.bestbuy.ca/en-ca/product/logitech-mx-mechanical-wireless-full-size-backlit-tactile-keyboard-graphite/16157468?icmp=Recos_4across_y_mght_ls_lk");
        products.add("https://www.bestbuy.ca/en-ca/product/oakley-meta-vanguard-glasses-with-meta-ai-audio-photo-video-compatibility-black-prizm-24k/19466231");
        products.add("https://www.bestbuy.ca/en-ca/product/19206110");

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> {
            System.out.println("Running task");
            for (String product : products) {
                BestBuyProductFetcher fetcher = new BestBuyProductFetcher(product);
                fetcher.updateJson();
            }

            try {
                GitUpdator.pushChanges();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Finished running task");
        };
//        executor.execute(task);
        executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.DAYS);
    }
}
