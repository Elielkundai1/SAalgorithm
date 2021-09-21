
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;



public class DistribSA {
    private class SA implements Callable<SA.SAWrapper> {

        private class SAWrapper {
            public SAWrapper(long threadId, int value) {
                this.threadId = threadId;
                this.value = value;
            }

            private long threadId;
            private int value;


            public long getThreadId() {
                return threadId;
            }

            public int getValue() {
                return value;
            }
        }

        @Override
        public SAWrapper call() {
            return new SAWrapper(Thread.currentThread().getId(), 4);
        }
    }


    public static void main(String args[]) throws ExecutionException, InterruptedException {
        DistribSA DistribSA = new DistribSA();
        DistribSA.doSA();

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<SA.SAWrapper> values = new ArrayList<>();

        for (int x = 0; x < 5; x++) {
            SA algo = DistribSA.new SA();
            values.add(executorService.submit(algo).get());
        }

        int highestValue = values.stream()
                .mapToInt(SA.SAWrapper::getValue)
                .max()
                .getAsInt();

        Set<Long> threadsWithHighestValue = values.stream()
                .filter(algo -> algo.getValue() == highestValue)
                .map(SA.SAWrapper::getThreadId)
                .collect(Collectors.toSet());

        System.out.println("Id of core which achieved highest value : ");
        threadsWithHighestValue.forEach(System.out::println);


    }

    int length = 100;    //  length	of  the	required	solution

    double T = 1000;   //  initial  t e m p e r a t u t r e

    double coolRate = 0.1;  //  cooling	rate

    Random rand = new Random();

    int[] Vc = new int[length];

    int[] v1 = new int[length];

    int[] v2 = new int[length];

    int[] v3 = new int[length];

    int[] Vn = new int[length];

    int Vc_fit = 0;

    int v1_fit = 0;

    int v2_fit = 0;

    int v3_fit = 0;

    int Vn_fit = 0;

    {

        for (int i = 0; i < Vc.length; i++) {

            if (Math.random() > 0.5) {

                Vc[i] = 0;

            } else {

                Vc[i] = 1;

            }

        }

    }

    int numOf = 1;


    public void doSA() {    //  si mu la te d	an ne al in g	al go ri th m


        int count = 0;

        while (Vc_fit != Vc.length) {

            Vc_fit = this.getFit(Vc);

            System.out.println("Vc fitness " + Vc_fit + " at loop " + count);
            count++;

            v1 = Vc.clone();

            v2 = Vc.clone();

            v3 = Vc.clone();

            v1 = this.mutate(v1).clone();

            v1_fit = this.getFit(v1);

            v2 = this.mutate(v2).clone();

            v2_fit = this.getFit(v2);

            v3 = this.mutate(v3).clone();

            v3_fit = this.getFit(v3);


            if ((v1_fit > v2_fit) && (v1_fit > v3_fit)) {

                Vn = v1.clone();

            } else if ((v2_fit > v1_fit) && (v2_fit > v3_fit)) {
                Vn = v2.clone();

            } else if
            ((v2_fit > v1_fit) && (v2_fit > v3_fit)) {


                Vn
                        =
                        v3.clone();


            } else {

                int sel = rand.nextInt(3);    //  randomly	se lc et in g	any  of  the	so lu tif none is an i m p r o v e m e m n t
                if (sel == 0) {

                    Vn = v1.clone();

                } else if (sel == 1) {

                    Vn = v2.clone();
                } else {

                    Vn = v3.clone();

                }

            }

            Vn_fit = this.getFit(Vn);

            if (Vc_fit < Vn_fit) {

                Vc = Vn.clone();

            } else if (Math.random() < T) {    //  randomly	a cc ep ti ng  a  weaker	solution

                Vc = Vn.clone();

                T = T * coolRate;

            }

        }

        for (int j = 0; j < Vc.length; j++) {

            System.out.print(Vc[j]);

        }

        System.out.println("");

    }

    public int[] mutate(int arr[]) {

        int index = rand.nextInt(arr.length);

        if (arr[index] == 0) {

            arr[index] = 1;

        } else {

            arr[index] = 0;

        }

        return arr;

    }

    public int getFit(int[] a) {

        int f = 0;

        for (int i = 0; i < a.length; i++) {

            if (a[i] == 1)

                f = f + 1;

        }

        return f;


    }
}





