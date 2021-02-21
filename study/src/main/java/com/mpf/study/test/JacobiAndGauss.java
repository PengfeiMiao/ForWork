package com.mpf.study.test;

public class JacobiAndGauss {

    static int n = 3;

    public static void main(String[] args) {
        float[][] A = {
                {-8, 1, 1},
                {1, -5, 1},
                {1, 1, -4}
        };
        float[] B = {1, 16, 7};
        float e, m, temp, t;
        float y[] = {0.0f, 0.0f, 0.0f},
                x0[] = {0.0f, 0.0f, 0.0f},
                x[] = {0.0f, 0.0f, 0.0f};
        int N, i, j, k, sum1, sum2;
        e = 10e-4f;
        System.out.println("输入要求的精度e=" + e);
        //Scanner scan = new Scanner(System.in);
        //e = scan.nextFloat();

        N = 30;
        System.out.println("最大迭代次数N=" + N);
        //N = scan.nextInt();
        for (i = 0; i < n; i++) {
            System.out.println("x0" + "[" + i + "]=" + x0[i]);
        }
        // Jacobi迭代过程

        for (k = 0; k < N; k++) {
            m = 0;
            for (i = 0; i < n; i++) {
                x[i] = B[i];
                for (j = 0; j < n; j++) {
                    if (j != i) {
                        x[i] = x[i] - A[i][j] * x0[j];
                    }
                }
                x[i] = x[i] / A[i][i];
//            System.out.println("x[]" + x[i]);
                temp = Math.abs(x[i] - x0[i]);
                //System.out.println("temp" + temp);
                // System.out.println(temp);
                if (temp > m) {
                    m = temp;
                }
            }
            System.out.print(k + 1 + "次迭代:");

            for (int i1 = 0; i1 < n; i1++) {
                System.out.println("x" + "[" + i1 + "]=" + x[i1]);
                x0[i1] = x[i1];
            }
            if (m < e) {
                sum1 = k + 1;
                System.out.println("Jacobi迭代结束");
                System.out.println("一共迭代了" + sum1);
                break;
            }
        }
        if (k > N) {
            System.out.println("第" + k + "次迭代未找出满足精度的解 发散");
        }
        System.out.println("-------------------------------------");
        System.out.println("以下是Gauss-Seidel迭代");
        System.out.println("-------------------------------------");
        // 以下是Gauss-Seidel迭代
        for (k = 0; k < N; k++) {
            m = 0;
            for (i = 0; i < n; i++) {
                t = y[i];
                y[i] = B[i];
                for (j = 0; j < n; j++) {
                    if (j != i) {
                        y[i] = y[i] - A[i][j] * y[j];
                    }
                }
                y[i] = y[i] / A[i][i];
                //System.out.println("x[]" + y[i]);
                temp = Math.abs(y[i] - t);
                //System.out.println("temp" + temp);
                // System.out.println(temp);
                if (temp > m) {
                    m = temp;
                }
            }
            System.out.print(k + 1 + "次迭代:");
            for (int i1 = 0; i1 < n; i1++) {
                System.out.println("x" + "[" + i1 + "]=" + y[i1]);
                //x0[i1] = x[i1];
            }
            if (m < e) {
                sum2 = k + 1;
                System.out.println("Gauss-Seidel迭代结束");
                System.out.println("一共迭代了" + sum2);
                break;
            }

            if (k > N) {
                System.out.println("第" + k + "次迭代未找出满足精度的解 发散");
            }
        }
    }
}